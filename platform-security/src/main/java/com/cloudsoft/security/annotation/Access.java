package com.cloudsoft.security.annotation;

import com.cloudsoft.security.enumeration.AccessLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhujianrong
 * 自定义注解，用法：
 * @Access(level = AccessLevel.INTERNAL)
 * AccessLevel.INTERNAL 是权限等级，可以设置，这是第一种权限设置，每一种设置，导致调用者要根据设置需要在herd要带指定参数来判断
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Access {

    AccessLevel level() default AccessLevel.USER;
}
