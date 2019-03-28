package com.tzh.login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	Realization realization=new Realization();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result=null;
		try {
			String value=realization.judge();
			if(value.equals("0")){
				result="fail";
			}else{
				result=realization.login(req);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.getWriter().write(result);
	}
	@Override
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		String result=realization.secondActivation(req);
		resp.getWriter().write(result);
	}
}
