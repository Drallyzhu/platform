package com.cloudsoft.platform.securityoauth2.exception;

public class HandleException extends RuntimeException {
    private static final long serialVersionUID = 7666240839844251335L;
    private  Integer code;
    private String msg;
    public HandleException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode(){
        return this.code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
