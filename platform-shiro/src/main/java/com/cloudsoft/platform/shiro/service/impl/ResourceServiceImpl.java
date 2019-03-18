package com.cloudsoft.platform.shiro.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.cloudsoft.common.domain.ResultData;
import com.cloudsoft.common.util.ElementsUtils;
import com.cloudsoft.common.util.IdWorkerUtils;
import com.cloudsoft.logger.LoggerUtil;
import com.cloudsoft.platform.shiro.model.ShiroResource;
import com.cloudsoft.platform.shiro.model.ShiroResourceExmple;
import com.cloudsoft.platform.shiro.model.dto.BindResourceDto;
import com.cloudsoft.platform.shiro.model.dto.ShiroResourceDto;
import com.cloudsoft.platform.shiro.repository.ShiroResourceRepository;
import com.cloudsoft.platform.shiro.service.ResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author zhujianrong
 * @version 创建时间：2018-9-12
 */
@Service
public class ResourceServiceImpl implements ResourceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

	@Autowired
	private ShiroResourceRepository shiroResourceRepository;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private IdWorkerUtils idWorkerUtils = new IdWorkerUtils();
	
	/**
	 * 新增资源
	 * @param shiroResourceDto 资源封装类
	 * @return
	 */
	@Override
	public ResultData saveResource(ShiroResourceDto shiroResourceDto) {
		try {
			LoggerUtil.info(LOGGER,"--------start-----执行新增资源信息,获取到的请求对象 shiroResourceDto:{0}", JSONObject.toJSONString(shiroResourceDto));
			if(checkRepeatFormResourceUrlAndKey(shiroResourceDto)) {
				ShiroResource resource = new ShiroResource();
				BeanUtils.copyProperties(shiroResourceDto, resource);
				resource.setId(idWorkerUtils.toString(idWorkerUtils.nextId()));
				shiroResourceRepository.save(resource);
				return ResultData.getSuccessResult("新增资源成功");
			}else{
				return ResultData.getFailResult("你添加的资源权限已存在，请检测key和url！");
			}
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"在新增资源时,发生系统异常{0}",e);
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}

	/**
	 * 修改资源
	 * @param shiroResourceDto 资源封装类
	 * @return
	 */
	@Override
	public ResultData updateResource(ShiroResourceDto shiroResourceDto) {
		try {
			LoggerUtil.info(LOGGER,"--------start-----执行修改资源信息,获取到的请求参数shiroResourceDto:{0}", JSONObject.toJSONString(shiroResourceDto));
			Optional<ShiroResource> resourceOp = shiroResourceRepository.findById(shiroResourceDto.getId());
			if (!resourceOp.isPresent()) {
				LoggerUtil.warn(LOGGER,"修改资源信息时,资源id:{0},该资源信息不存在",shiroResourceDto.getId());
				return ResultData.ERROR("资源信息不存在");
			}
			ShiroResource resource = resourceOp.get();
			BeanUtils.copyProperties(shiroResourceDto,resource);
			shiroResourceRepository.save(resource);
			return ResultData.SUCESS;
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"在新增资源时,发生系统异常{0}",e);
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}

	/**
	 * 获取资源列表(分页)
	 */
	@Override
	public ResultData getAllResource(Integer pageNum,Integer pageSize) {
		try {
			Pageable pageable = new PageRequest(pageNum,pageSize);
			Page<ShiroResource> pShiroResource = shiroResourceRepository.findAll(pageable);
			List<ShiroResource> data = pShiroResource.getContent();
			List<ShiroResourceDto> dataDto = new ArrayList<>();
			if (ElementsUtils.isNotBlank(data)) {
				for (ShiroResource shiroResource : data) {
					ShiroResourceDto shiroResourceDto = new ShiroResourceDto();
					BeanUtils.copyProperties(shiroResource, shiroResourceDto);
					dataDto.add(shiroResourceDto);
				}
			}
			return ResultData.SUCESS.setData(dataDto);
		} catch (Exception e) {
			LoggerUtil.warn(LOGGER,"在获取资源列表(不分页)时,发生系统异常{0}",e);
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}

	/**
	 * 根据角色id获取资源列表
	 * @param roleId
	 * @return
	 */
	@Override
	public ResultData getResourceList(String roleId) {
		try {
//			List<ShiroResource> data = shiroResourceRepository.getResourcesByRole(roleId);
			List<ShiroResource> data = new ArrayList<>();
			List<BindResourceDto> dataDto = new ArrayList<>();
			if (ElementsUtils.isNotBlank(data)) {
				for (ShiroResource shiroResource : data) {
					BindResourceDto resourceDto = new BindResourceDto();
					BeanUtils.copyProperties(shiroResource,resourceDto);
					resourceDto.setChecked(true);//已绑定
					dataDto.add(resourceDto);
				}
//				if (roleId != null) {
//					// 获取资源绑定的角色id列表
//					List<Long> resourceIds = shiroResourceMapper.selectIdsByRoleId(roleId);
//					if (AppUtils.isNotBlank(resourceIds)) {
//						for (BindResourceDto bindResourceDto : dataDto) {
//							if (resourceIds.contains(bindResourceDto.getId())) {
//								bindResourceDto.setChecked(true);
//							}
//						}
//					}
//				}
			}
			return ResultData.SUCESS.setData(dataDto);
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"roleId:{0},在角色绑定资源,获取资源列表时,发生系统异常:{1}",roleId,e);
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}

	private boolean checkRepeatFormResourceUrlAndKey(ShiroResourceDto shiroResourceDto){
		ShiroResource queryResource = new ShiroResource();
		queryResource.setGrandKey(shiroResourceDto.getGrandKey());
		queryResource.setResourceUrl(shiroResourceDto.getResourceUrl());
		ShiroResourceExmple se = ShiroResourceExmple.createExample(queryResource);
		List listQuery = shiroResourceRepository.findAll(se);
		return listQuery == null || listQuery.isEmpty();
	}


}
