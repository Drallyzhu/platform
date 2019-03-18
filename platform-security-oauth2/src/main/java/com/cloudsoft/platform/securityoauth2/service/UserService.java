package com.cloudsoft.platform.securityoauth2.service;

import com.cloudsoft.platform.securityoauth2.domain.User;
import com.cloudsoft.platform.securityoauth2.domain.vo.UserVO;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface UserService {

    User insert(User user);

    void saveAccountNumber(UserVO userVo) throws Exception;

    void updateAccountNumber(UserVO userVo) throws Exception;

    Page<User> findAll(Integer page, Integer limit, UserVO userVo);

    User findByMobile(String code);

    User findByCode(String code);

    User findByRole(String role);

    User update(User User) throws Exception;

    User updateUserInfo(User user) throws Exception;

    Map<String,Object> getUserManager();

    User findOne(String id);

}
