package com.xiongya.annnotation.demo.aop;


import com.xiongya.annnotation.demo.anos.Log;
import com.xiongya.annnotation.demo.controller.LoginController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@Aspect
@Order(2)
public class LogAspect {

    @Autowired
    private HttpServletResponse request;

    @Autowired
    private LoginController loginController;

    @Pointcut("@annotation(com.xiongya.annnotation.demo.anos.Log)")  //带有Log注解的任意方法
    public void saveLog(){}

    @Around("saveLog()")
    public void saveLog(ProceedingJoinPoint point) throws Throwable{
        //获取当前系统时间
        long start = System.currentTimeMillis();
        //获取方法签名
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        //获取方法上的注解对象
        Log logAnnotation = method.getAnnotation(Log.class);
        String name = null;
        if (logAnnotation != null){
            //注解上的描述
            name = logAnnotation.name();
        }
        //
        String className = point.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = signature.getName();
        //获取请求方法的参数值
        Object[] args = point.getArgs();
        //获取请求方法的参数名
        //String[] paramNames = signature.getParameterNames();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        String params = "";
        if (args != null && paramNames != null){
            for (int i = 0; i < args.length; i++){
                params += "  " + paramNames[i] + ":" + args[i];
            }
        }

        long time = System.currentTimeMillis() - start;
        StringBuffer log = new StringBuffer();
        log.append("注解上的name:").append(name).append("=====")
                .append("请求的方法：").append(className).append(".").append(methodName)
                .append("=====").append("请求的参数：").append(params).append("=====")
                .append("耗时").append(time).append("ms");
        System.out.println(log.toString());
        loginController.saveLog(log.toString());
       point.proceed();
    }
}
