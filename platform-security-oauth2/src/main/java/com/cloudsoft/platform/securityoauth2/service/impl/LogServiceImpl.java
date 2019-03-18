package com.cloudsoft.platform.securityoauth2.service.impl;

import com.cloudsoft.common.util.IdWorkerUtils;
import com.cloudsoft.platform.securityoauth2.domain.Logs;
import com.cloudsoft.platform.securityoauth2.domain.LogsExample;
import com.cloudsoft.platform.securityoauth2.domain.vo.LogsVo;
import com.cloudsoft.platform.securityoauth2.repository.LogsRepository;
import com.cloudsoft.platform.securityoauth2.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogsRepository logsRepository;

    @Autowired
    private IdWorkerUtils idWorker;

    @Override
    public Logs insert(Logs logs) throws Exception {
        logs.setId(idWorker.nextIdByString());
        logs.setCreateTime(new Date());
        return logsRepository.save(logs);
    }

    @Override
    public Page<Logs> findAll(Integer page, Integer limit, LogsVo logsVo){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page-1, limit, sort);
        return logsRepository.findAll(LogsExample.createExample(logsVo), pageRequest);
    }
}
