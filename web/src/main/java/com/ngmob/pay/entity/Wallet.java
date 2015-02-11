package com.ngmob.pay.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Wallet {
	
	public static final BigDecimal EXCHANGE = BigDecimal.valueOf(100D);
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private long userId;
	
	private long balance;//余额
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public static Wallet createWallet(long userId) {
		Wallet w = new Wallet();
		w.setUserId(userId);
		w.setBalance(0L);
		return w;
	}

	
	public static final double toRenMinBi(long amount) {
		return BigDecimal.valueOf(amount).divide(EXCHANGE, 2, BigDecimal.ROUND_DOWN).doubleValue();
	}

    /**
     * 用户扣款
     * @param amount
     * @throws Exception
     */
    public void decrease(long amount) throws Exception {
       
        if (balance < amount) {
            throw new Exception("扣款金额超过可用余额，请重新输入！");
        }
        balance = balance - amount;
    }
	
    /**
     * 充值进来，加入到冻结账户中
     * @param amount
     */
    public void increase(long amount){
    	balance = balance + amount;
    }
    
}
