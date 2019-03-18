package com.cloudsoft.security.aspect;

import com.cloudsoft.security.annotation.Access;
import com.cloudsoft.security.core.AccessConfig;
import com.cloudsoft.security.core.Segment;
import com.cloudsoft.security.enumeration.AccessError;
import com.cloudsoft.security.enumeration.AccessLevel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class AccessAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccessConfig srvToken;

//    @Value("${pinfire.service_token}")
//    private String srvToken = null;

    /**
     * 只切入加了Controller注解和access注解(这个access和jpa那个不一样，这个是自定义的)的类上
     */
//    @Pointcut("execution(public * *.*(..)) && @within(accessAnnotation) && @within(controllerAnnotation)")
//    public void withActionPointcut(Access accessAnnotation, Controller controllerAnnotation) {
//    }

    @Pointcut("execution(public * *.*(..)) && @within(accessAnnotation)")
    public void withActionPointcut(Access accessAnnotation) {
    }


//    @Around(value = "withActionPointcut(accessAnnotation,controllerAnnotation)")
    @Around(value = "withActionPointcut(accessAnnotation)")
    public Object beforeControllerExecution(ProceedingJoinPoint joinPoint, Access accessAnnotation) throws Throwable {
        System.out.println("进来了吗？？？？？？？？？？？");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object[] args = joinPoint.getArgs();
        AccessLevel level = accessAnnotation.level();
        if (level == null) {
            //未配置权限，直接通过
            return joinPoint.proceed(args);
        }
        Segment segment = new Segment(request);
        logger.debug("segment->" + segment);
        if (level.equals(AccessLevel.PUBLIC)) {
            //直接通过
        } else if (level.equals(AccessLevel.USER)) {
            String cfUid = request.getHeader("X-CF-UID");
            String urlcfUid = segment.getSegment(2);
            if (cfUid == null || !cfUid.equals(urlcfUid)) {
                return AccessError.ACCESS_LEVEL_ERROR.withMsg("User Level: mismatched target app id!");
            }
        } else if (level.equals(AccessLevel.ADMIN)) {
            String appid = request.getHeader("X-CF-APPID");
            String token = request.getHeader("X-CF-Authorization");
            String urlAppid = segment.getSegment(2);

            if (appid == null || !appid.equals(urlAppid)) {
                return AccessError.ACCESS_LEVEL_ERROR.withMsg("Admin Level: mismatched target app id!");
            }
            if (token == null) {
                return AccessError.ACCESS_LEVEL_ERROR.withMsg("Admin level: missing token");
            }
        } else if (level.equals(AccessLevel.INTERNAL)) {
            String token = request.getHeader("X-CF-InternalToken");
            if (!this.srvToken.getSrvToken().equals(token)) {
                return AccessError.ACCESS_LEVEL_ERROR.withMsg("Internal level: missiong or mismatched service token");
            }
        }
        return joinPoint.proceed(args);
    }
}
