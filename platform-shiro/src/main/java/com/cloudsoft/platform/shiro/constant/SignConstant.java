package com.cloudsoft.platform.shiro.constant;

public class SignConstant {


	/**
	 * 这个根据jwt加密生成（claims key是公司英文，value是当前项目名）
	 */
	public static final String PASSWORD_SALT = "eyJhbGciOiJIUzI1NiJ9.eyJjbG91ZHNvZnQiOiJiYy1iYWNrc3RhZ2UifQ.CKuFgXEejR6SOLWFBRSIl9Acp-vEzP1k07fbm7WU-Ns";

	/**
	 * 这个根据jwt加密生成（claims key是公司英文，value是"" 空字符串）
	 */
	public static final String SESSION_SALT ="eyJhbGciOiJIUzI1NiJ9.eyJjbG91ZHNvZnQiOiIifQ.da_KUw6u1Yju5Jwc1arwFiiZaSsNfw_WTKlDmXwtMI4";


	//VALID:有效  INVALID:不可用
	public static final String VALID = "VALID";
	public static final String INVALID = "INVALID";
	/**
	 * 失效时间设置为一个星期
	 * token 超时时间 24 * 60 * 60 * 1000 * 7
	 */
	public static final long USER_TOKEN_TTL = 24 * 60 * 60 * 1000 * 7;

}
