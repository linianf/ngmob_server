package com.ngmob.recharge.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import com.ngmob.recharge.wx.AccessTokenVisitor;
import com.ngmob.recharge.wx.PrepayIdRequestHandler;
import com.ngmob.recharge.wx.util.ConstantUtil;
import com.ngmob.recharge.wx.util.MD5Util;
import com.ngmob.recharge.wx.util.WXUtil;

@Controller
@RequestMapping("/wallet")
public class WxpayController extends BaseController{
	
	@Autowired
	private PrepayIdRequestHandler prepayIdRequestHandler;
	
	@Autowired
	private RechargeService rechargeService;
	
	@RequestMapping(value = "/getWxPayId")
	@ResponseBody
	public  AjaxResult  getWxPayId(@RequestParam("amount") long amount,String productName,int businessType,String orderNo,
			HttpServletRequest request){
		Map<String,String> data = new HashMap<String,String>();
		try{
			String userId = getCurrentUsername(request);
			Recharge recharge = null;
		
			recharge = rechargeService.recharge(Long.parseLong(userId),amount ,WalletWorkflow.TYPE_WEIXIN_RECHARGE);
			//获取操作的access_token]
			String access_token = AccessTokenVisitor.getAccessToken(userId);
			PrepayIdRequestHandler wxReqHandler = new PrepayIdRequestHandler();
			wxReqHandler.setParameter("appid", ConstantUtil.APP_ID);
			wxReqHandler.setParameter("appkey",ConstantUtil.APP_KEY);
			wxReqHandler.setParameter("noncestr", WXUtil.getNonceStr());
			
			wxReqHandler.setParameter("package", genPackage(productName,"WX_"+recharge.getId(),amount));
			
			wxReqHandler.setParameter("timestamp", WXUtil.getTimeStamp());
			wxReqHandler.setParameter("traceid", userId);
			//生成支付签名
			String sign = wxReqHandler.createSHA1Sign();
			wxReqHandler.setParameter("app_signature", sign);
			wxReqHandler.setParameter("sign_method", ConstantUtil.SIGN_METHOD);
			String gateUrl = ConstantUtil.GATEURL + access_token;
			wxReqHandler.setGateUrl(gateUrl);
			String prepayId = wxReqHandler.sendPrepay();
			data.put("appid", ConstantUtil.APP_ID);
			data.put("appkey", ConstantUtil.APP_KEY);
			data.put("partnerId", ConstantUtil.PARTNER);
			data.put("prepayId", prepayId);
			return AjaxResult.success(data);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *微信异步回调请求 支付成功后 增加充值流水
	 * 
	 * @param data
	 * @param encryptkey
	 * @return
	 */
	/**
	 * @param params
	 * @param response
	 */
	@RequestMapping("/wx_notify")
	public void wx_notify(@RequestParam Map<String,Object> params,HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if(verifySignFromWx(params)){
				    String trade_state = (String)params.get("trade_state");
				    String out_trade_no = (String)params.get("out_trade_no");
				    String time_end = (String)params.get("time_end");
				    String total_fee = (String)params.get("total_fee");
				    String transaction_id = (String)params.get("transaction_id");
				    boolean isOk = trade_state.equals("0");
				    String rechargeId = out_trade_no.substring(3);
				    Recharge recharge = rechargeService.getRechargeById(Long.parseLong(rechargeId));
				    if(isOk){
				    	WalletWorkflow ww = WalletWorkflow.createWalletWorkflow(Long.parseLong(total_fee), WX_PAY,recharge.getUserId(), out_trade_no,transaction_id);
						rechargeService.saveOrUpdate(ww);
				    }
				    
				    rechargeService.finishRecharge(0,
				        		isOk, Long.parseLong(out_trade_no.substring(3)),
					new Date(Long.parseLong(time_end)));
				   
			}
			out.println("success");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			out.println("error");
			out.flush();
		}
	}

	private  String  genPackage(String productName,String orderNo,long total_fee){
		Map<String,String> parms = new TreeMap<String,String>();
		parms.put("bank_type", "WX");
		parms.put("body", productName);
		parms.put("fee_type", "1");
		parms.put("input_charset", "GBK");
		parms.put("notify_url", ConstantUtil.notify_url);
		parms.put("out_trade_no", orderNo);
		parms.put("partner", ConstantUtil.PARTNER);
		parms.put("spbill_create_ip", "112.124.41.77");
		parms.put("total_fee", String.valueOf(total_fee));
		String string1 = mapToStr(parms);
		string1 = string1 + "&key="+ConstantUtil.PARTNER_KEY;
		String sign = MD5Util.MD5Encode(string1, "GBK").toUpperCase();
		String oriStr = "";
		try {
			oriStr = mapToEncodeStr(parms);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return oriStr + "&sign="+sign;
	}
	
	public static void main(String[] args){
		WxpayController dd = new WxpayController();
		System.out.println(dd.getWxPayId(500000,"牛邦客-牛逼充值",1,"0",null ));
	}
	
	private String mapToStr(Map<String,String> map){
		StringBuffer sb = new StringBuffer();
		Set<String> set = map.keySet();
		for(String key : set){
			sb.append(key+"=" + map.get(key)+"&");
		}
		String temp = sb.toString();
		return temp.substring(0,temp.length()-1);
	}
	
	private String mapToEncodeStr(Map<String,String> map) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		Set<String> set = map.keySet();
		for(String key : set){
			if(map.get(key)!=null&&!key.equals("sign"))
			  sb.append(key+"=" + URLEncoder.encode(map.get(key),"GBK")+"&");
		}
		String temp = sb.toString();
		return temp.substring(0,temp.length()-1);
	}
	
    private boolean  verifySignFromWx(Map<String,Object> params){
    	StringBuffer sb = new StringBuffer();
    	List<String> list = new ArrayList<String>(params.keySet());
    	Collections.sort(list);
    	for(String key : list){
    		if(params.get(key)!=null&&!key.equals("sign"))
    			sb.append(key+"=" + params.get(key)+"&");
    	}
    	sb.append("key="+ConstantUtil.PARTNER_KEY);
    	String sign = MD5Util.MD5Encode(sb.toString(), "GBK").toUpperCase();
    	String wxSign = (String)params.get("sign");
		return wxSign.equals(sign);
	}
}
