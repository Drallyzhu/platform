package com.cloudsoft.platform.securityoauth2.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "角色Vo类")
public class RoleVO implements Serializable {
    @ApiModelProperty(value = "角色名称", required = false)
    private String name;

    @ApiModelProperty(value = "角色状态(0.冻结 1.启用)", required = false)
    private String status;

    @ApiModelProperty(value = "是否显示管理员角色(0.不显示 1.显示)", required = false)
    private String showAdmin;
    
    //页数
    @ApiModelProperty(value = "页数", required = true)
    private Integer page;

    //每页条数
    @ApiModelProperty(value = "每页条数", required = true)
    private Integer limit;
}
