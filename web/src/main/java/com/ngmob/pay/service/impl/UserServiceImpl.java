package com.ngmob.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngmob.pay.dao.UserDao;
import com.ngmob.pay.entity.MobUser;
import com.ngmob.pay.service.UserService;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public void saveOrUpdateUser(MobUser mobUser) {
		userDao.saveOrUpdateUser(mobUser);
	}

	@Override
	public MobUser getUserById(long userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public MobUser getUserByUserName(String username) {
		return userDao.getUserByUserName(username);
	}

}
