package com.cloudsoft.platform.shiro.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@Table(name = "shiro_role_resource")
@EntityListeners(AuditingEntityListener.class)
public class ShiroRoleResource {

    /** id*/
    @Id
    @Column(name = "id", length = 30,columnDefinition = "varchar(30) NOT NULL COMMENT '资源角色关联表ID'")
    private String id;

    /** 角色id*/
    @Column(name = "role_id", length = 30,columnDefinition = "varchar(30) NOT NULL  COMMENT '对应角色表ID' ")
    private String roleId;

    /** 资源id*/
    @Column(name = "resource_id", length = 30,columnDefinition = "varchar(30) NOT NULL  COMMENT '对应资源表ID' ")
    private String resourceId;

    /** 创建时间*/
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time",length = 30,columnDefinition = "datetime  COMMENT '创建时间'")
    private Date createTime;

    /** 修改时间*/
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time",columnDefinition = "datetime DEFAULT NULL COMMENT '修改时间'")
    private Date updateTime;

    /** 状态(VALID:有效  INVALID:不可用)*/
    @Column(name = "status",columnDefinition = "varchar(9) DEFAULT NULL COMMENT '状态(VALID:有效  INVALID:不可用)'")
    private String status;


}