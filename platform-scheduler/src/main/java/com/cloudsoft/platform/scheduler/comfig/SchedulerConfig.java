package com.cloudsoft.platform.scheduler.comfig;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * SchedulerConfig
 *
 * @author zhujianrong
 */
@Configuration
@ComponentScan(basePackages = "com")
public class SchedulerConfig {
    private Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);

//    @Value("${scheduler.schedulerCenter.address}")
//    private String adminAddresses;
//
//    @Value("${scheduler.executor.appname}")
//    private String appName;
//
//    @Value("${scheduler.executor.ip}")
//    private String ip;
//
//    @Value("${scheduler.executor.port}")
//    private int port;
//
//    @Value("${scheduler.accessToken}")
//    private String accessToken;
//
//    @Value("${scheduler.executor.logpath}")
//    private String logPath;
//
//    @Value("${scheduler.executor.logretentiondays}")
//    private int logRetentionDays;

    @Autowired
    private Environment environment;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> scheduler-job config init.");
        String adminAddresses = environment.getProperty("scheduler.schedulerCenter.addresses");
        String appName = environment.getProperty("scheduler.executor.appname");
        String ip = environment.getProperty("scheduler.executor.ip");
        String port = environment.getProperty("scheduler.executor.port");
        String accessToken = environment.getProperty("scheduler.accessToken");
        String logPath = environment.getProperty("scheduler.executor.logpath");
        String logRetentionDays = environment.getProperty("scheduler.executor.logretentiondays");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppName(appName);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(StringUtils.isNotBlank(port)?Integer.parseInt(port):0);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(StringUtils.isNotBlank(logRetentionDays)?Integer.parseInt(logRetentionDays):0);

        return xxlJobSpringExecutor;
    }

}