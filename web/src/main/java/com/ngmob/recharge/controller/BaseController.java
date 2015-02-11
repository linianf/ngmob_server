package com.ngmob.recharge.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    
	//业务类型
	public static final int  WX_PAY = 1;
	public static final int  ALI_PAY = 2;
	
	public static final int  BUY = 3;
	
	public String getCurrentUsername(HttpServletRequest request){
		String userId = request.getParameter("userid");
	    return userId;
	}
	
	public int  getBusiType(String out_trade_no){
		String prefix = out_trade_no.substring(0,3);
		if("WX_".equals(prefix))
			return WX_PAY;
		else if("BA_".equals(prefix))
			return ALI_PAY;
		else
			return BUY;
	}
}
