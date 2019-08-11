package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

import redis.clients.jedis.Jedis;

public class TestRedis {
	
	//String类型的操作方式
	//IP:端口号:
	@Test
	public void testString() {
		Jedis jedis = new Jedis("192.168.181.129", 6379);
		
		String result = jedis.set("1902", "1902");
//		System.out.println(result);
//		System.out.println(jedis.get("1902"));
//		Set<String> keys = jedis.keys("*");
//		System.out.println(keys);
		//jedis.flushDB();
		jedis.incr("1902");
		jedis.expire("1902",10);
		System.out.println(jedis.get("1902"));
		
	}
	
	//设定数据超时的方法
	@Test
	public void testTimeOut() throws InterruptedException {
		Jedis jedis = new Jedis("192.168.181.129", 6379);
		jedis.setex("silai", 2, "muer");
		System.out.println(jedis.get("silai"));
		
		System.out.println("======================");
		Thread.sleep(3000);
		
		//当key不存在时操作正常,当Key存在时操作不正常
		Long result = jedis.setnx("silai","复读机");
		System.out.println(result+jedis.get("silai"));
	}
	
	//实现对象转换Json
	@Test
	public void ObjectToJson() throws IOException {
		ItemDesc item = new ItemDesc();
		item.setItemId(999L).setItemDesc("测试方法哟");
		
		ObjectMapper mapper = new ObjectMapper();
		//转换为Json时必须有get set方法
		String json = mapper.writeValueAsString(item);
		System.out.println(json);
		
		//将Json串转化为对象
		ItemDesc readValue = mapper.readValue(json,ItemDesc.class);
		System.out.println("测试对象:"+readValue);
	}
	
	//实现List集合与Json之间的转换
	@Test
	public void ListToJson() throws IOException {
		ItemDesc item = new ItemDesc();
		item.setItemId(999L).setItemDesc("测试方法哟");
		ItemDesc item2 = new ItemDesc();
		item.setItemId(998L).setItemDesc("测试方法哟");
		
		List<ItemDesc> list = new ArrayList<ItemDesc>();
		list.add(item);
		list.add(item2);
		
		//转换为json
		ObjectMapper mapper = new ObjectMapper();
		String asString = mapper.writeValueAsString(list);
		System.out.println("Json:");
		System.out.println(asString);
		
		Jedis jedis = new Jedis("192.168.181.129", 6379);
		jedis.set("ItemDescList",asString);
		String result = jedis.get("ItemDescList");
		
		//从redis中获取数据,json转为对象集合List
		@SuppressWarnings("unchecked")
		List<ItemDesc> readValue = mapper.readValue(result,list.getClass());
		
		System.out.println("==========================");
		System.out.println("List<ItemDesc>:");
		System.out.println(readValue);
		
		
	}
	/**
	 *  利用Redis保存业务数据 数据库
	 *  数据库数据: 对象 Object
	 *  String类型要求只能存储字符串类型
	 *  item --- Json --- 字符串
	 */
	@Test
	public void testSetObject() {
		Item item = new Item();
		item.setId(100L).setTitle("测测测");
		
		Jedis jedis = new Jedis("192.168.181.129", 6379);
	}
	
	class User{
		private Integer id;
		private Integer age;
		private String name;
		private String gender;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		} 
	}
	
	@Test
	public void userToJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setAge(88);
		user.setGender("男");
		user.setId(9377);
		user.setName("大力精钢");
		
		String asString = mapper.writeValueAsString(user);
		System.out.println(asString);
	}
	
	/**
	 *	1.获取userJson串
	 *  2.通过Json串获取json中Key
	 *  3.根据class类型的反射机制实例化对象
	 *  4.根据key调用setKey方法为对象赋值
	 *  5.最终生成对象
	 *  6.可以利用@JsonIgnoreProperties(ignoreUnknown = true)
	 *  	此注解 可以忽略位置属性
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public void jsonToUser() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setAge(88);
		user.setGender("男");
		user.setId(9377);
		user.setName("乌拉~~");
		String asString = mapper.writeValueAsString(user);
		
		mapper.readValue(asString,user.getClass());
	}
	
	
}
