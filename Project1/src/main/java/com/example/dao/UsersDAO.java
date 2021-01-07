package com.example.dao;

import com.example.model.Accounts;

public interface UsersDAO {

	public Accounts selectUser(String username, String password);
	public void init();
	public void destroy();
}
