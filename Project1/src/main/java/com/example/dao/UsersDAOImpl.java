package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.example.model.Accounts;

public class UsersDAOImpl implements UsersDAO {
	private String url = "jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/project1";
	private String username = System.getenv("TRAINING_DB_USERNAME");
	private String password = System.getenv("TRAINING_DB_PASSWORD");
	
	private static Logger logger = Logger.getLogger(UsersDAOImpl.class);

	public UsersDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public UsersDAOImpl(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public Accounts selectUser(String username, String password) {
		// TODO Auto-generated method stub
		try(Connection conn = DriverManager.getConnection(url,this.username,this.password)) {
			String sql = "SELECT eu.ers_users_id, eu.ers_username, eu.ers_password, eu.user_first_name"
					+ ", eu.user_last_name, eu.user_email, eur.user_role "
					+ "FROM ers_users eu INNER JOIN ers_user_roles eur ON (eur.ers_user_role_id = eu.user_role_id) " 
					+ "where ers_username = ? AND ers_password = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Accounts log = new Accounts(rs.getInt(1), username, password, rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7));
				logger.debug("Account created " + log);
				return log;
			} else {
				logger.debug("Wrong username/password");
				return null;
			}
		} catch(SQLException e) {
			logger.error(e);
		}
		return null;
	}

	@Override
	public void init() {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "CREATE TABLE ers_user_roles (\r\n" + 
					"	ers_user_role_id SERIAL PRIMARY KEY\r\n" + 
					"	, user_role VARCHAR NOT NULL\r\n" + 
					");"
					+ " CREATE TABLE ers_users (\r\n" + 
					"	ers_users_id SERIAL\r\n" + 
					"	, ers_username VARCHAR(20) NOT NULL UNIQUE\r\n" + 
					"	, ers_password VARCHAR(50) NOT NULL\r\n" + 
					"	, user_first_name VARCHAR(100) NOT NULL\r\n" + 
					"	, user_last_name VARCHAR(100) NOT NULL\r\n" + 
					"	, user_email VARCHAR(150) NOT NULL UNIQUE\r\n" + 
					"	, user_role_id INTEGER\r\n" + 
					"	, CONSTRAINT ers_users_pk PRIMARY KEY (ers_users_id)\r\n" + 
					"	, CONSTRAINT user_role_fk FOREIGN KEY (user_role_id)\r\n" + 
					"		REFERENCES ers_user_roles (ers_user_role_id)\r\n" + 
					"		ON DELETE CASCADE \r\n" + 
					");"
					+ "INSERT INTO ers_user_roles VALUES (1, 'f_manager'), (2,'employee');"
					+ "INSERT INTO ers_users VALUES (DEFAULT, 'david', 'asdf', 'David', 'Lyu', 'davidlyu55@gmail.com', 2);"
					+ "INSERT INTO ers_users VALUES (DEFAULT, 'david1', 'asdf', 'David', 'Lyu', 'davidlyu@gmail.com', 1);";
			Statement s = conn.createStatement();
			s.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void destroy() {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "DROP TABLE ers_users;"
					+ "DROP TABLE ers_user_roles;";
			Statement s = conn.createStatement();
			s.execute(sql);
		}catch(SQLException e) {
			System.out.println(e);
		}
		
	}
}
