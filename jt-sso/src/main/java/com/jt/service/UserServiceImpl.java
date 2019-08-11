package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * true 表示用户已存在, false表示用户可以使用
	 * 1.param 用户参数
	 * 2.type 1 username,2 phone,3 email
	 * 
	 * 将type转化为具体的字段
	 */
	@Override
	public boolean checkUser(String param, int type) { 
		String column =
		type==1 ? "username" : (type==2?"phone":"email");
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(column, param);
		int count = userMapper.selectCount(queryWrapper);
		
		return count==0?false:true;
	}

	@Override
	@Transactional
	public void saveUser(String user) {
		User entity = ObjectMapperUtil.toObject(user, User.class);
		entity.setEmail(entity.getPhone()).setCreated(new Date()).setUpdated(entity.getCreated());
		userMapper.insert(entity);
	}
	
	
	
}
