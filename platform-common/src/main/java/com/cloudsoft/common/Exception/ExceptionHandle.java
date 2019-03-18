package com.cloudsoft.common.Exception;

import com.alibaba.fastjson.JSONObject;
import com.cloudsoft.common.domain.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author zhujianrong
 * @date 2018-11-8 11:28
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultData Handle(Exception e){
        if (e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            return ResultData.getFailResult(businessException.getCode(),businessException.getMessage());
        }else if(e instanceof ConstraintViolationException){
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
            Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
            List<String> msgList = new ArrayList<>();
            while (iterator.hasNext()) {
                ConstraintViolation<?> cvl = iterator.next();
                msgList.add(cvl.getMessageTemplate());
            }
            return ResultData.getFailResult(JSONObject.toJSONString(msgList));
        }else {
            //将系统异常以打印出来
            logger.info("[系统异常]{}",e);
            return ResultData.getFailResult("未知错误");
        }

    }
}
