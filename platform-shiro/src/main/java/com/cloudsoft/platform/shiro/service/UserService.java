package com.cloudsoft.platform.shiro.service;

import com.cloudsoft.common.domain.ResultData;
import com.cloudsoft.platform.shiro.model.ShiroUser;
import com.cloudsoft.platform.shiro.model.dto.ShiroUserDto;

/**
 * @author zhujianrong
 */
public interface UserService {

	/**
	 * 用户登录
	 * @param account 用户名
	 * @param password 密码
	 * @return
	 */
	public ResultData login(String account, String password);

	/**
	 * 用户注销
	 * @param userId 用户ID
	 * @return
	 */
	public void loginOut(String userId);

	/**
	 * 新增用户（绑定角色）
	 * @param shiroUserDto 用户实体类
	 * @return
	 */
	 ResultData addUser(ShiroUserDto shiroUserDto);

	/**
	 * 更新用户
	 * @param shiroUserDto 用户实体类
	 * @return
	 */
	ResultData updateUser(ShiroUserDto shiroUserDto);

	/**
	 *  根据用户搜索条件获取列表
	 * @param shiroUserDto 对象
	 * @param pageNum 显示页数
	 * @param pageSize  第几页
	 * @return
	 */
	ResultData  getUserList(ShiroUserDto shiroUserDto, Integer pageNum, Integer pageSize);

	/**
	 * 变更用户状态
	 * @param userId 用户id
	 * @param status  用户状态
	 * @return
	 */
	ResultData updateUserStatus(String userId, String status);

	/**
	 * 根据用户ID获取用户信息
	 * @param userId 用户id
	 * @return
	 */
	ResultData getUser(String userId);

	/**
	 * 根据token获取登录用户信息
	 * @param accessToken
	 * @return
	 */
	ShiroUser getLoginUserInfo(String accessToken);


	/**
	 * 根据用户ID获取所有资源
	 * @param userId
	 * @return
	 */
	ResultData getUserResAll(String userId, String status);
}
