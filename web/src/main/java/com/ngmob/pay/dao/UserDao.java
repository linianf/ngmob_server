package com.ngmob.pay.dao;

import com.ngmob.pay.entity.MobUser;

public interface UserDao {
    
	void saveOrUpdateUser(MobUser mobUser);
	
	MobUser  getUserById(long userId);
	
	MobUser  getUserByUserName(String username);
	
}
