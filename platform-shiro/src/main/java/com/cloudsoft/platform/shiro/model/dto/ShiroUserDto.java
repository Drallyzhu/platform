package com.cloudsoft.platform.shiro.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ShiroUserDto implements Serializable {
    private static final long serialVersionUID = -6588946690541265660L;
    /** id*/
    private String id;

    /** 登录用户名*/
    private String loginName;

    /** 密码*/
    private String password;

    /** 真实姓名*/
    private String nickName;

    /** 性别(MAN=男;WOMAN=女)*/
    private String sex;

    /** 状态(VALID:有效  INVALID:不可用)*/
    private String status;

    /** 联系电话*/
    private String phone;

    /** 最近登录时间*/
    private Date lastLoginTime;

    /** 修改时间*/
    private Date updateTime;

    /** 创建时间*/
    private Date createTime;

    /** 角色ID(多个)*/
    private String roleIds;


}