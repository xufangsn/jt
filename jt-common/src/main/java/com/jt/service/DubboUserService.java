package com.jt.service;

import org.springframework.transaction.annotation.Transactional;

import com.jt.pojo.User;

public interface DubboUserService {

	@Transactional
	void doRegister(User user);

	String findUserByUP(User user);

}
