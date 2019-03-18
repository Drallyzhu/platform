package com.cloudsoft.security.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 权限配置
 */
@Component
public class AccessConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    //如果要引用这个模块，记得模块那边要配置
    @Value("${cloudsoft.service_token}")
    private String srvToken = null;

    public String getSrvToken() {
        return srvToken;
    }

    public void setSrvToken(String srvToken) {
        this.srvToken = srvToken;
    }
}
