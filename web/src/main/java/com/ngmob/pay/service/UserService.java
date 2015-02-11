package com.ngmob.pay.service;

import com.ngmob.pay.entity.MobUser;

public interface UserService {

    void saveOrUpdateUser(MobUser mobUser);
	
	MobUser  getUserById(long userId);
	
	MobUser  getUserByUserName(String username);
}
