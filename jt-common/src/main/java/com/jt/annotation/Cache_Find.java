package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jt.enu.KEY_ENUM;

//定义一个查询注解
@Retention(RetentionPolicy.RUNTIME)	//程序运行时有效
@Target(ElementType.METHOD)	//注解作用的范围
public @interface Cache_Find {
	String key() default "";	//接收用户key值
	KEY_ENUM keyType() default KEY_ENUM.AUTO;	//定义key类型
	
	int secondes() default 0; //永不失效
}
