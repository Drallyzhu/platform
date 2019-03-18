package com.cloudsoft.platform.shiro.enums;



public enum SystemEnum {
	/** 是否添加Url执行shiro认证和授权,开发环境为N,测试和生产环境必须为Y */
	ISSHIROAUTHCANDPERMSOFTRUE("Y"),
	ISSHIROAUTHCANDPERMSOFFALST("N"),

	/** 时候验证token,开发环境为N,测试和生产环境必须为Y */
	ISUNTONKENOFTRUE("Y"),
	ISUNTONKENOFFALSE("N")

	;

	private final String value;

	private SystemEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
