package com.cloudsoft.platform.shiro.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * 数据加密工具类
 * @author zhujianrong
 * @date 2018-09-12
 *
 */
public class DesUtil {
	private final static String DES = "DES";
	private final static String KEY = "#@!&*123cloudsoft_";

	public static String encrypt(String content, String password){
		if(StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(password)){
			try{
				byte[] bt = encrypt(content.getBytes(), password.getBytes());
				bt = Base64.encodeBase64(bt);
				bt = Base64.encodeBase64(bt);
				bt = Base64.encodeBase64(bt);
				return Base64.encodeBase64String(bt);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String encrypt(String content){
		return encrypt(content, KEY);
	}

	public static String decrypt(String content){
		return decrypt(content, KEY);
	}

	public static String decrypt(String content, String password){
		if(StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(password)){
			try{
				byte[] buf = Base64.decodeBase64(content);
				buf = Base64.decodeBase64(buf);
				buf = Base64.decodeBase64(buf);
				buf = Base64.decodeBase64(buf);
				byte[] bt = decrypt(buf, password.getBytes());
				return new String(bt);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

	private static byte[] encrypt(byte[] data, byte[] key) throws Exception{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}

	private static byte[] decrypt(byte[] data, byte[] key) throws Exception{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}
}
