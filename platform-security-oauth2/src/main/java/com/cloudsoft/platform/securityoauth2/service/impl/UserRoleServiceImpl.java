package com.cloudsoft.platform.securityoauth2.service.impl;

import com.cloudsoft.common.util.IdWorkerUtils;
import com.cloudsoft.platform.securityoauth2.domain.UserRole;
import com.cloudsoft.platform.securityoauth2.repository.UserRoleRepository;
import com.cloudsoft.platform.securityoauth2.service.UserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private IdWorkerUtils idWorker;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole insert(UserRole userRole) {
        if (StringUtils.isBlank(userRole.getId())) {
            userRole.setId(idWorker.nextIdByString());
        }
        return userRoleRepository.save(userRole);
    }

}
