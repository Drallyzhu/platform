package com.cloudsoft.platform.shiro.enums;

import lombok.Getter;

/**
 * 错误信息枚举工具类
 */
@Getter
public enum BizErrorCode  {
	/**请求成功*/
	SUCCESS("0000","请求成功"),
    //========================================================================//
    //                              系统错误
    //========================================================================//
	/**系统未知错误*/
    UNKNOWN_EXCEPTION("9999","系统未知错误"),

    UNFULL_PARAMETER("1001","参数不全"),
    ;

    /** 操作代码 */
    private final String code;

    /** 描述 */
    private final String msg;

    private BizErrorCode(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return
     */
    public static BizErrorCode getByCode(String code) {
        for (BizErrorCode status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
