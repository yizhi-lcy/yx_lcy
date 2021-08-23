package com.baizhi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;  //对字符串支持比较友好,不能存储对象
    @Autowired
    private RedisTemplate redisTemplate;  //存储对象

    //切面类中的环绕通知
    //配置切点
    //  execution()：方法级别
    //  within()：类级别
    //  @annotation()：注解方式
    /*@Around("")
    public Object addCache(ProceedingJoinPoint JoinPoint){
        System.out.println("进入环绕通知");

        StringBuilder key = new StringBuilder();
        //获得类的全限定名
        String className = JoinPoint.getTarget().getClass().getName();
        key.append(className);
        //获得方法名
        String methodName = JoinPoint.getSignature().getName();
        key.append(methodName);
        //实参值
        Object[] args = JoinPoint.getArgs();
        for (Object arg : args) {
            key.append(arg);
        }

        //键序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //return null;//表示程序不向下执行

        //放行请求，执行目标方法，并把执行目标方法的返回值返回
        Object o =null;
        try {
            o = JoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        //redis键值对的形式存储
        //key：类的全限定名+方法名+实参
        //value：查询结果

        return o;//将执行后的方法返回值返回到客户端
    }*/
    //只要执行了增删改就清楚缓存

}
