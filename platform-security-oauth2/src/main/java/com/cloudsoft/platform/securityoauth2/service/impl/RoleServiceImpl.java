package com.cloudsoft.platform.securityoauth2.service.impl;

import com.cloudsoft.common.util.IdWorkerUtils;
import com.cloudsoft.platform.securityoauth2.domain.Role;
import com.cloudsoft.platform.securityoauth2.repository.RoleRepository;
import com.cloudsoft.platform.securityoauth2.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private IdWorkerUtils idWorker;

    @Override
    public Role insert(Role role) {
        if (StringUtils.isBlank(role.getId())){
            role.setId(idWorker.nextIdByString());
        }
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) throws Exception {
        if (StringUtils.isBlank(role.getId())){
            throw new Exception("ID ERROR");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role findOne(String id) {
        Optional<Role> optional = roleRepository.findById(id);
        if (null != optional && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public Role findByCode(String code) {
        Optional<Role> optional = roleRepository.findByCode(code);
        if (null != optional && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Role> findByUserId(String userId) {
        return roleRepository.findByUserId(userId);
    }
}
