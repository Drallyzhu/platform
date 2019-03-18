package com.cloudsoft.platform.securityoauth2.service;


import com.cloudsoft.common.domain.ResultData;
import com.cloudsoft.platform.securityoauth2.domain.dto.UserDTO;

public interface AuthService {

    ResultData register(UserDTO userDTO);

    ResultData login(String username, String password);

    ResultData refresh(String token);
}