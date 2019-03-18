package com.cloudsoft.platform.securityoauth2.security;

import com.cloudsoft.platform.securityoauth2.jwt.JwtAuthenticationEntryPoint;
import com.cloudsoft.platform.securityoauth2.jwt.filter.SecurityAuthenticationFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Environment environment;
    
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                // 设置UserDetailsService
                .userDetailsService(this.userDetailsService)
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
    }
    // 装载BCrypt密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new SecurityAuthenticationFilter();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()


                .authorizeRequests()



                // 允许访问的资源
                .antMatchers(authWhitelist()).permitAll()


                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/auth/**").permitAll()

                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 禁用缓存
        httpSecurity.headers().cacheControl();

        // 添加(Security拦截器，jwt做校验) filter
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 需要放行的URL
     */
    private  String[] authWhitelist (){
        StringBuffer list = new StringBuffer();
        list.append("/swagger-ui.html").append(",");
        list.append("/swagger-resources/**").append(",");
        list.append("/images/**").append(",");
        list.append("/webjars/**").append(",");
        list.append("/v2/api-docs").append(",");
        list.append("/configuration/security").append(",");
        list.append("/configuration/ui");
        String whitelist = environment.getProperty("jwt.whitelist");
        /*这段是配置文件加入，配置文件记得以 xxx,xxxx,xxx（结尾不要逗号）*/
        if(StringUtils.isNotBlank(whitelist))list.append(",").append(whitelist);
        return whitelist.toString().split(",");
    }

}
