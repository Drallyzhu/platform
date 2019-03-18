package com.cloudsoft.platform.securityoauth2.service.impl;

import com.cloudsoft.platform.securityoauth2.domain.Power;
import com.cloudsoft.platform.securityoauth2.repository.PowerRepository;
import com.cloudsoft.platform.securityoauth2.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    private PowerRepository powerRepository;

    @Override
    public List<Power> findByRole(String roleId) {
        return powerRepository.findByRole(roleId);
    }
}
