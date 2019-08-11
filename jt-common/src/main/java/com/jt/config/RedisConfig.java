package com.jt.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

//表示Redis配置类
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.nodes}")
	private String redisNodes;
	
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		//1.按照,拆分 再按照:拆分
		String[] strs = redisNodes.split(",");
		for (String string : strs) {
			String host = string.split(":")[0];
			int port = Integer.valueOf(string.split(":")[1]);
			
			nodes.add(new HostAndPort(host, port));
		}
				
		return new JedisCluster(nodes);
	}
}
	
	/*
	 * @Value("${redis.sentinel.masterName}") private String masterName;
	 * 
	 * @Value("${redis.sentinel}") private String jedisSentinelNodes;
	 * 
	 * @Bean public JedisSentinelPool jedisSentinelPool() { Set<String> sentinels =
	 * new HashSet<>(); sentinels.add(jedisSentinelNodes); return new
	 * JedisSentinelPool(masterName, sentinels);
	 * 
	 * }
	 */
	/*@Value("${redis.nodes}")
	private String redisNodes;
	
	@Bean
	public ShardedJedis shardedJedis() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		String[] nodes = redisNodes.split(",");
		for (String node : nodes) {
			String host = node.split(":")[0];
			int port = Integer.parseInt(node.split(":")[1]) ;
			JedisShardInfo info = new JedisShardInfo(host,port);
			shards.add(info);
		}
		return new ShardedJedis(shards);*/
	/*
	 * @Value("${jedis.host}") private String host;
	 * 
	 * @Value("${jedis.port}") private Integer port;
	 * 
	 * @Bean public Jedis jedis() { return new Jedis(host, port); }
	 */
