package com.cloudsoft.platform.securityoauth2.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
public class Base implements Serializable {

    private static final long serialVersionUID = 8770098654868063876L;

    @Id
    @Column(name = "id", columnDefinition = "varchar(64) NOT NULL COMMENT 'Id'")
    @ApiModelProperty(value = "Id", required = false)
    private String id;

    @Column(name = "create_id", columnDefinition = "varchar(20) DEFAULT NULL COMMENT '创建人Id'")
    @ApiModelProperty(value = "创建人Id", required = false)
    private String createId;

    @Column(name = "create_name", columnDefinition = "varchar(30) DEFAULT NULL COMMENT '创建人姓名'")
    @ApiModelProperty(value = "创建人姓名", required = false)
    private String createName;


    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "create_time", columnDefinition = "datetime DEFAULT NULL COMMENT '创建时间'")
    @ApiModelProperty(value = "创建时间", required = false)
    private Date createTime;

    @Column(name = "modify_id", columnDefinition = "varchar(20) DEFAULT NULL COMMENT '修改人Id'")
    @ApiModelProperty(value = "修改人Id", required = false)
    private String modifyId;

    @Column(name = "modify_name", columnDefinition = "varchar(30) DEFAULT NULL COMMENT '修改人姓名'")
    @ApiModelProperty(value = "修改人姓名", required = false)
    private String updateName;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "update_time", columnDefinition = "datetime DEFAULT NULL COMMENT '修改时间'")
    @ApiModelProperty(value = "修改时间", required = false)
    private Date modifyTime;

}
