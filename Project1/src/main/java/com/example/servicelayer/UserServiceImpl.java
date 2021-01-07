package com.example.servicelayer;

import org.apache.log4j.Logger;

import com.example.dao.UsersDAOImpl;
import com.example.model.Accounts;

public class UserServiceImpl implements UserService {
	private Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Override
	public Accounts getUser(String username, String password) {
		// TODO Auto-generated method stub
		UsersDAOImpl udi = new UsersDAOImpl();
//		username = username.toLowerCase();
		Accounts loginAcc = udi.selectUser(username, password);
		if(loginAcc == null) {
			logger.info("Wrong username/pass");
			return null;
		}
		return loginAcc;
		
	}

}
