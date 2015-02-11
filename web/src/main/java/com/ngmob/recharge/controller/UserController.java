package com.ngmob.recharge.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ngmob.pay.entity.MobUser;
import com.ngmob.pay.entity.Wallet;
import com.ngmob.pay.service.RechargeService;
import com.ngmob.pay.service.UserService;
import com.ngmob.pay.view.AjaxResult;
import com.ngmob.recharge.wx.util.MD5Util;
import com.ngmob.util.PassUtil;
import com.ngmob.util.RedisUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	  private Logger logger = LoggerFactory.getLogger(WalletController.class);
		
	  @Autowired
	  private UserService userService;
	  
	  @Autowired
	  private RechargeService rechargeService;
	  
	  @Autowired
	  private RedisUtil redisUtil;
	  
	  private final String H_VERIFY_SET = "hash:queue";
	  
	  /**
		* 用户登录
		* @return
	  */
	  @RequestMapping(value = "/login")
	  @ResponseBody
	  public AjaxResult login(String username,String password){
		  logger.info("用户登录" + username);
		  if(StringUtils.isEmpty(username)){
			  return AjaxResult.failed("用户名不可为空");
		  }
		  if(StringUtils.isEmpty(password)){
			  return AjaxResult.failed("密码不可为空");
		  }
		  String passMd5 = MD5Util.MD5Encode(password, "UTF8");
		  MobUser user = userService.getUserByUserName(username);
		  if(user.getPassword().equals(passMd5)){
			  return AjaxResult.success();
		  }
		  return AjaxResult.failed("密码错误");
	  }
	  
	  @RequestMapping(value = "/generateSms")
	  @ResponseBody
	  public  AjaxResult  generateSms(String mobile){
		  String veriCode = PassUtil.generate();
		  redisUtil.hset(H_VERIFY_SET, mobile, veriCode);
		  System.out.println(veriCode);
		  //TODO
		  return AjaxResult.success();
	  }
	  
	  @RequestMapping(value = "/verify")
	  @ResponseBody
	  public  AjaxResult  verify(String mobile,String verifyCode){
		  String realCode = redisUtil.hget(H_VERIFY_SET, mobile);
		  if(realCode.equals(verifyCode)){
			  return AjaxResult.success();
		  }
		  return AjaxResult.failed("验证码错误");
	  }
	  
	  @RequestMapping(value = "/regist")
	  @ResponseBody
	  public  AjaxResult  regist(String mobile,String password){
		  MobUser user = new MobUser();
		  user.setMobile(mobile);
		  user.setPassword(password);
		  user.setUsername(mobile);
		  userService.saveOrUpdateUser(user);
		  //初始化钱包
		  Wallet wallet = new Wallet();
		  wallet.setUserId(user.getId());
		  wallet.setBalance(0);
		  rechargeService.saveOrUpdateWallet(wallet);
		  return AjaxResult.success();
	  }
}
