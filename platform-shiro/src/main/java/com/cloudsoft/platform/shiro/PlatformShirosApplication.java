package com.cloudsoft.platform.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//@SpringBootApplication(scanBasePackages={"com.bcbackstage","com.cloudsoft"})
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
@EnableJpaRepositories("com.cloudsoft")
@EntityScan("com.cloudsoft")
@ComponentScan("com.cloudsoft")
@EnableJpaAuditing
@EnableRetry
public class PlatformShirosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformShirosApplication.class, args);
    }
}
