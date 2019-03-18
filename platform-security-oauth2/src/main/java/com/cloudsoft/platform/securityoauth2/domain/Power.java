package com.cloudsoft.platform.securityoauth2.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "power")
@ApiModel(value = "权限")
public class Power implements Serializable {

    private static final long serialVersionUID = -4696109987017789086L;

    @Id
    @Column(name = "id", columnDefinition = "varchar(64) NOT NULL COMMENT 'Id'")
    @ApiModelProperty(value = "Id", required = false)
    private String id;

    @ApiModelProperty(value = "权限名称", required = true)
    @Column(name = "name", columnDefinition = "varchar(32) DEFAULT NULL COMMENT '权限名称'")
    private String name;

    @ApiModelProperty(value = "权限编码", required = true)
    @Column(name = "code", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '权限编码'")
    private String code;

    @ApiModelProperty(value = "权限类型(0.菜单 1.动作)", required = true)
    @Column(name = "type", columnDefinition = "varchar(32) DEFAULT NULL COMMENT '权限类型(0.菜单 1.动作)'")
    private String type;

    @ApiModelProperty(value = "访问路径", required = true)
    @Column(name = "url", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '访问路径'")
    private String url;

    @ApiModelProperty(value = "权限等级", required = true)
    @Column(name = "level", columnDefinition = "int(11) DEFAULT NULL COMMENT '权限等级'")
    private Integer level;

    @ApiModelProperty(value = "父级权限", required = true)
    @Column(name = "parent_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '父级权限'")
    private String parentId;

    @ApiModelProperty(value = "权限顺序", required = true)
    @Column(name = "sort", columnDefinition = "int(11) DEFAULT NULL COMMENT '权限顺序'")
    private Integer sort;

    @Transient
    private List<Power> powerList;

}
