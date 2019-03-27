package com.gqx.BMS.util;

/**
 * @author GQX JDBC连接数据库gqx_bms
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Databaseconnection {

	// 启动jar包路径
	private static String driver = "com.mysql.jdbc.Driver";

	// 数据库路径
	private static String url = "jdbc:mysql://localhost:3306/gqx_bms";

	// 用户名
	private static String uname = "root";

	// 密码
	private static String upass = "123456";

	static {
		try {
			Class.forName(driver);                                     // 加载driver
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		Connection conn = Databaseconnection.getConnection();          // 连接数据库
		System.out.println(conn);
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try { 
			conn = DriverManager.getConnection(url, uname, upass);    
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

}
