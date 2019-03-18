package com.cloudsoft.platform.securityoauth2.service;

import com.cloudsoft.platform.securityoauth2.domain.Logs;
import com.cloudsoft.platform.securityoauth2.domain.vo.LogsVo;
import org.springframework.data.domain.Page;

public interface LogService {

    Logs insert(Logs logs) throws Exception;

    Page<Logs> findAll(Integer page, Integer limit, LogsVo logsVo);

}
