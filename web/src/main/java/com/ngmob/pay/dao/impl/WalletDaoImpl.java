package com.ngmob.pay.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngmob.pay.dao.WalletDao;
import com.ngmob.pay.entity.Wallet;
import com.ngmob.pay.entity.WalletWorkflow;

@Repository("walletDao")
public class WalletDaoImpl implements WalletDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdateWallet(Wallet wallet) {
		sessionFactory.getCurrentSession().saveOrUpdate(wallet);
	}

	@Override
	public Wallet getWalletByUserId(long userId) {
		String hql = "from Wallet where userId=:userId";
		return (Wallet)sessionFactory.getCurrentSession().createQuery(hql)
			   .setLong("userId", userId).uniqueResult();
	}

	@Override
	public void saveOrUpdate(WalletWorkflow walletWorkflow) {
		sessionFactory.getCurrentSession().saveOrUpdate(walletWorkflow);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WalletWorkflow> getWalletList(long userId, int start, int limit) {
		String hql = " from WalletWorkflow where userId=:userId";
		return sessionFactory.getCurrentSession().createQuery(hql).setLong("userId", userId)
		              .setFirstResult(start).setMaxResults(limit).list(); 
	}


}
