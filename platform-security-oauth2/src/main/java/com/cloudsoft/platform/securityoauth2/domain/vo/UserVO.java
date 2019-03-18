package com.cloudsoft.platform.securityoauth2.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pc
 * @date 2018/10/24 14:44
 */
@Data
@ApiModel(value = "用户Vo类")
public class UserVO implements Serializable {

    private static final long serialVersionUID = -1610108138813541745L;

    @ApiModelProperty(value = "id", required = false)
    private String id;

    // 名称
    @ApiModelProperty(value = "姓名", required = false)
    private String name;

    // 昵称
    @ApiModelProperty(value = "昵称", required = false)
    private String nickName;

    // 密码
    @ApiModelProperty(value = "密码", required = false)
    private String pwd;

    // 交易密码
    @ApiModelProperty(value = "交易密码", required = false)
    private String payPwd;

    // 验证码
    @ApiModelProperty(value = "验证码", required = false)
    private String vcode;

    // 所属人id
    @ApiModelProperty(value = "机构id", required = false)
    private String ownerId;

    //页数
    @ApiModelProperty(value = "页数", required = true)
    private Integer page;

    //每页条数
    @ApiModelProperty(value = "每页条数", required = true)
    private Integer limit;

    // 状态(0.正常 1.冻结)
    @ApiModelProperty(value = "状态(0.正常 1.冻结)", required = false)
    private String status;

    // 是否认证
    @ApiModelProperty(value = "认证(0.未认证 1.处理中 2.已认证 3.认证失败)", required = false)
    private String examine;

    @ApiModelProperty(value = "手机号", required = false)
    private String mobile;

    @ApiModelProperty(value = "是否已删除(0.未删除 1.已删除）", required = false)
    private String isDelete;

    @ApiModelProperty(value = "编号", required = false)
    private String code;

    @ApiModelProperty(value = "角色", required = false)
    private String role;

    @ApiModelProperty(value = "角色id", required = false)
    private String roleId;

    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    //创建开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建开始时间", required = false)
    private Date beginDate;

    //创建结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建结束时间", required = false)
    private Date endDate;

    //认证开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "认证开始时间", required = false)
    private Date examineBeginDate;

    //认证结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "认证结束时间", required = false)
    private Date examineEndDate;

    // 证件类型
    @ApiModelProperty(value = "证件类型(0:身份证)", required = false)
    private String cardType;

    // 性别
    @ApiModelProperty(value = "性别", required = false)
    private String sex;

    // 身份证号
    @ApiModelProperty(value = "身份证号", required = true)
    private String idCard;

    // 身份证正面
    @ApiModelProperty(value = "身份证正面", required = true)
    private String idCardFront;

    // 身份证反面
    @ApiModelProperty(value = "身份证反面", required = true)
    private String idCardBack;

    // 地址
    @ApiModelProperty(value = "地址", required = false)
    private String address;


    //邀请码
    @ApiModelProperty(value = "邀请码", required = false)
    private String invitationCode;

    //注册码
    @ApiModelProperty(value = "注册码", required = false)
    private String pollCode;

    @ApiModelProperty(value = "上级用户姓名", required = false)
    private String parentName;
}
