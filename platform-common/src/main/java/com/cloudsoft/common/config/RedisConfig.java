package com.cloudsoft.common.config;

import com.alibaba.fastjson.JSON;
import com.cloudsoft.common.util.BeanUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;

/**
 * 自动生成ID键值对
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                char sp = ':';
                sb.append(target.getClass()).append(sp);
                sb.append(method.getName()).append(sp);
                if (params.length > 0){
                    for (Object obj : params) {
                        if (BeanUtils.isBaseType(obj.getClass())) {
                            sb.append(obj);
                        } else {
                            sb.append(JSON.toJSON(obj).hashCode());
                        }
                    }
                }else{
                    sb.append(0);
                }
                return sb.toString();
            }
        };
    }

//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        //cacheManager.setDefaultExpiration(86400);
//        return cacheManager;
//    }

//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        RedisCacheManager cacheManager = RedisCacheManager.create(connectionFactory);
//        //cacheManager.setDefaultExpiration(86400);
//        return cacheManager;
//    }
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(3)); // 设置缓存有效期3小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }


    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }
}