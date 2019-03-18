package com.cloudsoft.platform.securityoauth2.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@ApiModel(value = "用户对象")
public class User extends Base {

    private static final long serialVersionUID = -2028714474709406722L;

    // 编号
    @ApiModelProperty(value = "编号", required = false)
    @Column(name = "code", columnDefinition = "varchar(30) DEFAULT NULL COMMENT '编号'")
    private String code;

    // 名称
    @ApiModelProperty(value = "姓名", required = true)
    @Column(name = "name", columnDefinition = "varchar(30) DEFAULT NULL COMMENT '姓名'")
    private String name;

    // 性别
    @ApiModelProperty(value = "性别", required = false)
    @Column(name = "sex", columnDefinition = "varchar(30) DEFAULT NULL COMMENT '性别'")
    private String sex;

    // 昵称
    @ApiModelProperty(value = "昵称", required = false)
    @Column(name = "nick_name", columnDefinition = "varchar(30) DEFAULT NULL COMMENT '昵称'")
    private String nickName;

    // 手机
    @ApiModelProperty(value = "手机号", required = true)
    @Column(name = "mobile", columnDefinition = "varchar(20) DEFAULT NULL COMMENT '手机号'")
    private String mobile;

    // 密码
    @ApiModelProperty(value = "密码", required = true)
    @Column(name = "pwd", columnDefinition = "varchar(125) DEFAULT NULL COMMENT '密码'")
    private String pwd;

    // 身份证号
    @ApiModelProperty(value = "身份证号", required = true)
    @Column(name = "id_card", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '身份证号'")
    private String idCard;

    // 证件类型
    @ApiModelProperty(value = "证件类型(0:身份证)", required = false)
    @Column(name = "card_type", columnDefinition = "varchar(18) DEFAULT NULL COMMENT '证件类型'")
    private String cardType;

    /**
     *  角色 Admin.管理员ROLE_STAFF.员工 ROLE_CUSTOMER.代理商 ROLE_SUPPLIER.商户
     */
    @ApiModelProperty(value = "角色", required = false)
    @Column(name = "role", columnDefinition = "varchar(32) DEFAULT NULL COMMENT '角色'")
    private String role;

    // 状态(0.正常 1.冻结)
    @ApiModelProperty(value = "状态", required = false)
    @Column(name = "status", columnDefinition = "varchar(4) DEFAULT NULL COMMENT '状态(0.正常 1.冻结)'")
    private String status;

    // 最后登录时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_time", columnDefinition = "datetime DEFAULT NULL COMMENT '最后登录时间'")
    @ApiModelProperty(value = "最后登录时间", required = false)
    private Date lastLoginTime;

    // 应用Id
    @Column(name = "app_id", columnDefinition = "varchar(128) DEFAULT NULL COMMENT '应用Id'")
    @ApiModelProperty(value = "应用Id", required = false)
    private String appId;

    // 应用私钥
    @Column(name = "private_key", columnDefinition = "varchar(128) DEFAULT NULL COMMENT '应用私钥'")
    @ApiModelProperty(value = "应用私钥", required = false)
    private String privateKey;

    // 身份证正面
    @Column(name = "id_card_front", columnDefinition = "varchar(128) DEFAULT NULL COMMENT '身份证正面'")
    @ApiModelProperty(value = "身份证正面", required = true)
    private String idCardFront;

    // 身份证反面
    @Column(name = "id_card_back", columnDefinition = "varchar(128) DEFAULT NULL COMMENT '身份证反面'")
    @ApiModelProperty(value = "身份证反面", required = true)
    private String idCardBack;

    // 是否认证
    @Column(name = "examine", columnDefinition = "varchar(4) DEFAULT NULL COMMENT '认证(0.未认证 1.处理中 2.已认证 3.认证失败)'")
    @ApiModelProperty(value = "认证(0.未认证 1.处理中 2.已认证 3.认证失败)", required = false)
    private String examine;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "examine_time", columnDefinition = "datetime DEFAULT NULL COMMENT '认证时间'")
    @ApiModelProperty(value = "认证时间", required = false)
    private Date examineTime;

    @Column(name = "is_delete", columnDefinition = "varchar(2) DEFAULT NULL COMMENT '是否已删除(0.未删除 1.已删除'")
    @ApiModelProperty(value = "是否删除", required = false)
    private String isDelete;

    // 支付密码
    @ApiModelProperty(value = "支付密码", required = false)
    @Column(name = "pay_pwd", columnDefinition = "varchar(125) DEFAULT NULL COMMENT '支付密码'")
    private String payPwd;

    // 地址
    @ApiModelProperty(value = "地址", required = false)
    @Column(name = "address", columnDefinition = "varchar(125) DEFAULT NULL COMMENT '地址'")
    private String address;

    // 认证驳回理由
    @ApiModelProperty(value = "认证驳回理由", required = false)
    @Column(name = "reject", columnDefinition = "text DEFAULT NULL COMMENT '认证驳回理由'")
    private String reject;

    @ApiModelProperty(value = "邀请码", required = false)
    @Column(name = "invitation_code", columnDefinition = "varchar(32) DEFAULT NULL COMMENT '邀请码'")
    private String invitationCode;

    @ApiModelProperty(value = "注册码", required = false)
    @Column(name = "poll_code", columnDefinition = "varchar(32) DEFAULT NULL COMMENT '注册码'")
    private String pollCode;

    @ApiModelProperty(value = "机构id", required = false)
    @Column(name = "owner_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '机构id'")
    private String ownerId;

    @ApiModelProperty(value = "上级用户姓名", required = false)
    @Column(name = "parent_name", columnDefinition = "varchar(30) DEFAULT NULL COMMENT '上级用户姓名'")
    private String parentName;

    @ApiModelProperty(value = "角色id", required = false)
    @Column(name = "role_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '角色id'")
    private String roleId;

    @ApiModelProperty(value = "角色名称", required = false)
    @Column(name = "role_name", columnDefinition = "varchar(30) DEFAULT NULL COMMENT '角色名称'")
    private String roleName;

    @ApiModelProperty(value = "代理等级", required = false)
    @Column(name = "level", columnDefinition = "int(11) DEFAULT NULL COMMENT '代理等级'")
    private Integer level;

    @Transient
    private List<Role> roles;
}