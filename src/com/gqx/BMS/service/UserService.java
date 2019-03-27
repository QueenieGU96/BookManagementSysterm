package com.gqx.BMS.service;

import com.gqx.BMS.dao.UserDao;

public class UserService {

	private final UserDao userDao = new UserDao();

	/*调用类UserDao*/
	
	public boolean userLoginSuccess(String username, String pwd) {
		boolean isSuccess = userDao.userLoginSuccess(username, pwd);
		return isSuccess;
	}

}
