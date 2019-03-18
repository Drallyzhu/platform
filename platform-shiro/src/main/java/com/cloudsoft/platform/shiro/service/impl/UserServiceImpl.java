package com.cloudsoft.platform.shiro.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.cloudsoft.common.domain.PageData;
import com.cloudsoft.common.domain.ResultData;
import com.cloudsoft.common.util.BeanUtils;
import com.cloudsoft.common.util.ElementsUtils;
import com.cloudsoft.common.util.IdWorkerUtils;
import com.cloudsoft.logger.LoggerUtil;
import com.cloudsoft.platform.shiro.constant.SignConstant;
import com.cloudsoft.platform.shiro.model.ShiroResource;
import com.cloudsoft.platform.shiro.model.ShiroUser;
import com.cloudsoft.platform.shiro.model.ShiroUserExmple;
import com.cloudsoft.platform.shiro.model.ShiroUserRole;
import com.cloudsoft.platform.shiro.model.dto.MenuDto;
import com.cloudsoft.platform.shiro.model.dto.ShiroUserDto;
import com.cloudsoft.platform.shiro.repository.ShiroResourceRepository;
import com.cloudsoft.platform.shiro.repository.ShiroUserRepository;
import com.cloudsoft.platform.shiro.repository.ShiroUserRoleRepository;
import com.cloudsoft.platform.shiro.service.UserService;
import com.cloudsoft.platform.shiro.util.DesUtil;
import com.cloudsoft.platform.shiro.util.EncryptUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author zhujianrong
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private ShiroUserRoleRepository shiroUserRoleRepository;

	@Autowired
	private ShiroResourceRepository shiroResourceRepository;

	@Autowired
	private ShiroUserRepository shiroUserRepository;

	@Autowired
	private RedisTemplate redisTemplate;

	private IdWorkerUtils idWorkerUtils = new IdWorkerUtils();


	@Override
	public ResultData login(String account, String password) {
		LoggerUtil.info(LOGGER,"account:{"+account+"}");
		if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
			return ResultData.ERROR("用户名或密码不正确");
		}
		Subject subject = SecurityUtils.getSubject();
		String passwordByMd5 = EncryptUtil.encryptMD5(password, SignConstant.PASSWORD_SALT);
		UsernamePasswordToken shiroToken = new UsernamePasswordToken(account,passwordByMd5);
		try {
			LoggerUtil.info(LOGGER,"shirod执行login,login:{0}",shiroToken);
			subject.login(shiroToken);
		} catch (UnknownAccountException e) {
			LoggerUtil.warn(LOGGER,"account:{0},该用户名不存在",account);
			return ResultData.ERROR("该用户名不存在");
		} catch (IncorrectCredentialsException e) {
			LoggerUtil.warn(LOGGER,"account:{0},密码错误",account);
			return ResultData.ERROR("密码错误");
		}
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		if(SignConstant.INVALID.equalsIgnoreCase(shiroUser.getStatus())){ //VALID:有效  INVALID:不可用
			subject.logout();
			LoggerUtil.warn(LOGGER,"status:{0},用户已停用！", shiroUser.getStatus());
			return ResultData.ERROR("用户已停用！");
		}
		LoggerUtil.info(LOGGER,"记录最后一次登录时间");
		//记录最后一次登录时间
		shiroUser.setLastLoginTime(new Date());
		shiroUserRepository.save(shiroUser);
		//放入缓存
		LoggerUtil.info(LOGGER,"----------放入缓存");
		//生成token
		String token = DesUtil.encrypt(shiroUser.getId() + "");
		JSONObject tokenObj = new JSONObject();
		tokenObj.put("accessToken", token);
		tokenObj.put("shiroSessionId", subject.getSession().getId());
		//保存到redis
		LoggerUtil.info(LOGGER,"------------保存到redis");
		redisTemplate.opsForValue().set("token_" + shiroUser.getId(), token, SignConstant.USER_TOKEN_TTL, TimeUnit.SECONDS);
		return ResultData.SUCESS.setData(tokenObj);
	}

	@Override
	public void loginOut(String userId) {
		if(StringUtils.isNotEmpty(userId)){
			Subject subject = SecurityUtils.getSubject();
			redisTemplate.delete("token_" + userId);
			subject.logout();
			LoggerUtil.info(LOGGER,"注销id为" + userId + "成功");
		}else{
			LoggerUtil.error(LOGGER,"注销id为" + userId + "失败,失败原因为" + userId + "为空");
		}
	}

    @Transactional
	@Override
	public ResultData addUser(ShiroUserDto shiroUserDto) {
		try {
			if(check(shiroUserDto)){
                if(checkLoginName(null, shiroUserDto.getLoginName())){
					String RepeatRoleId = ElementsUtils.checkIsRepeat(shiroUserDto.getRoleIds());//检验角色id有没有重复
					if(StringUtils.isNotBlank(RepeatRoleId)){
						return ResultData.getFailResult("角色ID有重复，重复ID是:{"+RepeatRoleId+"}");
					}
					String id = idWorkerUtils.toString(idWorkerUtils.nextId());
					ShiroUser shiroUser = new ShiroUser();
					BeanUtils.copySameProperties(shiroUser,shiroUserDto);
					shiroUser.setId(id);
					if(StringUtils.isBlank(shiroUser.getPId())){
						shiroUser.setPId("root");
					}
                    shiroUser.setPassword(EncryptUtil.encryptMD5(shiroUser.getPassword(), SignConstant.PASSWORD_SALT));//md5加密
					LoggerUtil.info(LOGGER,"copy omsShiroUser：{0}", shiroUser);
					LoggerUtil.info(LOGGER,"新建用户数据");
					shiroUserRepository.save(shiroUser);
					insertByBatch(packRoleId(id, shiroUserDto.getRoleIds()));//批量插入用户角色管理表
                    return ResultData.getSuccessResult(id,"新增用户成功！");
                }else {
                    return ResultData.getFailResult("登录名已经存在");
                }
            }else{
				return ResultData.getFailResult("参数不全");
            }
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"Exception:{0}",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultData.UNKNOWN_EXCEPTION;
		}

	}


	@Transactional
	@Override
	public ResultData updateUser(ShiroUserDto shiroUserDto) {
		try {
			if(check(shiroUserDto)){
                if(checkLoginName(shiroUserDto.getId(), shiroUserDto.getLoginName())){
					String RepeatRoleId = ElementsUtils.checkIsRepeat(shiroUserDto.getRoleIds());//检验角色id有没有存在
					if(StringUtils.isNotBlank(RepeatRoleId)){
						return ResultData.getFailResult("角色ID有重复，重复ID是:{"+RepeatRoleId+"}");
					}
                    Optional<ShiroUser> opOldShiroUser = shiroUserRepository.findById(shiroUserDto.getId());
                    if(!opOldShiroUser.isPresent()){
						ResultData.getFailResult("当前用户ID:"+shiroUserDto.getId()+"没有有效信息");
					}
					ShiroUser oldShiroUser = opOldShiroUser.get();
                    ShiroUser shiroUser = copy(oldShiroUser, shiroUserDto);
					shiroUserRepository.save(shiroUser);
                    shiroUserRoleRepository.deleteByUserID(shiroUserDto.getId());
                    insertByBatch(packRoleId(shiroUserDto.getId(), shiroUserDto.getRoleIds()));
                    return ResultData.getSuccessResult("修改用户信息成功");
                }else{
                    return ResultData.getFailResult("登录名已经存在");
                }
            }else{
                return ResultData.getFailResult("参数不全");
            }
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"Exception:{0}",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}

	@Override
	public ResultData getUserList(ShiroUserDto shiroUserDto, Integer pageNum, Integer pageSize) {
		LoggerUtil.info(LOGGER,"omsShiroUserDto:{0},pageNum:{1},pageSize:{2}", shiroUserDto,pageNum,pageSize);
		ShiroUser su = new ShiroUser();
		org.springframework.beans.BeanUtils.copyProperties(shiroUserDto,su);
		ShiroUserExmple exmple = ShiroUserExmple.createExample(su);
		Pageable pageable = new PageRequest(pageNum,pageSize, Sort.Direction.DESC);
		Page<ShiroUser> pageShiroUser=shiroUserRepository.findAll(exmple,pageable);
		List<ShiroUser> expList = new ArrayList<>();
		PageData<ShiroUser> pageData = new PageData<>();
		pageData.setData(pageShiroUser.getContent());
		pageData.setPageNo(pageNum);
		pageData.setPages(pageShiroUser.getNumber());
		pageData.setPageSize(pageSize);
		pageData.setTotal(pageShiroUser.getTotalPages());
		return ResultData.getSuccessData(pageData);
	}


	@Transactional
	@Override
	public ResultData updateUserStatus(String userId, String status) {
		try {
			Optional<ShiroUser> optionalShiroUser = shiroUserRepository.findById(userId);
			if(optionalShiroUser.isPresent()){
				ShiroUser shiroUser = optionalShiroUser.get();
				if(checkStatus(status)){
					shiroUser.setStatus(status);
					shiroUserRepository.save(shiroUser);
					return ResultData.getSuccessResult("更新状态成功！");
				}else {
					return ResultData.getFailResult("状态格式不对");
				}
            }else{
                return ResultData.getFailResult("根据ID获取用户对象为空！");
            }
		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"Exception:{0}",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultData.UNKNOWN_EXCEPTION;
		}

	}

	@Override
	public ResultData getUser(String userId) {
		Optional<ShiroUser> OptionalshiroUser = shiroUserRepository.findById(userId);
		if(OptionalshiroUser.isPresent()){
			return ResultData.getSuccessResult(OptionalshiroUser.get(),"操作成功！");
		}else{
			return ResultData.getFailResult("根据ID获取用户对象为空！");
		}
	}

	@Override
	public ShiroUser getLoginUserInfo(String accessToken) {
		if(StringUtils.isBlank(accessToken)){
			return null;
		}
		String userId = DesUtil.decrypt(accessToken);
		Optional<ShiroUser> OptionalshiroUser = shiroUserRepository.findById(userId);
		if(OptionalshiroUser.isPresent()){
			return OptionalshiroUser.get();
		}else{
			return null;
		}
	}

	@Override
	public ResultData getUserResAll(String userId,String status) {
		LoggerUtil.info(LOGGER,"getUserResAll方法id:{0}",userId);
		try {
			if(userId!=null){
				List<ShiroResource> shiroResource = shiroResourceRepository.getUserResAll(userId,status);
				if(CollectionUtils.isNotEmpty(shiroResource)){
					List<MenuDto> list = packing(shiroResource);
					return ResultData.getSuccessData(list);
				}else{
					return ResultData.getFailResult("根据ID和状态获取用户资源为空！");
				}
			}else{
				return ResultData.getFailResult("ID为空！");
			}

		} catch (Exception e) {
			LoggerUtil.error(LOGGER,"Exception:{0}",e);
			return ResultData.UNKNOWN_EXCEPTION;
		}
	}

	private boolean check(ShiroUserDto shiroUserDto){
		if(shiroUserDto == null ){
			return  false;
		}else {
			if(shiroUserDto.getId() !=null
					&& StringUtils.isBlank(shiroUserDto.getLoginName())
					&& StringUtils.isBlank(shiroUserDto.getPassword())
					&& checkSex(shiroUserDto.getSex())
					&& checkStatus(shiroUserDto.getStatus())
					&& ElementsUtils.isNotBlank(shiroUserDto.getRoleIds())){
				return  false;
			}else{
				return  true;
			}
		}
	}
	private boolean check(ShiroUser shiroUser){
		if(shiroUser == null ){
			return  false;
		}else {
			if(StringUtils.isBlank(shiroUser.getLoginName())
					&& StringUtils.isBlank(shiroUser.getPassword())
					&& checkSex(shiroUser.getSex())
					&& checkStatus(shiroUser.getStatus())
					){
				return  false;
			}else{
				return  true;
			}
		}
	}

	private List packRoleId(String userId ,String roleId){
		String[] roles = roleId.split(",");
		List list = new ArrayList();
		for(int i=0,len=roles.length;i<len;i++){
			ShiroUserRole shiroUserRole = new ShiroUserRole();
			shiroUserRole.setId(idWorkerUtils.toString(idWorkerUtils.nextId()));
			shiroUserRole.setRoleId(roles[i]);
			shiroUserRole.setUserId(userId);
			shiroUserRole.setCreateTime(new Date());
			shiroUserRole.setUpdateTime(new Date());
			shiroUserRole.setStatus("VALID");
			list.add(shiroUserRole);
		}
		return  list;
	}

	private void insertByBatch(List<ShiroUserRole> shiroUserRoleList){
		for (ShiroUserRole shiroUserRole: shiroUserRoleList) {
			shiroUserRoleRepository.save(shiroUserRole);
		}

	}






	private ShiroUser copy (ShiroUser old_ShiroUser, ShiroUserDto update_omsShiroUser){
			ShiroUser ShiroUser = new ShiroUser();
			if(StringUtils.isNotEmpty(update_omsShiroUser.getLoginName())){
				old_ShiroUser.setLoginName(update_omsShiroUser.getLoginName());
			}
			if(StringUtils.isNotEmpty(update_omsShiroUser.getNickName())){
				old_ShiroUser.setNickName(update_omsShiroUser.getNickName());
			}
			if(StringUtils.isNotEmpty(update_omsShiroUser.getPassword())){
				if(!old_ShiroUser.getPassword().equalsIgnoreCase(update_omsShiroUser.getPassword())){
					old_ShiroUser.setPassword(EncryptUtil.encryptMD5(update_omsShiroUser.getPassword(), SignConstant.PASSWORD_SALT));
				}
			}
			if(StringUtils.isNotEmpty(update_omsShiroUser.getPhone())){
				old_ShiroUser.setPhone(update_omsShiroUser.getPhone());
			}
			if(checkSex(update_omsShiroUser.getSex())){
				old_ShiroUser.setSex(update_omsShiroUser.getSex());
			}
			if(checkStatus(update_omsShiroUser.getStatus())){
				old_ShiroUser.setStatus(update_omsShiroUser.getStatus());
			}
			return old_ShiroUser;

	}





	private boolean checkSex(String checkSex){
		if(StringUtils.isNotBlank(checkSex)){
			if("MAN".equalsIgnoreCase(checkSex)){
				return true;
			} else if("WOMEN".equalsIgnoreCase(checkSex)){
				return true;
			} else if("SHEMALE".equalsIgnoreCase(checkSex)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	private boolean checkStatus(String checkStatus){
		if(StringUtils.isNotBlank(checkStatus)){
			if("VALID".equalsIgnoreCase(checkStatus)){
				return true;
			} else if("INVALID".equalsIgnoreCase(checkStatus)){
				return true;
			} else{
				return false;
			}
		}else{
			return false;
		}
	}

	private boolean checkLoginName(String id , String loginName){
		long cound =0;
		if(StringUtils.isNotBlank(id)){
			 cound = shiroUserRepository.checkLoginName(id,loginName);
		}else{
			cound = shiroUserRepository.checkLoginNames(loginName);
		}
			if(cound>0){
				return false;
			}else{
				return true;
			}
//		}
	}

	private List<MenuDto> packing(List<ShiroResource> shiroResourceList){
		List<MenuDto> menuList = new ArrayList<MenuDto>();
		if(CollectionUtils.isNotEmpty(shiroResourceList)){
			for (ShiroResource shiroResource : shiroResourceList) {
				MenuDto menuDto = new MenuDto();
				copy(menuDto, shiroResource);
				menuList.add(menuDto);
			}
			return menuList;
		}else{
              return menuList;
		}
	}

	private void copy(MenuDto menuDto, ShiroResource shiroResource){
		if(menuDto!=null && shiroResource !=null){
			MenuDto.Meta meta = menuDto.new Meta();
			menuDto.setId(shiroResource.getId());
			menuDto.setParentId(shiroResource.getParentId());
			menuDto.setResourceName(StringUtils.isBlank(shiroResource.getResourceName())?"": shiroResource.getResourceName());
			meta.setPath(StringUtils.isBlank(shiroResource.getPath())?"": shiroResource.getPath());
			meta.setIcon(StringUtils.isBlank(shiroResource.getIcon())?"": shiroResource.getIcon());
			meta.setTitle(StringUtils.isBlank(shiroResource.getTitle())?"": shiroResource.getTitle());
			meta.setComponent(StringUtils.isBlank(shiroResource.getComponent())?"": shiroResource.getComponent());
			meta.setResourceMarking(StringUtils.isBlank(shiroResource.getResourceMarking())?"": shiroResource.getResourceMarking());
			meta.setSort(shiroResource.getSort()!=null?shiroResource.getSort():0);
			meta.setType(shiroResource.getType()!=null?shiroResource.getType():0);
			meta.setStatus(StringUtils.isBlank(shiroResource.getStatus())?"": shiroResource.getStatus());
			menuDto.setMeta(meta);
		}

	}


}
