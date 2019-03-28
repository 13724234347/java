package com.tzh.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import com.tzh.util.DateBaseLink;
import com.tzh.util.Mail;


public class Realization {
	
	private static DateBaseLink dateBaselink=new DateBaseLink();
	
	public String login(HttpServletRequest req) throws SQLException{
		String account=req.getParameter("account");
		if(state(account).equals("success")){
			String verificationCode=req.getParameter("verificationCode");
			Integer verificationCode1=(Integer) req.getSession().getAttribute("authenticationCode");
			if(verificationCode1==null||verificationCode1.equals("")){
				return "sendOut";
			}else{
				if(verificationCode.equals(String.valueOf(verificationCode1))){
					String password=req.getParameter("password");
					String sql="select count(*) from user where account=? and password=?";
					Connection conn=(Connection) dateBaselink.getCon();
					PreparedStatement pr=(PreparedStatement) conn.prepareStatement(sql);
					pr.setString(1,account);
					pr.setString(2,password);
					ResultSet rs=pr.executeQuery();
					rs.next();
					Integer value=rs.getInt(1);
					if(value.equals("1")){
						return "success";
					}else{
						return "fail";
					}
				}else{
					return "error";
				}
			}
		}else{
			return "1";
		}
		
	}
	public static String state(String account) throws SQLException{
		String result="";
		String sql="select state from user where account=?";
		Connection conn=(Connection) dateBaselink.getCon();
		PreparedStatement pr=(PreparedStatement) conn.prepareStatement(sql);
		pr.setString(1,account);
		ResultSet rs=pr.executeQuery();
		rs.next();
		Object value=rs.getInt(1);
		if(value.equals(1)){
			result="success";
		}else if(value.equals("")||value.equals(0)){
			result="fail";
		}
		return result;
	}
	
	public String judge() throws SQLException{
		String sql="select count(*) from user";
		Connection conn=(Connection) dateBaselink.getCon();
		PreparedStatement pr=(PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs=pr.executeQuery();
		rs.next();
		Integer value=rs.getInt(1);
		if(value.equals("0")){
			return String.valueOf(value);
		}else{
			return String.valueOf(value);
		}
		
	}
	
	
	
	
	public String secondActivation(HttpServletRequest req){
		String result="";
		String account=req.getParameter("account");
		updateDate(account);
		if((prompt(account)).equals("0")){
			String secondEmail=req.getParameter("secondEmail");
			String smtp = "smtp.126.com";// smtp服务器
			String from = "tzh_java@126.com";// 邮件显示名称
			String to = secondEmail;// 收件人的邮件地址，必须是真实地址
			String copyto = "";// 抄送人邮件地址，可以不填
			String subject = "邮件激活";// 邮件标题
			String content = "<a href='http://192.168.1.103:8080/loginPage/register?account="+account+"'>激活账号</a>";// 邮件内容
			String username = "tzh_java@126.com";// 发件人真实的账户名
			String password1 ="tzz123";// 发件人密码
			Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password1);
			result="alreadyActivated";
		}else{
			result="notActivated";
		}
		return result;
	}
	
	public static String prompt(String account){
		String result="";
		String sql="select state from user where account=?";
		Connection conn=(Connection) dateBaselink.getCon();
		try {
			PreparedStatement pr = (PreparedStatement) conn.prepareStatement(sql);
			pr.setString(1,account);
			ResultSet rs=pr.executeQuery();
			rs.next();
			String value=rs.getString(1);
			if(value.equals("1")){
				result="1";
			}else if(value.equals("0")){
				result="0";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static void updateDate(String account){
		String sql="update user set date=? where account =?";
		Connection conn=(Connection) dateBaselink.getCon();
		try {
			PreparedStatement pr = (PreparedStatement) conn.prepareStatement(sql);
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//当前激活时间 
			pr.setString(1,time);
			pr.setString(2,account);
			pr.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
