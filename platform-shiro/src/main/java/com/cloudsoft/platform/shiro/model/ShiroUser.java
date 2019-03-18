package com.cloudsoft.platform.shiro.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhujianrong
 */
@Entity
@Data
@Table(name = "shiro_user")
@EntityListeners(AuditingEntityListener.class)
public class ShiroUser implements Serializable  {
    private static final long serialVersionUID = -2251174163373343305L;

    @Id
    @Column(name = "id", length = 30,columnDefinition = "varchar(30) NOT NULL")
    private String id;

    @Column(name = "p_id", length = 30,columnDefinition = "varchar(30) NOT NULL DEFAULT 'root' COMMENT '父id，默认最高级root' ")
    private String pId;

    /** 登录用户名*/
    @Column(name = "login_name",length = 30,columnDefinition = "varchar(30) DEFAULT NULL COMMENT '登录用户名'")
    private String loginName;

    /** 密码*/
    @Column(name = "password",length = 60,columnDefinition = "varchar(60) DEFAULT NULL COMMENT '密码'")
    private String password;

    /** 真实姓名*/
    @Column(name = "nick_name",length = 30,columnDefinition = "varchar(30) DEFAULT NULL COMMENT '真实姓名'")
    private String nickName;

    /** 性别(MAN=男;WOMAN=女)*/
    @Column(name = "sex",length = 7,columnDefinition = "varchar(9) DEFAULT NULL COMMENT '性别(MAN=男;WOMAN=女)'")
    private String sex;


    /** 状态(VALID:有效  INVALID:不可用)*/
    @Column(name = "status",length = 7,columnDefinition = "varchar(9) DEFAULT NULL COMMENT '状态(VALID:有效  INVALID:不可用)'")
    private String status;

    /** 联系电话*/
    @Column(name = "phone",length = 30,columnDefinition = "varchar(30) DEFAULT NULL COMMENT '联系电话'")
    private String phone;

    /** 最近登录时间*/
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_time",columnDefinition = "datetime DEFAULT NULL COMMENT '最近登录时间'")
    private Date lastLoginTime;

    /** 修改时间*/
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time",columnDefinition = "datetime DEFAULT NULL COMMENT '修改时间'")
    private Date updateTime;

    /** 创建时间*/
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time",length = 30,columnDefinition = "datetime  COMMENT '创建时间'")
    private Date createTime;

    /** 扩展字段1（varcher类型））*/
    @Column(name = "extend1",length = 30,columnDefinition = "varchar(135) DEFAULT NULL COMMENT '扩展字段1（varcher类型）'")
    private String extend1;

    /** 扩展字段2（varcher类型））*/
    @Column(name = "extend2",length = 30,columnDefinition = "varchar(135) DEFAULT NULL COMMENT '扩展字段2（varcher类型）'")
    private String extend2;




}
