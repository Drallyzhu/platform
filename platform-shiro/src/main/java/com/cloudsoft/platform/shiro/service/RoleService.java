package com.cloudsoft.platform.shiro.service;


import com.cloudsoft.common.domain.ResultData;

public interface RoleService {

	/**
	 * 获取角色列表(不分页)
	 * @return
	 */
	ResultData getAllRole(String userId);

	/**
	 * 分页获取角色列表
	 * @param pageSize 页面大小
	 * @param pageNum 当前页
	 * @return
	 */
	ResultData getAllRoleByPage(int pageSize, int pageNum);

	/**
	 * 新增角色信息
	 * @param roleName 角色名称
	 * @param description 描述
	 * @return
	 */
	ResultData saveRole(String roleName, String description);

	/**
	 * 修改角色信息
	 * @param roleId 角色id
	 * @param roleName 角色名称
	 * @param description 描述
	 * @return
	 */
	ResultData updateRole(String roleId, String roleName, String description);

	/**
	 * 角色绑定资源
	 * @param roleId 角色id
	 * @param resourceIds 资源id(多个)
	 * @return
	 */
	ResultData bindResource(String roleId, String resourceIds);
	
}
