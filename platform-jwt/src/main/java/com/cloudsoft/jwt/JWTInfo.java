package com.cloudsoft.jwt;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author zhujianrong
 * jwt 基础参数
 *
 */
public class JWTInfo implements Serializable {
	private static final long serialVersionUID = -1704047991031481047L;
	/**
	 * 企业ID,暂时只支持一个
	 */
	private String cor;

	/**
	 * 存放sf_uid
	 */
	private String sub;
	/**
	 * 存放appid
	 */
	private String aud;
	/**
	 * 授权信息
	 */
	private Map<String, Map<String, Integer>> per;

	public JWTInfo() {
	}

	public JWTInfo(String sub, String aud, String cor, Map<String, Map<String, Integer>> per) {
		this.sub = sub;
		this.aud = aud;
		this.cor = cor;
		this.per = per;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getAud() {
		return aud;
	}

	public void setAud(String aud) {
		this.aud = aud;
	}

	public Map<String, Map<String, Integer>> getPer() {
		return per;
	}

	public void setPer(Map<String, Map<String, Integer>> per) {
		this.per = per;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

}
