package com.cloudsoft.platform.securityoauth2.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -6516833063982703847L;

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "验证码", required = true)
    private String vcode;

    @ApiModelProperty(value = "注册码", required = false)
    private String pollCode;

    @ApiModelProperty(value = "邀请码", required = false)
    private String invitationCode;

    /**
     * 商家管理列表字段begin
     **/
    //用户Id
    @ApiModelProperty(value = "Id", required = false)
    private String id;

    // 手机
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    // 名称
    @ApiModelProperty(value = "姓名", required = true)
    private String name;


    // 应用Id
    @ApiModelProperty(value = "应用Id", required = false)
    private String appId;

    // 是否认证
    @ApiModelProperty(value = "认证(0.未认证 1.已认证 2.认证失败)", required = false)
    private String examine;

    //是否认证中文形式
    @ApiModelProperty(value = "未认证、已认证、认证失败", required = false)
    private String examineString;


    // 状态(0.正常 1.冻结)
    @ApiModelProperty(value = "状态", required = false)
    private String status;


    //状态中文形式
    @ApiModelProperty(value = "正常、冻结", required = false)
    private String statusString;


    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", required = false)
    private Date createTime;


    //最后登录时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后登录时间", required = false)
    private Date lastLoginTime;


    @ApiModelProperty(value = "证件类型(0:身份证)", required = false)
    private String cardType;

    @ApiModelProperty(value = "性别", required = false)
    private String sex;


    // 身份证正面
    @ApiModelProperty(value = "身份证正面", required = true)
    private String idCardFront;

    // 身份证反面
    @ApiModelProperty(value = "身份证反面", required = true)
    private String idCardBack;

    @ApiModelProperty(value = "上级用户姓名", required = false)
    private String parentName;


    /**商家管理列表字段end**/
}
