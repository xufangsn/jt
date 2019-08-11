package com.jt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jt.pojo.User;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private HttpClientService httpClient;
	
	@Override
	public void doRegister(User user) {
		String url = "http://sso.jt.com/user/register";
		//将密码加密
		String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Password);
		
		Map<String,String> params = new HashMap<>();
		params.put("user",ObjectMapperUtil.toJSON(user));
		String doPost = httpClient.doPost(url, params);
		
	}

}
