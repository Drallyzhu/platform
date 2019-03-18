package com.cloudsoft.jwt;

/**
 * @author zhujianrong
 */
public class CommonConstants {
    
    public static final Integer EX_TOKEN_ERROR_CODE = 40101;
    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 40102;
    // 客户端token异常
    public static final Integer EX_CLIENT_INVALID_CODE = 40131;
    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40331;
    public static final Integer EX_OTHER_CODE = 500;
    

    public static final String SUB = "sub";
    public static final String EXP = "exp";
    public static final String IAT = "iat";
    public static final String AUD = "aud";
    public static final String PER  = "per";
    public static final String COR  = "cor";
}
