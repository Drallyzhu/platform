package com.cloudsoft.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhujianrong
 */
public class JWTParse {

	// 该方法使用HS256算法和Secret:bankgl生成signKey(如果没有传key，就默认已公司英文来做key)
	private static Key getKeyInstance() {
		// We will sign our JavaWebToken with our ApiKey secret
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(TextCodec.BASE64.encode("cloudsoft"));
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		return signingKey;
	}

	private static Key getKeyInstance(String key) {
		// We will sign our JavaWebToken with our ApiKey secret
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(TextCodec.BASE64.encode(key));
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		return signingKey;
	}

	// 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
	public static String createWebToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
	}

	// 解析Token，同时也能验证Token，当验证失败返回null
	public static Map<String, Object> parserWebToken(String jwt, String key) {

		Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance(key)).parseClaimsJws(jwt).getBody();
		return jwtClaims;

	}

	/**
	 * 返回per中第一个key。暂时只支持一家企业
	 * 
	 * @param per
	 * @return
	 */
	public static String getCorpFromPer(Map<String, Map<String, Integer>> per) {
		if (per == null) {
			return null;
		}
		for (String str : per.keySet()) {
			return str;
		}
		return null;
	}

	public static Jws<Claims> parserToken(String token, String key) throws Exception {
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
		return claimsJws;
	}

	public static void main(String[] str) throws Exception {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("cloudsoft", "");
		String s = JWTParse.createWebToken(claims);
		System.out.println(s);

		String token = "eyJhbGciOiJIUzI1NiJ9.eyJhYSI6ImFhYSJ9.gLjIid2PuCqMlzrVZqASrjX8bRjA6ht7YYe2b7rAedE";

		String key = "miffy";
		Map<String, Object> m = JWTParse.parserWebToken(token, key);

		System.out.println(m);
		System.out.println(m.get("dd"));
		System.out.println("-------------");
	}
}
