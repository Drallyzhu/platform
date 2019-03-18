package com.cloudsoft.platform.securityoauth2.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "日志Vo类")
public class LogsVo implements Serializable {
    private static final long serialVersionUID = -8372925815145426958L;

    @ApiModelProperty(value = "操作人ID", required = false)
    private String operatorId;

    @ApiModelProperty(value = "操作人名称", required = false)
    private String operatorName;

    @ApiModelProperty(value = "商家ID", required = false)
    private String userId;

    @ApiModelProperty(value = "商家名称", required = false)
    private String username;

    @ApiModelProperty(value = "动作", required = false)
    private String actionName;

    //创建开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建开始时间", required = false)
    private Date beginDate;

    //创建结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建结束时间", required = false)
    private Date endDate;

    //页数
    @ApiModelProperty(value = "页数", required = true)
    private Integer page;

    //每页条数
    @ApiModelProperty(value = "每页条数", required = true)
    private Integer limit;

}
