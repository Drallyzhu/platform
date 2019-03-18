package com.cloudsoft.platform.scheduler.feign.client;

import com.cloudsoft.platform.scheduler.model.SchedulerJobInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhujianrong
 * @date 2018-11-19 15:14
 * 连接定时器中心接口
 */
@FeignClient(name = "Scheduler-client",url = "${scheduler.schedulerCenter.address}")
public interface SchedulerCenterClient {


    /**
     * 创建定时器
     * @param SchedulerJobInfo
     * @return
     */
    @PostMapping(value = "/jobinfo/add")
    String jobinfoAdd(@RequestBody SchedulerJobInfo SchedulerJobInfo);

    /**
     * 修改定时器
     * @param SchedulerJobInfo 定时器对象
     * @return
     */
    @PutMapping(value = "/jobinfo/update")
    String jobinfoUpdate(@RequestBody SchedulerJobInfo SchedulerJobInfo);

    /**
     * 删除定时器
     * @param id 定时器ID
     * @return
     */
    @DeleteMapping(value = "/jobinfo/remove/{id}")
    String jobinforemove(@PathVariable(value = "id") int id);

    /**
     * 停止定时器
     * @param id 定时器ID
     * @return
     */
    @PutMapping(value = "/jobinfo/stop/{id}")
    String jobinfoStop(@PathVariable(value = "id") int id);


    /**
     * 启动定时器
     * @param id 定时器ID
     * @return
     */
    @PutMapping(value = "/jobinfo/start/{id}")
    String jobinfoStart(@PathVariable(value = "id") int id);


    /**
     * 触发定时器
     * @param id 定时器ID
     * @param executorParam 这个就是页面里面点击按钮填的信息，具体可以查看页面版
     * @return
     */
    @PostMapping(value = "/jobinfo/trigger")
    String jobinfoAdd(@RequestParam(value ="id" ) int id, @RequestParam(value ="executorParam" ) String executorParam);












}
