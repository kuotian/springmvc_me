package com.hdu.springmvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target\@Retention：元注解，作用就是给注解进行解释的
//@Target：最终我们这个注解是要作用在什么地方（类、方法或者字段）
//ElementType.TYPE：类
//ElementType.METHOD：方法
//@Retention：最终我们这个注解是要保留到哪个阶段（源代码、class文件、运行时环境）
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseBody {

    // RequestMapping(value="")
    String value() default "";
}
