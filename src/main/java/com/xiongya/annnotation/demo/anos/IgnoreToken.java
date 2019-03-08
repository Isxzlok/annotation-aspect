package com.xiongya.annnotation.demo.anos;

import java.lang.annotation.*;

/**
 * 自定义一个注解
 */
@Target(ElementType.METHOD)  //@Target:被描述的注解可以用在什么地方（加上ElementType.METHOD参数是指该自定义注解是作用在方法上）
@Retention(RetentionPolicy.RUNTIME)  //@Retention 指定被描述的注解在什么范围内有效 （加上RetentionPolicy.RUNTIME参数，则是指在运行时有效）
@Documented //是一个标记注解，木有成员，用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。
public @interface IgnoreToken {
}
