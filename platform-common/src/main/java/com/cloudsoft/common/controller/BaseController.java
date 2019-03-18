package com.cloudsoft.common.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cloudsoft.common.domain.ResultData;
import com.cloudsoft.common.util.RequestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseController {

	protected String getSuccessByData(ResultData resultData){
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", "100");
		resultObject.put("msg", "数据请求成功");
		resultObject.put("responseData", resultData.getData());
		return resultObject.toJSONString(resultObject,SerializerFeature.WriteNullStringAsEmpty);
	}

	protected String getSuccessByMsg(ResultData resultData) {
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", "100");
		resultObject.put("msg", resultData.getMsg());
		resultObject.put("responseData", resultData.getData());
		return resultObject.toJSONString(resultObject,SerializerFeature.WriteNullStringAsEmpty);
	}

	protected String getSuccessByAll(ResultData resultData) {
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", resultData.getCode());
		resultObject.put("msg", resultData.getMsg());
		resultObject.put("responseData", resultData.getData());
		return resultObject.toJSONString(resultObject, SerializerFeature.WriteNullStringAsEmpty);
	}


	protected String getErrorMsgInfo(ResultData resultData){
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", resultData.getCode());
		resultObject.put("msg", resultData.getMsg());
		resultObject.put("responseData", resultData.getData());

		return resultObject.toJSONString();
	}

	protected String getSuccessInfo(){
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", "100");
		resultObject.put("msg", "数据请求成功");
		resultObject.put("responseData", "");
		return resultObject.toJSONString();
	}

	protected String getFailureInfo(){
		return getFailureInfo("数据请求失败，请重试");
	}

	protected String getFailureInfo(String msg){
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", "101");
		resultObject.put("msg", msg);
		resultObject.put("responseData", "");
		return resultObject.toJSONString();
	}

	protected String getLoginFailureInfo(){
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", "111");
		resultObject.put("msg", "请重新登录");
		resultObject.put("responseData", "");
		return resultObject.toJSONString();
	}

	protected String getNoLoginInfo(){
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", "111");
		resultObject.put("msg", "请先登录再访问");
		resultObject.put("responseData", "");
		return resultObject.toJSONString();
	}

	protected String getUnauthorizedInfo(){
		JSONObject resultObject = new JSONObject();
		resultObject.put("code", "112");
		resultObject.put("msg", "没有权限访问");
		resultObject.put("responseData", "");
		return resultObject.toJSONString();
	}


	protected String getFieldError(BindingResult br){
		List<FieldError> fieldErrors = br.getFieldErrors();
		// 遍历不合法字段
		List<String> list = new ArrayList<String>();
		JSONObject resultObject = new JSONObject();
		fieldErrors.forEach(
				fieldError -> {
					list.add("错误参数 : "+fieldError.getField()+" ,信息内容 : {"+fieldError.getDefaultMessage()+"}" );
				}
		);
		resultObject.put("code", "113");
		resultObject.put("msg", "请求参数有误");
		resultObject.put("responseData", JSON.toJSON(list));
		return resultObject.toJSONString();
	}

	protected String getSystemPath(){
		return RequestUtils.getRequest().getScheme() + "://" + RequestUtils.getRequest().getServerName() + ":" + RequestUtils.getRequest().getServerPort() + RequestUtils.getRequest().getContextPath() + "/";
	}

//	protected String getPartnerNo(){
//		Map<String, Object> userInfo = (Map<String, Object>)RequestUtil.getRequest().getAttribute("userInfo");
//		return userInfo.get("partnerNo").toString();
//	}

	protected String getUserId(){
		Map<String,Object> userInfo = (Map<String, Object>) RequestUtils.getRequest().getAttribute("userInfo");
		return userInfo.get("userId") + "";
	}




}
