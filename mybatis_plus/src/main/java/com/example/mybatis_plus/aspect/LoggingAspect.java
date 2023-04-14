package com.example.mybatis_plus.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author duran
 * @description TODO
 * @date 2023-03-17 15:17
 */
@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.example.mybatis_plus.annotation.MyLog)")
    public void myPointCut() {
        //签名，可以理解成这个切入点的一个名称
    }

    @Before("execution(* com.example.mybatis_plus.product.service.impl.ProductServiceImpl.*(..))")
//    @Before("myPointCut()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before executing method: {}", joinPoint.getSignature().getName());
        logger.info("Before executing args: {}", joinPoint.getArgs());
    }

    @After("execution(* com.example.mybatis_plus.product.service.impl.ProductServiceImpl.*(..))")
//    @After("myPointCut()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("After executing method: {}", joinPoint.getSignature().getName());
    }

    @Around("execution(* com.example.mybatis_plus.product.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info("====== 开始执行 {}.{} ======",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());

        // 记录开始时间
        long begin = System.currentTimeMillis();

        // 执行目标 service
        Object result = joinPoint.proceed();

        // 记录结束时间
        long end = System.currentTimeMillis();
        long takeTime = end - begin;

        if (takeTime > 3000) {
            logger.error("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        } else if (takeTime > 2000) {
            logger.warn("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        } else {
            logger.info("====== 执行结束，耗时：{} 毫秒 ======", takeTime);
        }

        return result;
    }
}
