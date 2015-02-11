package com.ngmob.pay.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 充值订单
 * @author lnf
 *
 */
@Entity
public class Recharge{
	public static final int STATUS_WAIT = 0;// 申请充值，等待支付
	public static final int STATUS_CHECKED = 1;// 支付成功
	public static final int STATUS_ERROR = 2;// 支付失败
	
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long userId;// 充值的人
	private long amount;//金额  单位 分
	private int status;//订单状态
	private Date createTime;//创建时间
	private Date dealTime;//充值订单处理时间
	private int type;//充值平台类型
	private Date paySuccessTime;//支付成功后，第三方支付成功的时间

	public Date getPaySuccessTime() {
    
        return paySuccessTime;
    }

    public void setPaySuccessTime(Date paySuccessTime) {
    
        this.paySuccessTime = paySuccessTime;
    }
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public static Recharge createRecharge(long userId, int type,long amount) {
		Recharge r = new Recharge();
		r.setUserId(userId);
		r.setAmount(amount);
		r.setType(type);
		r.setStatus(STATUS_WAIT);
		r.setDealTime(null);
		return r;
	}
	
	public void check(boolean success) {
		if (success) {
			status = STATUS_CHECKED;
		} else {	
			status = STATUS_ERROR;	
		}
	}

}