package com.ngmob.pay.view;

public class RechargeType {

    private  int rType; //1:易宝支付储蓄卡 ,2:易宝支付信用卡
    private String rUrl ;//图片
    private int rechargeMaxLimit = 1000; //最高充值金额
    private int rechargeMinLimit = 1;  //最低充值金额

    public int getrType() {
        return rType;
    }

    public void setrType(int rType) {
        this.rType = rType;
    }

    public String getrUrl() {
        return rUrl;
    }

    public void setrUrl(String rUrl) {
        this.rUrl = rUrl;
    }

    public int getRechargeMaxLimit() {
        return rechargeMaxLimit;
    }

    public void setRechargeMaxLimit(int rechargeMaxLimit) {
        this.rechargeMaxLimit = rechargeMaxLimit;
    }

    public int getRechargeMinLimit() {
        return rechargeMinLimit;
    }

    public void setRechargeMinLimit(int rechargeMinLimit) {
        this.rechargeMinLimit = rechargeMinLimit;
    }
}

