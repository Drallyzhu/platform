package com.cloudsoft.platform.securityoauth2.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "role")
@ApiModel(value = "角色")
public class Role extends Base{

    private static final long serialVersionUID = -5248834662559773055L;

    @ApiModelProperty(value = "角色编码", required = true)
    @Column(name = "code", columnDefinition = "varchar(30) DEFAULT NULL COMMENT '角色编码'")
    private String code;

    @ApiModelProperty(value = "角色名称", required = true)
    @Column(name = "name", columnDefinition = "varchar(30) DEFAULT NULL COMMENT '角色名称'")
    private String name;

    @ApiModelProperty(value = "角色状态(0.冻结 1.启用)", required = true)
    @Column(name = "status", columnDefinition = "varchar(4) DEFAULT NULL COMMENT '角色状态(0.冻结 1.启用)'")
    private String status;

    @ApiModelProperty(value = "角色说明", required = true)
    @Column(name = "remark", columnDefinition = "varchar(225) DEFAULT NULL COMMENT '角色说明'")
    private String remark;

    @ApiModelProperty(value = "部门ID", required = true)
    @Column(name = "dept_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '部门ID'")
    private String deptId;

    @ApiModelProperty(value = "部门名称", required = true)
    @Column(name = "dept_name", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '部门名称'")
    private String deptName;

    @ApiModelProperty(value = "排序值", required = true)
    @Column(name = "sort", columnDefinition = "int(11) DEFAULT NULL COMMENT '排序值'")
    private int sort;

}
