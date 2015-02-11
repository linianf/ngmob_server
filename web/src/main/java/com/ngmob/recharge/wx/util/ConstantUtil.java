package com.ngmob.recharge.wx.util;

public class ConstantUtil {
	/**
	 * �̼ҿ��Կ��Ƕ�ȡ�����ļ�
	 */
	
	//��ʼ��
	public static String APP_ID = "wxc408ea5cea5e8276";//΢�ſ���ƽ̨Ӧ��id
	public static String APP_SECRET = "1193fd6b585cfbf373934672d4bd6af5";//Ӧ�ö�Ӧ��ƾ֤
	//Ӧ�ö�Ӧ����Կ
	public static String APP_KEY = "4zdIPCduLFxKIXaEHSGhIpfZ9pmTduz2P6955Mx65sUa0hjlv0FVJxdYzRBicn5V7DYZV4oywaymdp8M4JwMieztOvuG98rk8QnptvgNDqNnG9ECYhGZOQzIzF2j4J27";
	                                 
	public static String PARTNER = "1219483101";//�Ƹ�ͨ�̻���
	public static String PARTNER_KEY = "d1e3c2e799556a556c1367b7b71fd650";//�̻��Ŷ�Ӧ����Կ
	public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";//��ȡaccess_token��Ӧ��url
	public static String GRANT_TYPE = "client_credential";//�����̶�ֵ 
	public static String EXPIRE_ERRCODE = "42001";//access_tokenʧЧ�����󷵻ص�errcode
	public static String FAIL_ERRCODE = "40001";//�ظ���ȡ������һ�λ�ȡ��access_tokenʧЧ,���ش�����
	public static String GATEURL = "https://api.weixin.qq.com/pay/genprepay?access_token=";//��ȡԤ֧��id�Ľӿ�url
	public static String ACCESS_TOKEN = "access_token";//access_token����ֵ
	public static String ERRORCODE = "errcode";//�����ж�access_token�Ƿ�ʧЧ��ֵ
	public static String SIGN_METHOD = "sha1";//ǩ���㷨����ֵ
	//package����ֵ
	public static String packageValue = "bank_type=WX&body=%B2%E2%CA%D4&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F127.0.0.1%3A8180%2Ftenpay_api_b2c%2FpayNotifyUrl.jsp&out_trade_no=2051571832&partner=1900000109&sign=10DA99BCB3F63EF23E4981B331B0A3EF&spbill_create_ip=127.0.0.1&time_expire=20131222091010&total_fee=1";
    
	public static String notify_url = "http://pay.nbker.com/wallet/wx_notify.html";
}
