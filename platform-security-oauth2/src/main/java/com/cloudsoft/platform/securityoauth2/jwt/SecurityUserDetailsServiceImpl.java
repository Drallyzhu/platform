package com.cloudsoft.platform.securityoauth2.jwt;

import com.cloudsoft.platform.securityoauth2.domain.User;
import com.cloudsoft.platform.securityoauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 实现security用户模块逻辑
 */
@Service
public class SecurityUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByCode(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return SecurityUserFactory.create(user);
        }
    }


}
