package com.jt.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.annotation.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.RedisService;

import redis.clients.jedis.JedisCluster;


@Aspect	//标识切面
@Component	//将对象交给spring容器管理
public class RedisAspect {
	//容器初始化时不需要实例化该对象
	//只有用户使用时才初始化
	//一般工具类中添加该注解
	@Autowired(required = false)
	private JedisCluster jedis;
	
//	private ShardedJedis jedis;
//	@Pointcut("@annotation(com.jt.annotation.RedisCache)")
//	public void doCache() {}
	
	//使用该方法可以直接获取注解对象
	@SuppressWarnings("unchecked")
	@Around("@annotation(cache_find)")
	public Object around( ProceedingJoinPoint joinPoint,Cache_Find cache_find){
		
		//1.获取key的值
		String key = getKey(joinPoint,cache_find);
		//2.根据key查询缓存
		String result = jedis.get(key);
		Object data = null;
		try {
			if(StringUtils.isEmpty(result)) {
				//查询结果为null,表示缓存中没有数据
				//查询数据库
				data = joinPoint.proceed();
				
				//将数据转化为JSON串
				String json = ObjectMapperUtil.toJSON(data);
				//判断用户是否设定了超时时间
				if(cache_find.secondes()==0) 
					//表示不要超时
					jedis.set(key, json);
				else 
					jedis.setex(key, cache_find.secondes(),json);
				System.out.println("第一次查询");
			}else {
				Class targetClass = getClass(joinPoint);
				data = ObjectMapperUtil.toObject(result, targetClass);
				System.out.println("AOP查询");
			}

		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
//		List<EasyUITree> treeList = new ArrayList<EasyUITree>(); 
//			//查看缓存中是否有此信息
//			//需要参数如何获得参数?(如客户端传过来的parentId)
//			//执行目标方法
//			treeList = (List<EasyUITree>) pj.proceed();
			
		return data;
	}

	//获取返回值类型
	private Class getClass(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getReturnType();
	}

	/**
	 * 1.判断key的类型  auto enum
	 * @param joinPoint
	 * @param cache_find
	 * @return
	 */
	private String getKey(ProceedingJoinPoint joinPoint, Cache_Find cache_find) {
		
		//1.获取key的类型
		KEY_ENUM key_ENUM = cache_find.keyType();
		
		//2.判断key类型
		if(key_ENUM.equals(KEY_ENUM.EMPTY)) {
			//表示使用用户自己的key
			return cache_find.key();
		}
		
		//表示用户的key需要拼接 key+"_"+第一个参数
		String strArgs = String.valueOf(joinPoint.getArgs()[0]);
		String key = cache_find.key()+"_"+strArgs;
		return key;
	}
	
}
