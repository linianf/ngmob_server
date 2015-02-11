package com.ngmob.pay.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngmob.pay.dao.RechargeDao;
import com.ngmob.pay.entity.Recharge;

@Repository("rechargeDao")
public class RechargeDaoImpl implements RechargeDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdateRecharge(Recharge Recharge) {
		sessionFactory.getCurrentSession().saveOrUpdate(Recharge);
	}

	@Override
	public Recharge getRechargeById(long rechargeId) {
		String hql = " from Recharge where id=:rechargeId";
		return (Recharge)sessionFactory.getCurrentSession().createQuery(hql)
		              .setLong("rechargeId", rechargeId).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Recharge> getRechargeByUserId(long userId) {
		String hql = "from Recharge where userId=:userId";
		return sessionFactory.getCurrentSession().createQuery(hql)
	              .setLong("userId", userId).list();
	}
}
