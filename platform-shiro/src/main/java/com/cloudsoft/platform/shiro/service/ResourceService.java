package com.cloudsoft.platform.shiro.service;

import com.cloudsoft.common.domain.ResultData;
import com.cloudsoft.platform.shiro.model.dto.ShiroResourceDto;

public interface ResourceService {

	/**
	 * 新增资源
	 * @param shiroResourceDto 资源封装对象
	 * @return
	 */
	ResultData saveResource(ShiroResourceDto shiroResourceDto);

	/**
	 * 修改资源
	 * @param shiroResourceDto 资源封装对象
	 * @return
	 */
	ResultData updateResource(ShiroResourceDto shiroResourceDto);

	/**
	 * 获取资源列表(分页)
	 * @param pageSize 第几页
	 * @param pageNum 显示多少条
	 * @return
	 */
	ResultData getAllResource(Integer pageNum, Integer pageSize);

	/**
	 * 根据角色id获取资源列表
	 * @param roleId
	 * @return
	 */
	ResultData getResourceList(String roleId);
	
}
