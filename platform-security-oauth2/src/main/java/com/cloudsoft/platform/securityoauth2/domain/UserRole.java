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
@Table(name = "user_role")
@ApiModel(value = "用户角色")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 5458883136322287072L;

    @Id
    @Column(name = "id", columnDefinition = "varchar(64) NOT NULL COMMENT 'Id'")
    @ApiModelProperty(value = "Id", required = false)
    private String id;

    @ApiModelProperty(value = "用户ID", required = true)
    @Column(name = "user_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '用户ID'")
    private String userId;

    @ApiModelProperty(value = "角色ID", required = true)
    @Column(name = "role_id", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '角色ID'")
    private String roleId;

}
