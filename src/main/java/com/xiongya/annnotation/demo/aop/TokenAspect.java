package com.xiongya.annnotation.demo.aop;


import com.xiongya.annnotation.demo.anos.IgnoreToken;
import com.xiongya.annnotation.demo.controller.LoginController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
@Aspect  //切面注解，通常作用于类上，在类里面可以定义切入点和通知
@Order(1)  //设置该类在spring容器中的加载顺序
public class TokenAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private LoginController loginController;

    //JoinPoint的集合，是程序中需要注入Advice的位置的集合，指明Advice要在什么样的条件下才能被触发，在程序中主要体现为书写切入点表达式
    //这个便是拦截controller包以及它的子包中的所有类
    @Pointcut("within(com.xiongya.annnotation.demo.controller..*)")
    public void checkToken () {}  //签名

    @Before("checkToken()")
    public void checkToken(JoinPoint point) throws IOException {
        //返回目标方法的签名（方法签名：方法声明的两个组件构成了方法签名 - 方法的名称和参数类型。）
        MethodSignature signature = (MethodSignature)point.getSignature();
        //获取目标方法对象
        Method targetMethod = signature.getMethod();
        //判断目标方法是否被IgnoreToken注解修饰，如果是则不需要校验token
        if (!targetMethod.isAnnotationPresent(IgnoreToken.class)){
            //该方法没有被注解修饰，检验token
            //获取前台传过来的token
            String token  = request.getParameter("token");
            if (null == token || "".equals(token)){

                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("token不能为空");  //这里注意，因为response向前台发送的是json格式的数据，而现在发送的是String类型，所以在前台要使用text接收
                out.flush();
                out.close();
            }else{
                //token不为空，校验token
                if (!loginController.chkToken(token)){
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.print("token不合法");
                    out.flush();
                    out.close();
                }


            }


        }

    }


}
