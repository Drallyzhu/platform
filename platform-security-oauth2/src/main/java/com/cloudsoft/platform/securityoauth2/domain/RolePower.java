package com.cloudsoft.platform.securityoauth2.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role_power")
@ApiModel(value = "角色权限")
public class RolePower implements Serializable {

    private static final long serialVersionUID = 7208777939077152372L;

    @Id
    @Column(name = "id", columnDefinition = "varchar(64) NOT NULL COMMENT 'Id'")
    @ApiModelProperty(value = "Id", required = false)
    private String id;

    @ApiModelProperty(value = "角色ID", required = true)
    @Column(name = "role_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '角色ID'")
    private String roleId;

    @ApiModelProperty(value = "权限ID", required = true)
    @Column(name = "power_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '权限ID'")
    private String powerId;

}
