package com.cloudsoft.jwt;

/**
 * Created by ace on 2017/9/10.
 */
public class JwtTokenUtil {

	private static final int EXPIRE = 3000;
	private static final String PRIVATEKEY = "privateKey";
	private static final String PUBLICKEY = "publicKey";

	public static String generateToken(JWTInfo jwtInfo) throws Exception {
		return JWTHelper.generateToken(jwtInfo, PRIVATEKEY, EXPIRE);
	}

	public static JWTInfo getInfoFromToken(String token) throws Exception {
		return JWTHelper.getInfoFromToken(token, PUBLICKEY);
	}

}
