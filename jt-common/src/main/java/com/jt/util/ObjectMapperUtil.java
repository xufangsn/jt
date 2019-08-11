package com.jt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

//编辑工具类实现对象与json转化
public class ObjectMapperUtil {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	//
	public static String toJSON(Object target) {
		String json = null;
		try {
			json = MAPPER.writeValueAsString(target);
		} catch (Exception e) {
			e.printStackTrace();
			//将检查异常转化为运行时异常
			throw new RuntimeException();
		}
		
		return json;
	}
	
	public static <T>T toObject(String json,Class<T> targetClass){
		//初始定义一个对象为null,若运行出问题则返回null
		T target = null;
		try {
			target = MAPPER.readValue(json,targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return target;
	}
	
	
}
