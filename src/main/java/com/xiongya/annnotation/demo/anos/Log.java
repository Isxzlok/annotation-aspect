package com.xiongya.annnotation.demo.anos;

import java.lang.annotation.*;

/**
 * 自定义注解
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited  //元注解是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型
            // 被用于一个class，则这个annotation将被用于该class的子类。
public @interface Log {

    String name() default "";
}
