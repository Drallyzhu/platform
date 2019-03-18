package com.cloudsoft.platform.shiro.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ApiModel(value = "资源数据请求封装类")
public class ShiroResourceDto {
    /** id*/
    @ApiModelProperty(value = "资源ID" ,required = true)
    @NotBlank(message = "资源ID不能为空")
    private String id;

    /** 资源名称*/
    @ApiModelProperty(value = "资源名称" ,required = true)
    @NotBlank(message = "资源名称不能为空")
    private String resourceName;

    /** 资源标记(唯一,通过该字段可以找到相应组件,可以用在按钮标识，可以用到一些其他标识，可以和pageUrl结合)*/
    @ApiModelProperty(value = "资源标记(唯一,通过该字段可以找到相应组件,可以用在按钮标识，可以用到一些其他标识，可以和pageUrl结合)" ,required = false)
    private String resourceMarking;

    /** 授权码(shiro 里面的通过shiroToken校验)*/
    @ApiModelProperty(value = "授权码(shiro 里面的通过shiroToken校验)" ,required = true)
    @NotBlank(message = "授权码不能为空")
    private String grandKey;

    /** 资源访问路径*/
    @ApiModelProperty(value = "资源访问路径" ,required = true)
    @NotBlank(message = "资源访问路径不能为空")
    private String resourceUrl;

    /** 资源等级（所属父级，0:没有父级）*/
    @ApiModelProperty(value = "权限类型（所属父级，0:没有父级）" ,required = true)
    @NotBlank(message = "资源等级不能为空")
    private String parentId;

    /** 排序*/
    @ApiModelProperty(value = "排序,从小到大排序,默认填0" ,required = true)
    @NotBlank(message = "排序不能为空")
    private Integer sort;

    /** 资源类型，以后业务主页可以做树结构，不清楚填1(0代表按钮 ,1代表导航（父），2代表一级菜单，3代表二级菜单，4代表三级菜单，5代表四级菜单，6代表五级菜单，7代表六级菜单)*/
    @ApiModelProperty(value = "资源类型(0代表按钮 ,1代表导航（父），2代表一级菜单，3代表二级菜单，4代表三级菜单，5代表四级菜单，6代表五级菜单，7代表六级菜单)" ,required = true)
    @NotBlank(message = "资源类型不能为空")
    private Integer type;

    /** 前端使用path*/
    @ApiModelProperty(value = "前端使用path" ,required = false)
    private String path;

    /** 前端使用component*/
    @ApiModelProperty(value = "前端使用component" ,required = false)
    private String component;

    /** 前端使用title*/
    @ApiModelProperty(value = "前端使用title" ,required = false)
    private String title;

    /** 前端使用icon*/
    @ApiModelProperty(value = "前端使用icon" ,required = false)
    private String icon;

    /** 状态(VALID:有效  INVALID:无效)*/
    @ApiModelProperty(value = "状态(VALID:有效  INVALID:无效)" ,required = true)
    @NotBlank(message = "资源状态不能为空")
    private String status;
}