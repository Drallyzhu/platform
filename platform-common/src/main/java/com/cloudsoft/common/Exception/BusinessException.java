package com.cloudsoft.common.Exception;

/**
 * @author zhujianrong
 * @date 2018-11-8 20:41
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 3006991224624822528L;

	private final String code;

	public BusinessException(String code) {
		super();
		this.code = code;
	}
	public BusinessException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
