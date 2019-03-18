package com.cloudsoft.common.domain;


import java.io.Serializable;

public class ResultData<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	// 处理成功
	public static final String OK = "0000";
	public static final String OK_MSG = "操作成功";
	// 未知错误
	public static final String UNKNOWN = "9999";
	public static final String UNKNOWN_MSG = "操作失败";

	// 程序错误
	public static final String ERR = "5000";
	public static final String ERR_MSG = "程序错误";

	public static final ResultData SUCESS = new ResultData(OK, OK_MSG);
	public static final ResultData ERROR = new ResultData(ERR, ERR_MSG);
	public static final ResultData UNKNOWN_EXCEPTION = new ResultData(UNKNOWN, UNKNOWN_MSG);

	private String code = UNKNOWN;
	private String msg = "";
	private T data;

	/**
	 * 失败
	 * @return
	 */
	public static ResultData getFailResult(){
		return new ResultData(ERR, ERR_MSG);
	}

	public static ResultData ERROR(String msg){
		return new ResultData("-1", msg);
	}

	/**
	 * 失败
	 * @param message
	 * @return
	 */
	public static ResultData getFailResult(String message){

		return new ResultData(ERR, message);
	}

	/**
	 * 失败
	 * @param code
	 * @param message
	 * @return
	 */
	public static ResultData getFailResult(String code,String message){

		return new ResultData(code, message);
	}

	/**
	 * 成功
	 * @param message
	 * @return
	 */
	public static ResultData getSuccessResult(String message){
		return new ResultData(OK, message);
	}

	public ResultData toOK(){
		return ResultData.SUCESS;
	}

	/**
	 * 成功
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ResultData getSuccessData(T data){
		return new ResultData(OK, OK_MSG, data);
	}

	/**
	 * 成功
	 * @param data
	 * @param message
	 * @return
	 */
	public static <T> ResultData getSuccessResult(T data, String message){
		return new ResultData(OK, message, data);
	}

	public ResultData(){
	}

	public ResultData(String code, String message){
		this.code = code;
		this.msg = message;
	}

	public ResultData(String code, String message, T result){
		this.code = code;
		this.msg = message;
		this.data = result;
	}

	public ResultData(T result){
		this(OK, "操作成功！", result);
	}






	public T getData(){
		return data;
	}

	public ResultData setData(T data){
		this.data = data;
		return this;
	}

	public String getCode(){
		return code;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getMsg(){
		return msg;
	}

	public void setMsg(String message){

		this.msg = message;
	}
}
