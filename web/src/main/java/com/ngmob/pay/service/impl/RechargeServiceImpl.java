package com.ngmob.pay.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngmob.pay.dao.RechargeDao;
import com.ngmob.pay.dao.WalletDao;
import com.ngmob.pay.entity.Recharge;
import com.ngmob.pay.entity.Wallet;
import com.ngmob.pay.entity.WalletWorkflow;
import com.ngmob.pay.service.RechargeService;

@Service("rechargeService")
@Transactional
public class RechargeServiceImpl implements RechargeService{

	@Autowired
	private RechargeDao rechargeDao;
	
	@Autowired
	private WalletDao walletDao;
	
	@Override
	public Recharge recharge(long userId,
			long amount, int type) throws Exception {
		
		Recharge recharge = Recharge.createRecharge(userId,type, amount);
		rechargeDao.saveOrUpdateRecharge(recharge);
		return recharge;
	}
	
	@Override
	public void finishRecharge(long userId, boolean success, long rechargeId, Date paySuccessTime) throws Exception {
		Recharge recharge = rechargeDao.getRechargeById(rechargeId);
		if (recharge==null||recharge.getStatus() == Recharge.STATUS_CHECKED) {
			return;//
		}
		if (userId!=0) {
			
			if (userId != recharge.getUserId()) {
				throw new Exception("订单号非法");
			}
		}
		recharge.check(success);
		
		if (!success) {
			rechargeDao.saveOrUpdateRecharge(recharge);
			return;
		}
		long amount = recharge.getAmount();
		Wallet wallet = walletDao.getWalletByUserId(recharge.getUserId());
		wallet.increase(amount);
		
		walletDao.saveOrUpdateWallet(wallet);

		recharge.setPaySuccessTime(paySuccessTime);
		rechargeDao.saveOrUpdateRecharge(recharge);
	}

	@Override
	public Recharge getRechargeById(long rechargeId) {
		return rechargeDao.getRechargeById(rechargeId);
	}

    public Wallet getWalletByUserId(long userId){
    	return walletDao.getWalletByUserId(userId);
    }

	@Override
	public void saveOrUpdate(WalletWorkflow walletWorkflow) {
		walletDao.saveOrUpdate(walletWorkflow);
	}

	@Override
	public void increase(long userid, String orderNo,long amount, int type) {
		Wallet wallet = walletDao.getWalletByUserId(userid);
		wallet.increase(amount);
		WalletWorkflow wf = WalletWorkflow.createWalletWorkflow(amount,type,userid,orderNo,null);
		walletDao.saveOrUpdate(wf);
	}

	@Override
	public void reduce(long userid, String orderNo, long amount, int type) throws Exception {
		Wallet wallet = walletDao.getWalletByUserId(userid);
		wallet.decrease(amount);
		WalletWorkflow wf = WalletWorkflow.createWalletWorkflow(amount, type, userid,orderNo,null);
		walletDao.saveOrUpdate(wf);
	}

	@Override
	public void saveOrUpdateWallet(Wallet wallet) {
		walletDao.saveOrUpdateWallet(wallet);
	}
}
