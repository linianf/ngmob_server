package com.ngmob.pay.view;

import java.util.Date;

public class PayResult {

    private boolean success;

    private long id;

    /**
     * 支付成功后，第三方支付成功的时间
     */
    private Date paySuccessTime;

    public Date getPaySuccessTime() {

        return paySuccessTime;
    }

    public void setPaySuccessTime(Date paySuccessTime) {

        this.paySuccessTime = paySuccessTime;
    }

    private PayResult(boolean success, long id) {

        this.success = success;
        this.id = id;
    }

    private PayResult(boolean success, long id, Date paySuccessTime) {

        this.success = success;
        this.id = id;
        this.paySuccessTime = paySuccessTime;
    }

    public boolean isSuccess() {

        return success;
    }

    public long getId() {

        return id;
    }

    public static PayResult success(long id) {

        return new PayResult(true, id);
    }

    public static PayResult success(long id, Date paySuccessTime) {

        return new PayResult(true, id, paySuccessTime);
    }
    
    public static PayResult failed(long id) {

        return new PayResult(false, id);
    }

}
