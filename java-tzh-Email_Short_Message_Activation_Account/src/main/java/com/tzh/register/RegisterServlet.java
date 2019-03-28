package com.tzh.register;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Realization realization=new Realization();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String id=req.getParameter("id");
		String result=null;
		if(id.equals("1")){
			try {
				result=realization.register(req);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(id.equals("2")){
			result=realization.verificationCode(req);
		}
		resp.getWriter().write(result);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String returnValue=null;
		try {
			String result=realization.activation(req);
			if(result.equals("success")){
				returnValue="激活成功";
			}else if(result.equals("fail")){
				returnValue="激活不成功，超出激活时间";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		req.setAttribute("returnValue",returnValue);
		req.getRequestDispatcher("jsp/success.jsp").forward(req,resp);
	}
	
}
