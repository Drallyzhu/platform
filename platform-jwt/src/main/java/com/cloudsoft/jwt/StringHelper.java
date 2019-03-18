package com.cloudsoft.jwt;

/**
 * 
 * @author zhujianrong
 * 简单校验
 *
 */
public class StringHelper {
    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }
}
