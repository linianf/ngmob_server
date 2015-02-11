package com.ngmob.pay.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WalletWorkflow {

	public static final int TYPE_ALIPAY_RECHARGE = 1;// 支付宝充值
	
	public static final int TYPE_WEIXIN_RECHARGE = 2;// 微信支付充值
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
 	private long id;
	
	private long userid;

	private long amount;//交易金额

	private int type;//业务类型

	private Date createTime;
	
	private String orderNo;//订单编号
	
	private String thirdNo;//第三方的交易号 

	public String getThirdNo() {
		return thirdNo;
	}

	public void setThirdNo(String thirdNo) {
		this.thirdNo = thirdNo;
	}
	
	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public static WalletWorkflow createWalletWorkflow(long amount, int type, long userid,String orderNo,String thirdNo) {
		WalletWorkflow ww = new WalletWorkflow();
		ww.setAmount(amount);
		ww.setUserid(userid);
		ww.setType(type);
		ww.setCreateTime(new Date());
		ww.setOrderNo(orderNo);
		ww.setThirdNo(thirdNo);
		return ww;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}