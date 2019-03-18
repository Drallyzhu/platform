package com.cloudsoft.platform.securityoauth2.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 * 这个注解是，spring data jpa 查询出来对象，是持久化，如果对象set值时候，方法报错，那么他隐形保存你set的参数，这样就出错了
 */
@Aspect
@Order(5)
@Component
public class SessionAspect {

    private static final Logger logger = Logger.getLogger("SessionAspect");

    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("@annotation(com.cloudsoft.platform.securityoauth2.aspect.EvictSession)")
    public void permissions() {
        // pointcut mark
    }

    @AfterReturning(returning = "object", pointcut = "permissions()")
    public void doAfterReturning(Object object) throws Throwable {
        if (null != object){
            Session session = entityManager.unwrap(Session.class);
            session.evict(object);
        }
    }
}
