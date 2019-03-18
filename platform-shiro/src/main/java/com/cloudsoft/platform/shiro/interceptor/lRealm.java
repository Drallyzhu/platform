package com.cloudsoft.platform.shiro.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cloudsoft.logger.LoggerUtil;
import com.cloudsoft.platform.shiro.enums.StatusEnum;
import com.cloudsoft.platform.shiro.model.ShiroResource;
import com.cloudsoft.platform.shiro.model.ShiroUser;
import com.cloudsoft.platform.shiro.repository.ShiroResourceRepository;
import com.cloudsoft.platform.shiro.repository.ShiroUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class lRealm extends AuthorizingRealm {

	private static final Logger LOGGER = LoggerFactory.getLogger(lRealm.class);

	@Autowired
	private ShiroUserRepository shiroUserRepository;
	@Autowired
	private ShiroResourceRepository shiroResourceRepository;
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		LoggerUtil.info(LOGGER,"------------------执行授权操作-----------------");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获取登录用户所有资源
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		if (shiroUser != null) {
			List<ShiroResource> resources = shiroResourceRepository.getUserResAll(shiroUser.getId(), StatusEnum.VALID.value());
//			List<ShiroResource> resources = new ArrayList<>();
			if (resources!=null && resources.size()>0) {
				for (com.cloudsoft.platform.shiro.model.ShiroResource ShiroResource : resources) {
					if (StringUtils.isNotBlank(ShiroResource.getResourceUrl()) && StringUtils.isNotBlank(ShiroResource.getGrandKey())) {
						info.addStringPermission(ShiroResource.getGrandKey());
					}
				}
			}
		}
//		info.addRole("adminss"); //角色控制权限
		return info;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		LoggerUtil.info(LOGGER,"----------------执行shiro认证,获取到的用户名为:{0}-----------------",token.getUsername());
		Optional<ShiroUser> optional = shiroUserRepository.findByLoginName(token.getUsername());
		if (!optional.isPresent()) {
			// 用户名不存在,返回null给shiro即可
			return null;
		}
		ShiroUser shiroUser = optional.get();
		//shiro自己也有session,将loginUser放入它自己的session给自己用,第一个参数principal变量中
		//第二个参数为从数据库取出的用户验证的用户密码
		LoggerUtil.info(LOGGER,"获取到用户信息ShiroUser={0}",JSONObject.toJSONString(shiroUser));
		return new SimpleAuthenticationInfo(shiroUser,shiroUser.getPassword(),shiroUser.getLoginName());
	}

}
