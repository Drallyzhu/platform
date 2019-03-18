package com.cloudsoft.platform.securityoauth2.jwt;

import com.cloudsoft.logger.LoggerUtil;
import com.cloudsoft.platform.securityoauth2.domain.User;
import com.cloudsoft.platform.securityoauth2.exception.TokenException;
import com.cloudsoft.platform.securityoauth2.service.UserService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.tokenHead}")
    private  String tokenHead;

    @Autowired
    private UserService userService;

    public String getUsernameFromToken(String token) {
        String username;
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
            try {
                claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
            }catch (ExpiredJwtException e) {
                LoggerUtil.error(log,"Token已过期: {0} " + e);
            throw new TokenException("Token已过期");
            } catch (UnsupportedJwtException e) {
                LoggerUtil.error(log,"Token格式错误: {0} " + e);
                throw new TokenException("Token格式错误");
            } catch (MalformedJwtException e) {
                LoggerUtil.error(log,"Token没有被正确构造: {0} " + e);
                throw new TokenException("Token没有被正确构造");
            } catch (IllegalArgumentException e) {
                LoggerUtil.error(log,"非法参数异常: {0} " + e);
                throw new TokenException("非法参数异常");
            }
        return claims;
        }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        SecurityUser user = (SecurityUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

    /**
     * 从请求头中获取token
     *
     * @return
     */
    private String getToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest().getHeader(this.header);
    }

    public User getLoginUser() {
        String header = getToken();
        if (header != null && header.startsWith(this.tokenHead)) {
            String token = header.substring(tokenHead.length()); // The part after "Bearer "
            String username = this.getUsernameFromToken(token);
            User user = userService.findByMobile(username);
            return user;
        }
        return null;
    }
}

