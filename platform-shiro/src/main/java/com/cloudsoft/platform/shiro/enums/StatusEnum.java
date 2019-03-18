package com.cloudsoft.platform.shiro.enums;



public enum StatusEnum {
	/** 有效 */
	VALID("VALID"),
	
	/** 无效 */
	INVALID("INVALID")
	
	;

	private final String value;
	
	private StatusEnum(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
