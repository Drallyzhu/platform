package com.cloudsoft.platform.shiro.model;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@Table(name = "shiro_role")
@EntityListeners(AuditingEntityListener.class)
public class ShiroRole {
    /** id*/
    @Id
    @Column(name = "id", length = 30,columnDefinition = "varchar(30) NOT NULL COMMENT '角色表ID'")
    private String id;

    /** 描述*/
    @Column(name = "description", length = 100,columnDefinition = "varchar(100) DEFAULT NULL  COMMENT '描述' ")
    private String description;

    /** 角色名称*/
    @Column(name = "role_name", length = 50,columnDefinition = "varchar(200) DEFAULT NULL  COMMENT '角色名称' ")
    private String roleName;

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
    @Column(name = "status",columnDefinition = "varchar(9) DEFAULT NULL COMMENT '状态(VALID:有效  INVALID:不可用)'")
    private String status;


}