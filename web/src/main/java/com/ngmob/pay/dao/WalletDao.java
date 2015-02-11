package com.ngmob.pay.dao;

import java.util.List;

import com.ngmob.pay.entity.Wallet;
import com.ngmob.pay.entity.WalletWorkflow;

public interface WalletDao {

	void saveOrUpdateWallet(Wallet wallet);
	
	Wallet getWalletByUserId(long userId);
	
	void saveOrUpdate(WalletWorkflow walletWorkflow);
	
	List<WalletWorkflow> getWalletList(long userId,int start,int limit);
}
