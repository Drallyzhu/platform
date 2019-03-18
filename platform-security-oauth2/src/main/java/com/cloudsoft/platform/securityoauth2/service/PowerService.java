package com.cloudsoft.platform.securityoauth2.service;

import com.cloudsoft.platform.securityoauth2.domain.Power;

import java.util.List;

public interface PowerService {

    List<Power> findByRole(String roleId);
}
