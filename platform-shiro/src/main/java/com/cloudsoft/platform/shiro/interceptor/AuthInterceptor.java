package com.cloudsoft.platform.shiro.interceptor;

import com.cloudsoft.logger.LoggerUtil;
import com.cloudsoft.platform.shiro.annotation.Authentication;
import com.cloudsoft.platform.shiro.enums.SystemEnum;
import com.cloudsoft.platform.shiro.model.ShiroUser;
import com.cloudsoft.platform.shiro.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);

	@Autowired
	private UserService userService;

	@Autowired
	private Environment environment;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) o;
		Method method = handlerMethod.getMethod();
		// 取权限
		Authentication authentication = method.getAnnotation(Authentication.class);
		String isUnTonken = environment.getProperty("shiro.isUnTonken");
		if (authentication != null && StringUtils.isNotBlank(isUnTonken) && SystemEnum.ISSHIROAUTHCANDPERMSOFTRUE.value().equalsIgnoreCase(isUnTonken)) {
			String accessToken = request.getHeader("accessToken");
			LoggerUtil.info(LOGGER,"preHandle get token:{0}", accessToken);
			ShiroUser shiroUser = userService.getLoginUserInfo(accessToken);
			if (null != shiroUser) {
				//封装用户信息，当方法参数传入调用方法，方便获取数据，不用多次调用底层方法查询
				Map<String, Object> userInfo = new HashMap<String, Object>();
				userInfo.put("userId", shiroUser.getId());
				request.setAttribute("userInfo", userInfo);
				return true;
			} else {
				//重定向到封装错误信息控制层
				request.getRequestDispatcher("/error/errorToken").forward(request, response);
				return false;
			}
		}
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
//		System.out.println("------------------------postHandle------------------------");

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
//		System.out.println("------------------------afterCompletion------------------------");
	}
}
