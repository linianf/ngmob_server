package com.ngmob.pay.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngmob.pay.dao.UserDao;
import com.ngmob.pay.entity.MobUser;

@Repository("userDao")
public class UserDaoImpl implements UserDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdateUser(MobUser mobUser) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(mobUser);
	}

	@Override
	public MobUser getUserById(long userId) {
		String hql = " from MobUser where id=:userId";
		return (MobUser)sessionFactory.getCurrentSession().createQuery(hql)
		              .setLong("userId", userId).uniqueResult();
	}

	@Override
	public MobUser getUserByUserName(String username) {
		String hql = "from MobUser where username=:username";
		return (MobUser)sessionFactory.getCurrentSession().createQuery(hql)
			   .setString("username", username).uniqueResult();
	}

}
