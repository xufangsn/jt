package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestSentinel {
	//
	@Test
	public void test01() {
		//masterName 代表主机的变量名称
		//sentinels Set<String> IP:端口
		
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.181.129:26379");
		JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);
		
		Jedis jedis = sentinelPool.getResource();
		jedis.set("cc","端午节后没假了!");
		System.out.println(jedis.get("cc"));
		jedis.close();
		
	}
}
