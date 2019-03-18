package com.cloudsoft.platform.securityoauth2.aspect;

import com.cloudsoft.common.domain.ResultData;
import com.cloudsoft.common.util.IPUtils;
import com.cloudsoft.platform.securityoauth2.domain.Logs;
import com.cloudsoft.platform.securityoauth2.domain.User;
import com.cloudsoft.platform.securityoauth2.jwt.JwtTokenUtil;
import com.cloudsoft.platform.securityoauth2.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * Web层日志切面
 * @author guan
 * @version 1.0.0
 * @date 16/5/17 上午10:42.
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private static final Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private LogService logService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 记录当前线程时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 记录当前线程对象
     */
    ThreadLocal<Logs> ThreadLocalLog = new ThreadLocal<>();

    /**
     * 保存操作切点
     */
    /*@Pointcut("execution(public * com.sofyun.supplier.web..*.save*(..))")
    public void saveLog(){}*/

    /**
     * 删除操作切点
     */
    /*@Pointcut("execution(public * com.sofyun.supplier.web..*.del*(..))")
    public void delLog(){}*/

    /**
     * 更新操作切点
     */
    /*@Pointcut("execution(public * com.sofyun.supplier.web..*.update*(..))")
    public void updateLog(){}*/

    /**
     * 切点(这种是范围性，不需要自定义注解)
     */
    /*@Pointcut("saveLog() || delLog() || updateLog()")
    public void webLog(){}*/


    /**
     * 切点(这种是自定义注解)
     */
    @Pointcut("@annotation(com.cloudsoft.platform.securityoauth2.aspect.LogRecord)")
    public void webLog() {
        // pointcut mark
    }

    /**
     * 增强织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取登录用户
        User user = jwtTokenUtil.getLoginUser();
        // 创建日志实列
        Logs logs = Logs.getInstance();
        logs.setOperatorId(user.getId());
        logs.setOperatorName(user.getName());
        logs.setUserId(user.getId());
        logs.setUsername(user.getName());
        String dateStr = dateformat.format(startTime.get());
        logs.setOperatorTime(dateformat.parse(dateStr));

        // 获取访问IP
        logs.setOperateIp(IPUtils.getIpAddr(request));

        // 获取访问地址
        String iniUrl = request.getRequestURL().toString();
        StringBuffer url = new StringBuffer("");
        String[] array = iniUrl.split("/");
        for (int i = 0; i < array.length; i++) {
            if(i > 2){
                url.append(array[i]);
                url.append("/");
            }
        }
        url.deleteCharAt(url.length() - 1);
        logs.setUrl(url.toString());

        // 获取请求参数
        Object[] args = joinPoint.getArgs();
        StringBuffer param = new StringBuffer();
        for (Object obj : args){
            param.append(obj.toString()).append(",");
        }
        String str = param.toString();
        logs.setParam(str.substring(0,str.length()-1));

        ThreadLocalLog.set(logs);

    }

    /**
     * 增强织入
     * @param object
     * @throws Throwable
     */
    @AfterReturning(returning = "object", pointcut = "webLog()")
    public void doAfterReturning(Object object) throws Throwable {
        Logs logs = ThreadLocalLog.get();
        if (object instanceof ResultData){
            ResultData responseBo = (ResultData)object;
            logs.setActionName(responseBo.getCode());
            logs.setResult(responseBo.getMsg());
            logs.setStatus(responseBo.getCode()+"");
        }else{
            logs.setResult(object.toString());
        }
        logService.insert(logs);
    }

}

