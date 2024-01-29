package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * @author:呼延涛
 * @version:1.0
 */
@Aspect
@Component
@Slf4j
public class RedisDeleteAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.controller.admin.DishController.*(..)) && @annotation(com.sky.annotation.RedisDelete)")
    public void redisDeletePointCut(){}


    /**
     * 前置通知,清理redis缓存数据
     * @param joinPoint
     */
    @Before("redisDeletePointCut()")
    public void redisDelete(JoinPoint joinPoint){
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
    }
}
