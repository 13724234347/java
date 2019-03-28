package com.tzh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DateBaseLink {
	public Connection getCon() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://192.168.1.86:3306/ssh?useUnicode=true&characterEncoding=UTF-8&user=root&password=root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void closeAll(Connection conn, PreparedStatement ps, Statement st, ResultSet rs) {
		try {
			if (null != conn) {
				conn.close();
			}
			if (null != ps) {
				ps.close();
			}
			if (null != st) {
				st.close();
			}
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
