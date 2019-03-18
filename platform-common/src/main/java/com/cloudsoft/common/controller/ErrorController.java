package com.cloudsoft.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhujianrong
 * @date 2018-9-13
 */
@RestController
@RequestMapping("/error")
@Slf4j
public class ErrorController extends BaseController {

    @RequestMapping("/errorToken")
    public String errorToken() {
        return getLoginFailureInfo();
    }

    /**
     * 没有进行登录认证
     * @return
     */
    @RequestMapping("/getNoLogin")
    public String getNoLogin() {
    	return getNoLoginInfo();
    }
    
    /**
     * 没有访问权限
     * @return
     */
    @RequestMapping("/getUnauthorized")
    public String getUnauthorized(){
    	return getUnauthorizedInfo();
    }
}
