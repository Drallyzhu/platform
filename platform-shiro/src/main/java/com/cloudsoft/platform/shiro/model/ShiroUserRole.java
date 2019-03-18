package com.cloudsoft.platform.shiro.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhujianrong
 */
@Entity
@Data
@Table(name = "shiro_user_role")
@EntityListeners(AuditingEntityListener.class)
public class ShiroUserRole {

    /** id*/
    @Id
    @Column(name = "id", length = 30,columnDefinition = "varchar(30) NOT NULL COMMENT '用户角色关联表ID'")
    private String id;

    /** 后台用户id*/
    @Column(name = "user_id", length = 30,columnDefinition = "varchar(30) NOT NULL  COMMENT '对应用户表ID' ")
    private String userId;

    /** 后台角色id*/
    @Column(name = "role_id", length = 30,columnDefinition = "varchar(30) NOT NULL  COMMENT '对应角色表ID' ")
    private String roleId;

    /** 创建时间*/
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time",columnDefinition = "datetime  COMMENT '创建时间'")
    private Date createTime;

    /** 修改时间*/
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time",columnDefinition = "datetime DEFAULT NULL COMMENT '修改时间'")
    private Date updateTime;

    /** 状态(VALID:有效  INVALID:不可用)*/
    @Column(name = "status",length = 7,columnDefinition = "varchar(9) DEFAULT NULL COMMENT '状态(VALID:有效  INVALID:不可用)'")
    private String status;


}