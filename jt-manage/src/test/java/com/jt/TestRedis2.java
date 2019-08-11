package com.jt;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis2 {
	@Test
	public void testHash() {
		Jedis jedis = new Jedis("192.168.181.129", 6379);
		jedis.hset("user1", "id","298");
		jedis.hset("user1", "name","牛");
		
		String hget = jedis.hget("user1","id");
		System.out.println(hget);
		
		System.out.println(jedis.hgetAll("user1"));
	}
	
	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.181.129", 6379);
		jedis.lpush("list", "1","2","3","4","5");
		System.out.println(jedis.lpop("list"));
		System.out.println(jedis.lpop("list"));
		System.out.println(jedis.lpop("list"));
		System.out.println(jedis.lpop("list"));
		System.out.println(jedis.lpop("list"));
		System.out.println(jedis.lpop("list"));
	}
	
	@Test
	public void TestTx() {
		Jedis jedis = new Jedis("192.168.181.129", 6379);
		Transaction transaction = jedis.multi();
		
		try {
			transaction.set("aa","1902");
			transaction.set("bb",null);
			transaction.exec();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.discard();
		}
	}
}
