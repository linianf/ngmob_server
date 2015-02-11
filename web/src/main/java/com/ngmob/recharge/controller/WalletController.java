package com.ngmob.recharge.controller;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ngmob.pay.entity.Wallet;
import com.ngmob.pay.service.RechargeService;
import com.ngmob.pay.view.AjaxResult;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private Logger logger = LoggerFactory.getLogger(WalletController.class);
	
	@Autowired
	private RechargeService rechargeService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryUserInfo")
	@ResponseBody
	public AjaxResult queryUserInfo(long userid) {
		logger.info("查询用户钱包" + userid);
		Wallet wallet = rechargeService.getWalletByUserId(userid);
		return AjaxResult.success(wallet);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/increase")
	@ResponseBody
	public AjaxResult increase(long userid,String orderNo,long amount,int type) {
		rechargeService.increase(userid, orderNo, amount,type);
		return AjaxResult.success();
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/decrease")
	@ResponseBody
	public AjaxResult decreaseNiubi(long userid,String orderNo,long niubi,int type,boolean canFreeze) {
		try {
			rechargeService.reduce(userid, orderNo, niubi, type);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.failed();
		}
	}
	
	/**
	 * @param userid
	 * @param orderNo
	 * @param type
	 * @param canFreeze
	 * @return
	 */
	@RequestMapping(value = "/createAccount")
	@ResponseBody
	public AjaxResult createAccount(long userid) {
		try {
			Wallet wallet = new Wallet();
			wallet.setUserId(userid);
			rechargeService.saveOrUpdateWallet(wallet);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.failed("ee");
		}
	}
}
