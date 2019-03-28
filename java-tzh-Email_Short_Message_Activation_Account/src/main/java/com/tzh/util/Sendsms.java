package com.tzh.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Sendsms {
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	
	public static String authenticationCode(String tel,HttpServletRequest req){
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url);
	
		client.getParams().setContentCharset("GBK");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");
	
		int mobile_code = (int)((Math.random()*9+1)*100000);
		HttpSession session = req.getSession();
		session.setAttribute("authenticationCode", mobile_code);
	    String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
	
		NameValuePair[] data = {//提交短信
			    new NameValuePair("account", "C33522101"), 
			    new NameValuePair("password", "155d9fa4b5bd05bf40bb474e0fd32bb9"), //查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
			    //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
			    new NameValuePair("mobile", tel), 
			    new NameValuePair("content", content),
		};
		method.setRequestBody(data);
		try {
			client.executeMethod(method);
			
			String SubmitResult =method.getResponseBodyAsString();
	
			//System.out.println(SubmitResult);
	
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
	
			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");
			
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
	
			 if("2".equals(code)){
				System.out.println("短信提交成功");
			}
	
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}	
		return "0";
	}
}
