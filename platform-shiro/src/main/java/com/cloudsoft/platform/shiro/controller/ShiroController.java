package com.cloudsoft.platform.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloudsoft.common.controller.BaseController;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShiroController extends BaseController {

	/**
	 * 登录认证异常
	 */
	@ExceptionHandler({ UnauthenticatedException.class, AuthenticationException.class })
	protected String authenticationException(HttpServletRequest request, HttpServletResponse response) {
			// 输出JSON
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", "-999");
		resultObject.put("msg", "没有登录，请登录!");
		resultObject.put("responseData","");
		return resultObject.toJSONString();
	}

	/**
	 * 权限异常
	 */
	@ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
	protected String authorizationException(HttpServletRequest request, HttpServletResponse response) {
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", "-998");
		resultObject.put("msg", "没有当前资源权限！");
		resultObject.put("responseData","");
		return resultObject.toJSONString();
	}



}
