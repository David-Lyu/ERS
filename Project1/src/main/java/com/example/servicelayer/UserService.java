package com.example.servicelayer;

import com.example.model.Accounts;

public interface UserService {
	public Accounts getUser(String username, String password);
}
