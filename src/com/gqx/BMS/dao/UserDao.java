package com.gqx.BMS.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gqx.BMS.util.Databaseconnection;

public class UserDao {

	/*参数值用户名、密码，sql--查询表user_info*/
	public boolean userLoginSuccess(String username, String pwd) {
		Connection conn = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			conn = Databaseconnection.getConnection();
			String sql = "select * from user_info where uname=? and pwd=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			// 获取查询结果
			if (rs.next()) {
				flag = true;
			}
			// 实现自动注册
			if (!flag) {
				flag = userRegisterSuccess(username, pwd);
			}
		} catch (Exception e) {
		} finally {
			// 关闭数据库连接
			try {
				if (rs != null)
					rs.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {

			}
		}
		return flag;
	}

	/*参数值用户名、密码，sql--插入数据表user_info*/
	public static boolean userRegisterSuccess(String username, String pwd) {
		Connection conn = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			conn = Databaseconnection.getConnection();
			String sql = "insert into user_info(uname,pwd) values(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, pwd);
			int i = ps.executeUpdate();
			// 确定已增加新用户名和密码
			if (i > 0) {
				flag = true;
			}
		} catch (Exception e) {
		} finally {
			// 关闭数据库连接
			try {
				if (rs != null)
					rs.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {

			}
		}
		return flag;
	}
}
