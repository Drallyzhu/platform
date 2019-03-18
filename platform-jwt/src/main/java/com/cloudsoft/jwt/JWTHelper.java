package com.cloudsoft.jwt;

import com.cloudsoft.common.util.RsaKeyHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhujianrong
 */
public class JWTHelper {
	private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

	/**
	 * 密钥加密token
	 *
	 * @param jwtInfo
	 * @param priKeyPath
	 * @param expire
	 * @return
	 * @throws Exception
	 */
	public static String generateToken(JWTInfo jwtInfo, String priKeyPath, int expire) throws Exception {
		String compactJws = Jwts.builder().setSubject(jwtInfo.getSub()).claim(CommonConstants.AUD, jwtInfo.getAud())
				.claim(CommonConstants.PER, jwtInfo.getPer()).claim(CommonConstants.COR, jwtInfo.getCor())
				.setExpiration(DateTime.now().plusSeconds(expire).toDate())
				.signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyPath)).compact();
		return compactJws;
	}

	/**
	 * 密钥加密token
	 *
	 * @param jwtInfo
	 * @param priKey
	 * @param expire
	 * @return
	 * @throws Exception
	 */
	public static String generateToken(JWTInfo jwtInfo, byte priKey[], int expire) throws Exception {
		String compactJws = Jwts.builder().setSubject(jwtInfo.getSub()).claim(CommonConstants.AUD, jwtInfo.getAud())
				.claim(CommonConstants.PER, jwtInfo.getPer())
				.claim(CommonConstants.COR, jwtInfo.getCor())
				.setExpiration(DateTime.now().plusSeconds(expire).toDate())
				.signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKey)).compact();
		return compactJws;
	}

	/**
	 * 公钥解析token
	 *
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Jws<Claims> parserToken(String token, String pubKeyPath) throws Exception {
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKeyPath))
				.parseClaimsJws(token);
		return claimsJws;
	}

	/**
	 * 公钥解析token
	 *
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception {
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKey)).parseClaimsJws(token);
		return claimsJws;
	}

	/**
	 * 获取token中的用户信息
	 *
	 * @param token
	 * @param pubKeyPath
	 * @return
	 * @throws Exception
	 */
	public static JWTInfo getInfoFromToken(String token, String pubKeyPath) throws Exception {
		Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
		Claims body = claimsJws.getBody();

		Map<String, Map<String, Integer>> per = (Map<String, Map<String, Integer>>) body.get(CommonConstants.PER);
		return new JWTInfo(body.getSubject(), (String) body.get(CommonConstants.AUD),
				(String) body.get(CommonConstants.COR), per);
	}

	/**
	 * 获取token中的用户信息
	 *
	 * @param token
	 * @param pubKey
	 * @return
	 * @throws Exception
	 */
	public static JWTInfo getInfoFromToken(String token, byte[] pubKey) throws Exception {
		Jws<Claims> claimsJws = parserToken(token, pubKey);
		Claims body = claimsJws.getBody();
		Map<String, Map<String, Integer>> per = (Map<String, Map<String, Integer>>) body.get(CommonConstants.PER);
		return new JWTInfo(body.getSubject(), (String) body.get(CommonConstants.AUD),
				(String) body.get(CommonConstants.COR), per);
	}

	public static void main(String[] str) throws Exception {
		Map<String, Map<String, Integer>> corp = new HashMap();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < 1; i++) {

			map.put("aaaaaaaaaaaaa" + i, 123);

		}
		corp.put("001", map);

		JWTInfo info = new JWTInfo("a", "b", "001", corp);

		String prikey = "xx1WET12^%3^(WE45";
		String pubkey = "123456";
		String token = JWTHelper.generateToken(info, "com/cloudsoft/jwt/privateKey", 100);
		System.out.println(token);

		JWTInfo info2 = JWTHelper.getInfoFromToken(token, "publicKey");

		System.out.println(info2.getAud());
		System.out.println(info2.getCor());
		System.out.println(info2.getPer().size());

		Map<String, Integer> resource = info2.getPer().get("001");
		Iterator itor = resource.keySet().iterator();
		while (itor.hasNext()) {
			String key = (String) itor.next();
			Integer value = map.get(key);
			System.out.println(key + " = " + value);
		}

	}
}
