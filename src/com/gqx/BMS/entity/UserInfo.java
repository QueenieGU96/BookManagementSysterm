package com.gqx.BMS.entity;

/**
 * @author GQX 管理员
 */

public class UserInfo {
	private String uName; // 用户名,PRI K
	
	private String pwd;   // 密码

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
