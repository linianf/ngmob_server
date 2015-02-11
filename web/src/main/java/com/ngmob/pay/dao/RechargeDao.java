package com.ngmob.pay.dao;

import java.util.List;

import com.ngmob.pay.entity.Recharge;

public interface RechargeDao {
   
	void saveOrUpdateRecharge(Recharge recharge);
	
	Recharge  getRechargeById(long rechargeId);
	
	List<Recharge> getRechargeByUserId(long userId);
}
