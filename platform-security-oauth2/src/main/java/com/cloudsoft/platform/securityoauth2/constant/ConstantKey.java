package com.cloudsoft.platform.securityoauth2.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * 描述：
 * <p>
 *
 * @author zhujianrong
 */
public class ConstantKey {

    @Autowired
    private Environment environment;

    /**
     * 签名key(jwt)
     */
    public static  String JWT_SIGNING_KEY;

    ConstantKey(){
        JWT_SIGNING_KEY =  environment.getProperty("jwt.singningKey");
    }
}
