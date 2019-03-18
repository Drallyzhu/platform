package com.cloudsoft.security.enumeration;


import com.cloudsoft.security.core.BaseResponse;

/**
 * @author zhujianrong
 */
public enum AccessError {
    ACCESS_LEVEL_ERROR(40001),
    INVALID_PIN_APPID(40002),
    WECHAT_FAIL_TO_DECRYPT_INFO(40003),
    TOKEN_SIGNATURE_VERIFY_FAIL(40301),
    TOKEN_EXPIRED(40302),
    TOKEN_FAILURE(40303),
    ENDPOINT_NOT_FOUND(40400),
    ACCOUNT_NOT_FOUND(40401),
    SERVER_DB_ERROR(50001),
    SERVER_UNKNOWN_ERROR(50002);
    private int code;

    AccessError(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BaseResponse withMsg(String msg) {
        return new BaseResponse(this.getCode(), msg);
    }

    @Override
    public String toString() {
        return "{\"code\":" + this.code + "}";
    }
}
