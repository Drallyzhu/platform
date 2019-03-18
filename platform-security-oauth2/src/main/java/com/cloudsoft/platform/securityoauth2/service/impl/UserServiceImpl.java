package com.cloudsoft.platform.securityoauth2.service.impl;

import com.cloudsoft.common.util.DateUtils;
import com.cloudsoft.common.util.IdWorkerUtils;
import com.cloudsoft.platform.securityoauth2.aspect.EvictSession;
import com.cloudsoft.platform.securityoauth2.domain.User;
import com.cloudsoft.platform.securityoauth2.domain.UserExample;
import com.cloudsoft.platform.securityoauth2.domain.UserRole;
import com.cloudsoft.platform.securityoauth2.domain.vo.UserVO;
import com.cloudsoft.platform.securityoauth2.jwt.JwtTokenUtil;
import com.cloudsoft.platform.securityoauth2.repository.UserRepository;
import com.cloudsoft.platform.securityoauth2.repository.UserRoleRepository;
import com.cloudsoft.platform.securityoauth2.service.UserRoleService;
import com.cloudsoft.platform.securityoauth2.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdWorkerUtils idWorker;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public User insert(User user) {
        if (StringUtils.isBlank(user.getId())) {
            user.setId(idWorker.nextIdByString());
        }
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void saveAccountNumber(UserVO userVo) throws Exception{

        User loginUser = jwtTokenUtil.getLoginUser();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setName(userVo.getName());
        user.setMobile(userVo.getMobile());
        user.setCode(userVo.getMobile());
        user.setPwd(encoder.encode(userVo.getPwd()));
        user.setRole("ROLE_STAFF");
        user.setIsDelete("0");
        user.setStatus("0");
        user.setCreateId(loginUser.getId());
        user.setCreateName(loginUser.getName());
        user.setCreateTime(new Date());
        user.setRoleName(userVo.getRoleName());
        user.setRoleId(userVo.getRoleId());

        User dataBaseUser = this.insert(user);

        UserRole userRole = new UserRole();
        userRole.setRoleId(userVo.getRole());
        userRole.setUserId(dataBaseUser.getId());
        userRoleService.insert(userRole);
    }

    @Transactional
    @Override
    public void updateAccountNumber(UserVO userVo) throws Exception {
        User loginUser = jwtTokenUtil.getLoginUser();

        Optional<User> userOptional = userRepository.findById(userVo.getId());
        User dataBaseUser = userOptional.get();

        dataBaseUser.setName(userVo.getName());
        dataBaseUser.setRoleName(userVo.getRoleName());
        dataBaseUser.setRoleId(userVo.getRoleId());
        dataBaseUser.setModifyId(loginUser.getId());
        dataBaseUser.setUpdateName(loginUser.getName());
        dataBaseUser.setModifyTime(new Date());
        this.update(dataBaseUser);

        //通过用户id删除所有关联关系,重新添加建立关联关系
        userRoleRepository.deleteByUserId(dataBaseUser.getId());

        UserRole userRole = new UserRole();
        userRole.setRoleId(userVo.getRole());
        userRole.setUserId(dataBaseUser.getId());
        userRoleService.insert(userRole);

    }

    @Override
    public Page<User> findAll(Integer page, Integer limit, UserVO userVo) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page-1, limit, sort);
        return userRepository.findAll(UserExample.createExample(userVo),pageRequest);
    }

    @Override
    public User findByMobile(String code) {
        return userRepository.findByCode(code);
    }

    @Override
    public User findByCode(String code) {
        return null;
    }

    @Override
    public User findByRole(String role) {
        List<User> userList = userRepository.findByRole(role);
        return (userList == null || userList.size() == 0) ? null : userList.get(0);
    }

    @Override
    public User update(User user) throws Exception {
        if (StringUtils.isBlank(user.getId())) {
            throw new Exception(" Id Error ");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUserInfo(User user) throws Exception {
        user.setModifyId(user.getId());

        user.setModifyTime(new Date());

        user.setUpdateName(user.getName());

        User model = this.update(user);

        return model;
    }

    @Override
    public Map<String, Object> getUserManager() {
        Map<String, Object> map = new HashMap<String, Object>();

        UserVO userVo = new UserVO();
        userVo.setRole("ROLE_CUSTOMER");
        userVo.setIsDelete("0");    //0：未删除

        Long count = this.getCount(userVo);
        map.put("UserAll", count);   //全部

        userVo.setExamine("2");
        count = this.getCount(userVo);
        map.put("examine", count);   //2：已认证

        userVo.setExamineBeginDate(DateUtils.beginOfDay(new Date()));
        userVo.setExamineEndDate(DateUtils.endOfDay(new Date()));
        count = this.getCount(userVo);
        map.put("todayExamine", count);   //今日认证

        UserVO userVO1 = new UserVO();
        userVO1.setRole("ROLE_CUSTOMER");
        userVO1.setStatus("0");
        userVO1.setIsDelete("0");    //0：未删除
        count = this.getCount(userVO1);
        map.put("enable", count);    //0:启用商家

        UserVO userVO2 = new UserVO();
        userVO2.setRole("ROLE_CUSTOMER");
        userVO2.setBeginDate(DateUtils.beginOfDay(new Date()));
        userVO2.setEndDate(DateUtils.endOfDay(new Date()));
        userVO2.setIsDelete("0");    //0：未删除
        count = this.getCount(userVO2);
        map.put("todayAdd", count);  //今日新增

        return map;
    }

    public Long getCount(UserVO userVo) {
        return userRepository.count(UserExample.createExample(userVo));
    }

    @EvictSession
    @Override
    public User findOne(String id) {
        Optional<User> optional = userRepository.findById(id);
        if (null != optional && optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
