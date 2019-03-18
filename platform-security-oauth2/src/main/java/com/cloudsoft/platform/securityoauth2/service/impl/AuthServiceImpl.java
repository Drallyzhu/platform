package com.cloudsoft.platform.securityoauth2.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloudsoft.common.domain.ResultData;
import com.cloudsoft.common.util.Base64Utils;
import com.cloudsoft.common.util.IdWorkerUtils;
import com.cloudsoft.common.util.RedisUtils;
import com.cloudsoft.platform.securityoauth2.domain.Power;
import com.cloudsoft.platform.securityoauth2.domain.Role;
import com.cloudsoft.platform.securityoauth2.domain.User;
import com.cloudsoft.platform.securityoauth2.domain.dto.UserDTO;
import com.cloudsoft.platform.securityoauth2.jwt.JwtAuthenticationResponse;
import com.cloudsoft.platform.securityoauth2.jwt.JwtTokenUtil;
import com.cloudsoft.platform.securityoauth2.jwt.SecurityUser;
import com.cloudsoft.platform.securityoauth2.repository.UserRepository;
import com.cloudsoft.platform.securityoauth2.service.AuthService;
import com.cloudsoft.platform.securityoauth2.service.PowerService;
import com.cloudsoft.platform.securityoauth2.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private IdWorkerUtils idWorker;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PowerService powerService;

    @Autowired
    private RedisUtils redisService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository,
            IdWorkerUtils idWorker) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.idWorker = idWorker;
    }

    @Override
    public ResultData register(UserDTO userDTO) {
        final String username = userDTO.getUsername();
        if(null != userRepository.findByCode(username) || null != userRepository.findByMobile(username)) {
            return ResultData.getFailResult("用户名已存在");
        }
        User user = new User();
        user.setCode(userDTO.getUsername());
        user.setPwd(userDTO.getPassword());
        user.setMobile(userDTO.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPwd();
        user.setPwd(encoder.encode(rawPassword));
        user.setCreateTime(new Date());
        user.setStatus("0");
        user.setExamine("0");
        user.setRole("ROLE_CUSTOMER");
        user.setId(idWorker.nextIdByString());
        return ResultData.SUCESS.setData(userRepository.save(user));
    }

    @Override
    public ResultData login(String username, String password) {
        User user = userRepository.findByCode(username);
        if (null == user || (!"Admin".equals(user.getRole()) && !"ROLE_STAFF".equals(user.getRole())) ){
            return ResultData.getFailResult("用户不存在");
        }else if ("1".equals(user.getIsDelete()) || "1".equals(user.getStatus()) ){
            return ResultData.getFailResult("用户已冻结");
        }
        try {
            password = Base64Utils.decryptBASE64(password);
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            final String token = jwtTokenUtil.generateToken(userDetails);

            // 加载权限
            List<String> authorities = new ArrayList<String>();
            List<Role> roles = roleService.findByUserId(user.getId());
            for (Role role : roles){
                List<Power> powers = powerService.findByRole(role.getId());
                for (Power power : powers){
                    authorities.add(power.getCode());
                }
            }
            redisService.set("Security:Authorities:" + user.getId(), JSON.toJSONString(authorities));
            // 更新最后登录时间
            user.setLastLoginTime(new Date());
            userRepository.save(user);
            return ResultData.SUCESS.setData(new JwtAuthenticationResponse(token));
        } catch (BadCredentialsException e) {
            return ResultData.getFailResult("用户名或密码错误");
        }catch (Exception e) {
            return ResultData.getFailResult("系统错误");
        }

    }

    @Override
    public ResultData refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        SecurityUser user = (SecurityUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return ResultData.SUCESS.setData(token);
        }
        return ResultData.getFailResult();
    }
}
