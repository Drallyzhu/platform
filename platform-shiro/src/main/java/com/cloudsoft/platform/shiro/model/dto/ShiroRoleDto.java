package com.cloudsoft.platform.shiro.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
@Data
public class ShiroRoleDto implements Serializable{
	private static final long serialVersionUID = 7008182467057299270L;

	/** id*/
    private String id;

    /** 描述*/
    private String description;

    /** 角色名称*/
    private String roleName;

    /** 创建时间*/
    private Date createTime;

    /** 修改时间*/
    private Date updateTime;

    /** 状态(VALID:有效  INVALID:不可用)*/
    private String status;


}