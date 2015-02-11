package com.ngmob.recharge.wx;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.ngmob.recharge.wx.util.MD5Util;
import com.ngmob.recharge.wx.util.TenpayUtil;

/**
 * ��������
 * ��������̳д��࣬��дcreateSign�������ɡ�
 * @author miklchen
 *
 */
public class RequestHandler {
	
	/** ����url��ַ */
	private String gateUrl;
	
	/** ��Կ */
	private String key;
	
	/** ����Ĳ��� */
	private SortedMap<String,String>  parameters;
	
	/** debug��Ϣ */
	private String debugInfo;
	
	/**
	 * ���캯��
	 * @param request
	 * @param response
	 */
	public RequestHandler() {
		this.gateUrl = "https://gw.tenpay.com/gateway/pay.htm";
		this.key = "";
		this.parameters = new TreeMap<String,String>();
		this.debugInfo = "";
	}
	
	/**
	*��ʼ��������
	*/
	public void init() {
	}

	/**
	*��ȡ��ڵ�ַ,����������ֵ
	*/
	public String getGateUrl() {
		return gateUrl;
	}

	/**
	*������ڵ�ַ,����������ֵ
	*/
	public void setGateUrl(String gateUrl) {
		this.gateUrl = gateUrl;
	}

	/**
	*��ȡ��Կ
	*/
	public String getKey() {
		return key;
	}

	/**
	*������Կ
	*/
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * ��ȡ����ֵ
	 * @param parameter ��������
	 * @return String 
	 */
	public String getParameter(String parameter) {
		String s = (String)this.parameters.get(parameter); 
		return (null == s) ? "" : s;
	}
	
	/**
	 * ���ò���ֵ
	 * @param parameter ��������
	 * @param parameterValue ����ֵ
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if(null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}
	
	/**
	 * �������еĲ���
	 * @return SortedMap
	 */
	public SortedMap<String,String> getAllParameters() {		
		return this.parameters;
	}

	/**
	*��ȡdebug��Ϣ
	*/
	public String getDebugInfo() {
		return debugInfo;
	}
	
	/**
	 * ��ȡ������������URL
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public String getRequestURL() throws UnsupportedEncodingException {
		
		this.createSign();
		
		StringBuffer sb = new StringBuffer();
		String enc = TenpayUtil.getCharacterEncoding();
		Set<Map.Entry<String,String>>  es = this.parameters.entrySet();
		Iterator<Map.Entry<String,String>>  it = es.iterator();
		while(it.hasNext()) {
			Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			
			if(!"spbill_create_ip".equals(k)) {
				sb.append(k + "=" + URLEncoder.encode(v, enc) + "&");
			} else {
				sb.append(k + "=" + v.replace("\\.", "%2E") + "&");
			}
		}
		
		//ȥ�����һ��&
		String reqPars = sb.substring(0, sb.lastIndexOf("&"));
		
		return this.getGateUrl() + "?" + reqPars;
		
	}
	
	/**
	 * ����md5ժҪ,������:����������a-z����,������ֵ�Ĳ������μ�ǩ����
	 */
	protected void createSign() {
		StringBuffer sb = new StringBuffer();
		Set<Map.Entry<String,String>>  es = this.parameters.entrySet();
		Iterator<Map.Entry<String,String>>  it = es.iterator();
		while(it.hasNext()) {
			Map.Entry<String,String>  entry = (Map.Entry<String,String> )it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		
		String enc = TenpayUtil.getCharacterEncoding();
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toUpperCase();
		
		this.setParameter("sign", sign);
		
		//debug��Ϣ
		this.setDebugInfo(sb.toString() + " => sign:" + sign);
		
	}
	
	/**
	*����debug��Ϣ
	*/
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
	 
}
