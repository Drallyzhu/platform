package com.cloudsoft.platform.shiro.service.impl;

import com.cloudsoft.common.domain.PageData;
import com.cloudsoft.common.domain.ResultData;
import com.cloudsoft.common.util.ElementsUtils;
import com.cloudsoft.common.util.IdWorkerUtils;
import com.cloudsoft.logger.LoggerUtil;
import com.cloudsoft.platform.shiro.enums.StatusEnum;
import com.cloudsoft.platform.shiro.model.ShiroResource;
import com.cloudsoft.platform.shiro.model.ShiroRole;
import com.cloudsoft.platform.shiro.model.ShiroRoleResource;
import com.cloudsoft.platform.shiro.model.dto.BindRoleDto;
import com.cloudsoft.platform.shiro.model.dto.ShiroRoleDto;
import com.cloudsoft.platform.shiro.repository.ShiroResourceRepository;
import com.cloudsoft.platform.shiro.repository.ShiroRoleRepository;
import com.cloudsoft.platform.shiro.repository.ShiroRoleResourceRepository;
import com.cloudsoft.platform.shiro.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private ShiroRoleRepository shiroRoleRepository;
	@Autowired
	private ShiroResourceRepository shiroResourceRepository;
	@Autowired
	private ShiroRoleResourceRepository shiroRoleResourceRepository;

	private IdWorkerUtils idWorkerUtils = new IdWorkerUtils();

	/**
	 * 获取角色列表(不分页)
	 * @param userId 用户id
	 * @return ResultData
	 * 			<0000:操作成功>
	 * 			<9999:操作失败>
	 */
	@Override
	public ResultData getAllRole(String userId) {
		LoggerUtil.info(LOGGER,"---------start----------执行获取角色列表(不分页),获取到的请求参数为userId:{0}",userId);
		try {
			// 获取所有的角色列表
			List<ShiroRole> data = shiroRoleRepository.selectAllAndStatus(StatusEnum.VALID.value());
			List<BindRoleDto> dataDto = new ArrayList<>();
			if (ElementsUtils.isNotBlank(data)) {
				for (ShiroRole shiroRole : data) {
					BindRoleDto bindRoleDto = new BindRoleDto();
					BeanUtils.copyProperties(shiroRole,bindRoleDto);
					dataDto.add(bindRoleDto);
				}
				if (userId != null) {
					// 获取用户绑定的角色id列表
					List<String> roleIds = shiroRoleRepository.selectIdsByUserId(userId);
					if (ElementsUtils.isNotBlank(roleIds)) {
						for (BindRoleDto bindRoleDto : dataDto) {
							if (roleIds.contains(bindRoleDto.getId())) {
								bindRoleDto.setChecked(true);
							}
						}
					}
				}
			}
			return ResultData.SUCESS.setData(dataDto);
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"userId:{0},在用户绑定角色,获取角色列表时,发生系统异常:{1}",userId,e);
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}
	/**
	 * 分页获取角色列表
	 * @param pageSize 页面大小
	 * @param pageNum 当前页
	 * @return ResultData
	 * 			<0000:操作成功>
	 * 			<9999:操作失败>
	 */
	@Override
	public ResultData getAllRoleByPage(int pageSize, int pageNum) {
		LoggerUtil.info(LOGGER,"------start-----开始执行分页获取角色列表,获取到的请求参数pageSize:{0},pageNum:{1}",pageSize,pageNum);
		try {
			Pageable pageable = new PageRequest(pageNum,pageSize, Sort.Direction.DESC);
			Page<ShiroRole> page = shiroRoleRepository.findAll(pageable);
			List<ShiroRoleDto> dataDto = new ArrayList<>();
			if (ElementsUtils.isNotBlank(page.getContent())) {
				for (ShiroRole shiroRole : page.getContent()) {
					ShiroRoleDto shiroRoleDto = new ShiroRoleDto();
					BeanUtils.copyProperties(shiroRoleDto, shiroRole);
					dataDto.add(shiroRoleDto);
				}
			}
			// 封装到pageData中
			PageData<ShiroRoleDto> pageData = new PageData<>();
			pageData.setData(dataDto);
			pageData.setPageNo(pageNum);
			pageData.setPages(page.getNumber());
			pageData.setPageSize(pageSize);
			pageData.setTotal(page.getTotalPages());
			return ResultData.SUCESS.setData(pageData);
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"在分页获取角色列表时,发生系统异常:{0}",e);
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}


	/**
	 * 新增角色信息
	 * @param roleName 角色名称
	 * @param description 描述
	 * @return
	 */
	@Override
	public ResultData saveRole(String roleName, String description) {
		try {
			ShiroRole role = new ShiroRole();
			String id = idWorkerUtils.toString(idWorkerUtils.nextId());
			role.setId(id);
			role.setRoleName(roleName);
			role.setDescription(description);
//			role.setCreateTime(DateUtil.getCurrentDate());
			role.setStatus(StatusEnum.VALID.value());
			shiroRoleRepository.save(role);
			return ResultData.getSuccessData(id);
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"roleName:{},description:{0}在保存角色时,发生系统异常{1}",roleName,description,e);
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}

	/**
	 * 修改角色信息
	 * @param roleId 角色id
	 * @param roleName 角色名称
	 * @param description 描述
	 * @return 
	 */
	@Override
	public ResultData updateRole(String roleId, String roleName, String description) {
		try {
			Optional<ShiroRole> op = shiroRoleRepository.findById(roleId);
			if (!op.isPresent()) {
				LoggerUtil.warn(LOGGER,"id:{0},roleName:{1},description:{2},在修改角色信息时,角色信息不存在",roleId,roleName,description);
				return ResultData.ERROR("要修改的角色信息不存在");
			}
			ShiroRole role = op.get();
			role.setRoleName(roleName);
			role.setDescription(description);
			shiroRoleRepository.save(role);
			return ResultData.getSuccessResult("修改角色成功！");
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"id:{0},roleName:{1},description:{2},在修改角色信息时,发生系统异常{3}",roleId,roleName,description,e);
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}

	/**
	 * 角色绑定资源
	 * @param roleId 角色id
	 * @param resourceIds 资源ids
	 * @return
	 */
	@Transactional
	@Override
	public ResultData bindResource(String roleId, String resourceIds) {
		try {
			String RepeatResourceId = ElementsUtils.checkIsRepeat(resourceIds.replaceAll(" ", ""));//检验资源id有没有存在
			if(StringUtils.isNotBlank(RepeatResourceId)){
				return ResultData.getFailResult("资源ID有重复，重复ID是:{"+RepeatResourceId+"}");
			}
			LoggerUtil.info(LOGGER,"-------start------执行角色绑定资源,获取到的请求参数id:{0},resourceIds:{1}",roleId,resourceIds);
			Optional<ShiroRole> opRole = shiroRoleRepository.findById(roleId);
			if (!opRole.isPresent()) {
				LoggerUtil.warn(LOGGER,"角色id:{0},该角色信息不存在",roleId);
				return ResultData.ERROR("该角色信息不存在");
			}
			// 判断资源ids是否存在
			if (ElementsUtils.isNotBlank(resourceIds)) {
				String[] resourceIdArr = resourceIds.split(",");
				for (String resourceId : resourceIdArr) {
					// 查找父级id并添加
					while (true) {
						Optional<ShiroResource> opResource = shiroResourceRepository.findById(resourceId);
						if (!opResource.isPresent()) {
							LoggerUtil.warn(LOGGER,"资源resourceIds:{0},其中resourceId为{1},该资源信息不存在",resourceIds,resourceId);
							return ResultData.ERROR("资源id为"+resourceId+",该资源信息不存在");
						}
						ShiroResource resource = opResource.get();
						String parentId = resource.getParentId();
						// 默认为0时没有父级
						if ("0".equals(parentId) || StringUtils.isBlank(parentId)) {
							break;
						}
//						String parentIdStr = String.valueOf(parentId);
						if (!resourceIds.contains(parentId)) {
							resourceIds += ("," + parentId);
							resourceId = parentId;
						} else {
							break;
						}
					}
				}
			}
			//角色绑定资源
			// 1.删除含有该角色id的中间表
			shiroRoleResourceRepository.deleteByRoleId(roleId);
			// 2.角色绑定资源ids
			if (ElementsUtils.isNotBlank(resourceIds)) {
				LoggerUtil.info(LOGGER,"角色绑定资源，角色id:{0}, 查到父级id并添加后需绑定的资源resourceIds={1}", roleId , resourceIds);
				String[] resourceIdArr = resourceIds.split(",");
				for (String resourceId : resourceIdArr) {
					ShiroRoleResource roleResource = new ShiroRoleResource();
					roleResource.setId(idWorkerUtils.toString(idWorkerUtils.nextId()));
					roleResource.setRoleId(roleId);
					roleResource.setResourceId(resourceId);
					roleResource.setStatus(StatusEnum.VALID.value());
					shiroRoleResourceRepository.save(roleResource);
				}
			}
			return ResultData.SUCESS;
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"在角色绑定资源时,角色id:{0},resourceIds:{1},发生系统异常{2}",roleId,resourceIds,e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}
	
}
