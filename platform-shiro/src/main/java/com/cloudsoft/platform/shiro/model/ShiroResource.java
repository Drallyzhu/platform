package com.cloudsoft.platform.shiro.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@Table(name = "shiro_resource")
@EntityListeners(AuditingEntityListener.class)
public class ShiroResource {
    /** id*/
    @Id
    @Column(name = "id", length = 30,columnDefinition = "varchar(30) NOT NULL COMMENT '资源表ID'")
    private String id;

    /** 资源名称*/
    @Column(name = "resource_name", length = 100,columnDefinition = "varchar(100) DEFAULT NULL  COMMENT '资源名称' ")
    private String resourceName;

    /** 资源标记(唯一,通过该字段可以找到相应组件,可以用在按钮标识，可以用到一些其他标识，可以和pageUrl结合)*/
    @Column(name = "resource_marking", length = 130,columnDefinition = "varchar(130) DEFAULT NULL  COMMENT '资源标记(唯一,通过该字段可以找到相应组件,可以用在按钮标识，可以用到一些其他标识，可以和pageUrl结合)' ")
    private String resourceMarking;

    /** 授权码(shiro 里面的 注入标签@RequiresPermissions("xxxx"))*/
    @Column(name = "grand_key", length = 100,columnDefinition = "varchar(100) DEFAULT NULL  COMMENT '授权码(shiro 里面的 authc校验和anon不用校验)' ")
    private String grandKey;

    /** 资源访问路径*/
    @Column(name = "resource_url", length = 100,columnDefinition = "varchar(100) DEFAULT NULL  COMMENT '资源访问路径' ")
    private String resourceUrl;

    /** 父ID（所属父级，0:没有父级）*/
    @Column(name = "parent_id", length = 100,columnDefinition = "varchar(100) NOT NULL DEFAULT '0'  COMMENT '权限类型（所属父级，0:没有父级）,对应资源ID' ")
    private String parentId;

    /** 排序*/
    @Column(name = "sort", length = 6,columnDefinition = "int(6) DEFAULT '0' COMMENT '排序，最小的排最前面'")
    private Integer sort;

    /** 资源类型(0代表按钮 ,1代表导航（父），2代表一级菜单，3代表二级菜单，4代表三级菜单，5代表四级菜单，6代表五级菜单，7代表六级菜单)*/
    @Column(name = "type", length = 6,columnDefinition = "int(6) DEFAULT '1' COMMENT '资源类型(0代表按钮 ,1代表导航（父），2代表一级菜单，3代表二级菜单，4代表三级菜单，5代表四级菜单，6代表五级菜单，7代表六级菜单'")
    private Integer type;

    /** 前端使用path*/
    @Column(name = "path", length = 100,columnDefinition = "varchar(100) DEFAULT NULL COMMENT '前端使用path'")
    private String path;

    /** 前端使用component*/
    @Column(name = "component", length = 100,columnDefinition = "varchar(100) DEFAULT NULL  COMMENT '前端使用component' ")
    private String component;

    /** 前端使用title*/
    @Column(name = "title", length = 100,columnDefinition = "varchar(100) DEFAULT NULL  COMMENT '前端使用title' ")
    private String title;

    /** 前端使用icon*/
    @Column(name = "icon", length = 100,columnDefinition = "varchar(100) DEFAULT NULL  COMMENT '前端使用icon' ")
    private String icon;

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

    /** 状态(VALID:有效  INVALID:无效)*/
    @Column(name = "status",columnDefinition = "varchar(9) DEFAULT NULL COMMENT '状态(VALID:有效  INVALID:不可用)'")
    private String status;


    
}