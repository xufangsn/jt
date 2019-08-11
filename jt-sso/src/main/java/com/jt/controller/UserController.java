package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster JedisCluster;
	
	/**
	 * 业务说明
	 * 校验业务是否存在
	 * http://sso.jt.com/user/check/{param}/{type}
	 * 返回值:SysResult
	 * 由于是跨域的请求
	 */
	
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,
			@PathVariable int type,
			String callback) {
		
		JSONPObject object = null;
		try {
			boolean flag = userService.checkUser(param,type);
			object = new JSONPObject(callback, SysResult.ok("OK",flag));
		} catch (Exception e) {
			e.printStackTrace();
			object = new JSONPObject(callback, SysResult.fail());
		}
		
		return object;
	}
	
	@RequestMapping("/register")
	public void saveUser(String user) {
		try {
			userService.saveUser(user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	
	}
	
	/**
	 *	利用跨域实现用户信息回显
	 * @return
	 */
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,String callback) {
		String userJSON = JedisCluster.get(ticket);
		if(StringUtils.isEmpty(userJSON))
			//回传的数据需要经过200判断
			return new JSONPObject(callback, SysResult.fail());
		else
			return new JSONPObject(callback, SysResult.ok(userJSON));
		
	}
	
}
