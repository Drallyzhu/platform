package com.cloudsoft.platform.securityoauth2.service;

import com.cloudsoft.platform.securityoauth2.domain.Role;

import java.util.List;

public interface RoleService {

    Role insert(Role role);

    Role update(Role Role) throws Exception;

    Role findOne(String id);

    Role findByCode(String code);

    List<Role> findByUserId(String userId);
}
