package com.cloudsoft.platform.securityoauth2.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "logs")
@ApiModel(value = "系统日志")
public class Logs extends Base {

    private static final long serialVersionUID = 5763247404018869591L;

    @ApiModelProperty(value = "商家ID", required = true)
    @Column(name = "user_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '商家ID'")
    private String userId;

    @ApiModelProperty(value = "商家名称", required = true)
    @Column(name = "user_name", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '商家名称'")
    private String username;

    @ApiModelProperty(value = "操作人ID", required = true)
    @Column(name = "operator_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '操作人ID'")
    private String operatorId;

    @ApiModelProperty(value = "操作人名称", required = true)
    @Column(name = "operator_name", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '操作人名称'")
    private String operatorName;

    @ApiModelProperty(value = "操作时间", required = true)
    @Column(name = "operator_time", columnDefinition = "datetime DEFAULT NULL COMMENT '操作时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date operatorTime;

    @ApiModelProperty(value = "操作IP", required = true)
    @Column(name = "operator_ip", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '操作IP'")
    private String operateIp;

    @ApiModelProperty(value = "访问路径", required = true)
    @Column(name = "url", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '访问路径'")
    private String url;

    @ApiModelProperty(value = "访问结果", required = true)
    @Column(name = "result", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '访问结果'")
    private String result;

    @ApiModelProperty(value = "参数", required = true)
    @Column(name = "param", columnDefinition = "text(0) DEFAULT NULL COMMENT '参数'")
    private String param;

    @ApiModelProperty(value = "动作", required = true)
    @Column(name = "action_name", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '动作'")
    private String actionName;

    @ApiModelProperty(value = "状态码", required = true)
    @Column(name = "status", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '状态码'")
    private String status;

    public static Logs getInstance(){
        return new Logs();
    }

}
