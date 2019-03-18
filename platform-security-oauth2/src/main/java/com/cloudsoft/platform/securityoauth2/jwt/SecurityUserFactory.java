package com.cloudsoft.platform.securityoauth2.jwt;

import com.cloudsoft.platform.securityoauth2.domain.Role;
import com.cloudsoft.platform.securityoauth2.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 生成jwtUser对象以及数据封装
 */

public final class SecurityUserFactory {

    private SecurityUserFactory() {
    }

    public static SecurityUser create(User user) {
        List<String> roles = new ArrayList<>();
        if (null != user.getRoles() && user.getRoles().isEmpty()){
            for (Role role : user.getRoles()){
                roles.add(role.getCode());
            }
        }
        return new SecurityUser(
                user.getId(),
                user.getCode(),
                user.getPwd(),
                mapToGrantedAuthorities(roles),
                user.getLastLoginTime()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
