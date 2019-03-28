package com.tzh.register;

import com.tzh.util.DateBaseLink;
import com.tzh.util.Mail;
import com.tzh.util.Sendsms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class Realization {
	
	private static DateBaseLink dateBaselink=new DateBaseLink();
	
	
	public String register(HttpServletRequest req) throws SQLException{
		String account=req.getParameter("account");
		String result=account(account);
		if(result.equals("succes")){
			String userName=req.getParameter("userName");
			String password=req.getParameter("password");
			String email=req.getParameter("email");
			Connection conn=(Connection) dateBaselink.getCon();
			String sql="insert into user(userName,account,password,email,date) values(?,?,?,?,?)";
			PreparedStatement pr=(PreparedStatement) conn.prepareStatement(sql);
			pr.setString(1,userName);
			pr.setString(2,account);
			pr.setString(3,password);
			pr.setString(4,email);
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());  
			pr.setString(5,time);
			pr.execute();
			String smtp = "smtp.126.com";// smtp服务器
			String from = "tzh_java@126.com";// 邮件显示名称
			String to = email;// 收件人的邮件地址，必须是真实地址
			String copyto = "";// 抄送人邮件地址，可以不填
			String subject = "邮件激活";// 邮件标题
			String content = "<a href='http://192.168.1.103:8080/loginPage/register?account="+account+"'>激活账号</a>";// 邮件内容
			String username = "tzh_java@126.com";// 发件人真实的账户名
			String password1 ="tzz123";// 发件人密码
			Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password1);
			return "success";
		}else{
			return "fail";
		}
	}
	
	
	//激活账号
	public String activation(HttpServletRequest req) throws SQLException{
		Long beginUseTime=null;
		Long nowUserTime=null;
		String result="";
		String account=req.getParameter("account");
		String value=obsolete(account);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//当前激活时间 
			beginUseTime = format.parse(value).getTime();//把注册时间转为时间戳
			nowUserTime=format.parse(time).getTime();//把当前激活时间转为时间戳
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Long differTime=(nowUserTime/1000)-(beginUseTime/1000);
		if(differTime > 60){
			result="fail";
		}else{
			Connection conn=(Connection) dateBaselink.getCon();
			String sql="update user set state=1 where account=?";
			PreparedStatement pr=(PreparedStatement) conn.prepareStatement(sql);
			pr.setString(1,account);
			pr.executeUpdate();
			result="success";
		}
		return result;
		
	}
	public String verificationCode(HttpServletRequest req){
		String tel=req.getParameter("tel");
		String result= Sendsms.authenticationCode(tel, req);
		return result;
	}
	
	
	public static String account(String account) throws SQLException{
		String sql="select count(*) from user where account=?";
		Connection conn=(Connection) dateBaselink.getCon();
		PreparedStatement pr=(PreparedStatement) conn.prepareStatement(sql);
		pr.setString(1,account);
		ResultSet rs=pr.executeQuery();
		rs.next();
		Integer value=rs.getInt(1);
		if(value.equals(0)){
			return "succes";
		}else{
			return "fail";
		}
	}
	
	public static String obsolete(String account) throws SQLException{
		String sql="select date from user where account=?";
		Connection conn=(Connection) dateBaselink.getCon();
		PreparedStatement pr=(PreparedStatement) conn.prepareStatement(sql);
		pr.setString(1,account);
		ResultSet rs=pr.executeQuery();
		rs.next();
		String value=rs.getString(1);
		return value;
	}
	
	
	
}
