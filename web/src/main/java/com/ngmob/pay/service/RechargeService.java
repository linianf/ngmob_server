package com.ngmob.pay.service;

import java.util.Date;

import com.ngmob.pay.entity.Recharge;
import com.ngmob.pay.entity.Wallet;
import com.ngmob.pay.entity.WalletWorkflow;

public interface RechargeService {

	/**
	 * 带有充值卡类型的充值记录
	 */
    Recharge recharge(long userId, long amount, int type) throws Exception;

    
    void finishRecharge(long userId, boolean success, long rechargeId, Date paySuccessTime) throws Exception;

    
    Recharge  getRechargeById(long rechargeId);
    
    Wallet getWalletByUserId(long userId);
    
    void saveOrUpdate(WalletWorkflow walletWorkflow);
    
    void increase(long userid,String orderNo,long amount,int type);
    
    void reduce(long userid, String orderNo, long niubi, int type) throws Exception;
    
    void saveOrUpdateWallet(Wallet wallet);
}
