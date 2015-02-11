package com.ngmob.recharge.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ngmob.pay.entity.Recharge;
import com.ngmob.pay.entity.WalletWorkflow;
import com.ngmob.pay.service.RechargeService;
import com.ngmob.pay.view.AjaxResult;
import com.ngmob.recharge.controller.alipay.util.AlipayNotify;
import com.ngmob.recharge.controller.alipay.util.ConstantUtil;

@Controller
@RequestMapping("/wallet")
public class AlipayController extends BaseController{

	@Autowired
	private RechargeService rechargeService;
	
	@RequestMapping(value = "/getAliPayInfo")
	@ResponseBody
	public  AjaxResult  getAliPayInfo(@RequestParam("amount") long amount,String productName,int businessType,String orderNo,
			HttpServletRequest request){
		Map<String,String> data = new HashMap<String,String>();
		try{
			String userId = getCurrentUsername(request);
			Recharge recharge = null;
			recharge = rechargeService.recharge(Long.parseLong(userId),amount ,WalletWorkflow.TYPE_ALIPAY_RECHARGE);
			data.put("out_trade_no", "BA_"+recharge.getId());
			data.put("partner", ConstantUtil.partner);
			data.put("seller_id", ConstantUtil.seller);
			data.put("notify_url", ConstantUtil.notify_url);
			data.put("secure_verify_code", ConstantUtil.secure_verify_code);
			data.put("rsaPrivate", ConstantUtil.rsaPrivate);
			data.put("ali_public_key", ConstantUtil.ali_public_key);
			return AjaxResult.success(data);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *支付宝异步回调请求 支付成功后 增加充值流水
	 * 
	 * @param data
	 * @param encryptkey
	 * @return
	 */
	@RequestMapping("/alipay_notify")
	public void alipay_notify(@RequestParam Map<String,String> params,HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if(AlipayNotify.verify(params)){
				    String trade_status = (String)params.get("trade_status");
				    if(trade_status.equals("WAIT_BUYER_PAY")){
				    	out.println("success");
						out.flush();
						return;
				    }
				    System.out.println("回调支付宝状态：" + trade_status);
				    String out_trade_no = (String)params.get("out_trade_no");
				    String notify_time = (String)params.get("notify_time");
				    String total_fee = (String)params.get("total_fee");
				    String trade_no = (String)params.get("trade_no");
				    boolean isOk = trade_status.equals("TRADE_FINISHED"); 
				    String rechargeId = out_trade_no.substring(3);
				    Recharge recharge = rechargeService.getRechargeById(Long.parseLong(rechargeId));
				    if(isOk){
				    	WalletWorkflow ww = WalletWorkflow.createWalletWorkflow((long)(Double.parseDouble(total_fee)*100), ALI_PAY,recharge.getUserId(), out_trade_no,trade_no);
						rechargeService.saveOrUpdate(ww);
				    }
				    rechargeService.finishRecharge(0,
				        		isOk, Long.parseLong(out_trade_no.substring(3)),
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(notify_time));
			}
			out.println("success");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			out.println("error");
			out.flush();
		}
	}
}
