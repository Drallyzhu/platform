package com.cloudsoft.platform.shiro.config;


import com.cloudsoft.common.util.IdWorkerUtils;
import com.cloudsoft.logger.LoggerUtil;
import com.cloudsoft.platform.shiro.constant.SignConstant;
import com.cloudsoft.platform.shiro.enums.StatusEnum;
import com.cloudsoft.platform.shiro.interceptor.lRealm;
import com.cloudsoft.platform.shiro.model.ShiroResource;
import com.cloudsoft.platform.shiro.repository.ShiroResourceRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

@Configuration
@Getter
@Setter
@Order(5)
public class ShiroConfiguration {
    private static final  Logger LOGGER = LoggerFactory.getLogger(ShiroConfiguration.class);

	@Autowired
	private ShiroResourmapstructceRepository shiroResourceRepository;

    @Autowired
    private Environment environment;


	//  SHIRO核心拦截器配置  
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        //  无权限跳转url
        String unauthorizedUrl =  environment.getProperty("shiro.unauthorizedUrl");
        // 没有登录跳转url
        String loginUrl = environment.getProperty("shiro.loginUrl");
        // 是否添加Url执行shiro认证和授权,开发环境为N,测试和生产环境必须为Y
        String isShiroAuthcAndPerms = environment.getProperty("shiro.isShiroAuthcAndPerms");
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 必须设置SecuritManager
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl(loginUrl);
        bean.setUnauthorizedUrl(unauthorizedUrl);
        //配置访问权限  
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<String, String>(); 
        LoggerUtil.info(LOGGER,"---------start----------开始加入需要授权和认证访问的url---------------------");
        if ("Y".equals(isShiroAuthcAndPerms)) {
        	 // 查询数据库中所有的资源
            Sort sort = new Sort(Sort.Direction.ASC,"sort");
            List<ShiroResource> resources = shiroResourceRepository.findAllByStatus(StatusEnum.VALID.value(),sort);
//            filterChainDefinitionMap.put("/api/oms/user/loginOut", "logout");
//            if (resources != null && resources.size() > 0) {
//                for (ShiroResource ShiroResource : resources) {
//                    String resourceUrlUrl = ShiroResource.getResourceUrl();
//                    String grandKey = ShiroResource.getGrandKey();
//                    if (StringUtils.isNotBlank(resourceUrlUrl)&&StringUtils.isNotBlank(grandKey)) {
//                        filterChainDefinitionMap.put(resourceUrlUrl,"perms["+grandKey+"]");
//                    }
//                }
//            }

            //角色拦截
            //filterChainDefinitionMap.put("/user", "authc,roles[user]");
            //filterChainDefinitionMap.put("/admin", "authc,roles[user,admin]");


            filterChainDefinitionMap.put("/actuator/health", "anon");
            filterChainDefinitionMap.put("/swagger-ui.html", "anon");
            filterChainDefinitionMap.put("/swagger-resources/**", "anon");
            filterChainDefinitionMap.put("/images/**","anon");
            filterChainDefinitionMap.put("/webjars/**","anon");
            filterChainDefinitionMap.put("/v2/api-docs","anon");
            filterChainDefinitionMap.put("/configuration/ui","anon");
            filterChainDefinitionMap.put("/configuration/security","anon");
            filterChainDefinitionMap.put("/upload/image","anon");
            filterChainDefinitionMap.put("/**", "authc");//拦截所有请求
            for (Entry<String, String> entry : filterChainDefinitionMap.entrySet()) {
                LoggerUtil.info(LOGGER,"--------------"+entry.getKey()+" = "+entry.getValue());
    		}
		} else {
            LoggerUtil.info(LOGGER,"=================没有添加url执行shiro的认证和授权===================");
		}
        LoggerUtil.info(LOGGER,"-------------------加入需要授权和认证访问的url结束------------end-----------");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean ;  
    }  

	/**
     * 拦截器
     * delegatingFilterProxy方法参考http://www.cnblogs.com/ginponson/p/6217057.html 
     * 修复Spring Boot整合shiro出现UnavailableSecurityManagerException 问题 
     * 此处设置相当于在web.xml中增加filter 
     * */  
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);  
        proxy.setTargetBeanName("shiroFilter");  
        filterRegistrationBean.setFilter(proxy);  
        return filterRegistrationBean;  
    }
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(Realm());
        return securityManager;
    }
   
    @Bean
    public lRealm Realm() {
        lRealm omsRealm = new lRealm();
        return omsRealm;
    }
    

    

    
    /** 
     * 配置shiro redisManager 
     * <p> 
     * 使用的是shiro-redis开源插件 使用配置redis
     * 
     * @return 
     */  
    public RedisManager redisManager() {
        String host = environment.getProperty("spring.redis.host");
        int port = Integer.parseInt(environment.getProperty("spring.redis.port"));
        int timeout = Integer.parseInt(environment.getProperty("spring.redis.jedis.pool.timeout"));
        String password = environment.getProperty("spring.redis.password");
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire((int) SignConstant.USER_TOKEN_TTL);// 配置缓存过期时间
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }
   
    /** 
     * cacheManager 缓存 redis实现 
     * <p> 
     * 使用的是shiro-redis开源插件 
     * 
     * @return 
     */  
    @Bean(name = "shiroCacheManager")
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public MyRedisSessionDAO redisSessionDAO() {
        MyRedisSessionDAO redisSessionDAO = new MyRedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 注册ID生成工具
     * @return
     */
    @Bean
    public IdWorkerUtils getIdWorker(){
        IdWorkerUtils ic = new IdWorkerUtils(
                Long.parseLong(environment.getProperty("idWorker.workerId")),
                Long.parseLong(environment.getProperty("idWorker.datacenterId")));
        return ic;
    }
    
}
