package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.example.model.Accounts;
import com.example.model.Reimbursement;

public class ReimbDAOImpl implements ReimbDAO {
	
	//State
	private String url = "jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/project1";
	private String username = System.getenv("TRAINING_DB_USERNAME");
	private String password = System.getenv("TRAINING_DB_PASSWORD");
	
	private Logger logger = Logger.getLogger(UsersDAOImpl.class);
	
	//Constructors
	public ReimbDAOImpl() {}
	
	public ReimbDAOImpl(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public int insertIntoReimb(double reimbAmount, String reimbDescription, String reimbReceipt, Accounts a, int reimbType) {
		// TODO Auto-generated method stub
		try(Connection conn = DriverManager.getConnection(url,username,password)) {
			String sql = "INSERT INTO ers_reimbursement (reimb_amount, reimb_description, reimb_receipt, reimb_author,reimb_status_id,reimb_type_id) "
					+ "VALUES (?,?,?,?,0,?) RETURNING *";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, reimbAmount);
			ps.setString(2, reimbDescription);
			ps.setString(3, reimbReceipt);
			ps.setInt(4, a.getUserId());
			ps.setInt(5, reimbType);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				logger.info(rs.getString(1)+ "The query was added");
				return rs.getInt(1);
			} else {
				logger.error("Reimbursment Not Created");
			}
			
		} catch (SQLException e) {
			logger.error(e);
		}
		return -1;
	}

	@Override
	public Reimbursement selectReimb(int reimbId) {
		
		try(Connection conn = DriverManager.getConnection(url,username,password)) {
			String sql = "SELECT e.reimb_id , e.reimb_amount, eu2.user_first_name, eu2.user_last_name " + 
					"	, eu.user_first_name , eu.user_last_name, es.reimb_status, et.reimb_type " + 
					"	, e.reimb_submitted , e.reimb_resolved , e.reimb_description, e.reimb_receipt, eu2.ers_username " + 
					"FROM ers_reimbursement e " + 
					"INNER JOIN ers_reimbursement_status es ON (e.reimb_status_id = es.reimb_status_id) " + 
					"INNER JOIN ers_reimbursement_type et ON (e.reimb_type_id = et.reimb_type_id) " + 
					"LEFT JOIN ers_users eu ON (e.reimb_resolver = eu.ers_users_id) " + 
					"INNER JOIN ers_users eu2 ON (e.reimb_author = eu2.ers_users_id) " + 
					"WHERE e.reimb_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, reimbId);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				Reimbursement reimb = new Reimbursement(rs.getString(1), rs.getString(2),rs.getString(3) + " "+ rs.getString(4), rs.getString(5) + " " + rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13));
				logger.info("Got query and created Reimbursement object" + reimb);
				return reimb;
			}
		}catch (SQLException e) {
			logger.error(e);
		}
		return null;
		
	}

	@Override
	public List<Reimbursement> selectUserReimbs(int accountId) {
		// TODO Auto-generated method stub
		List<Reimbursement> allReimbursList = new ArrayList<Reimbursement>();
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "SELECT e.reimb_id , e.reimb_amount, eu2.user_first_name, eu2.user_last_name " + 
					"	, eu.user_first_name , eu.user_last_name, es.reimb_status, et.reimb_type " + 
					"	, e.reimb_submitted , e.reimb_resolved , e.reimb_description, e.reimb_receipt, eu2.ers_username " + 
					"FROM ers_reimbursement e " + 
					"INNER JOIN ers_reimbursement_status es ON (e.reimb_status_id = es.reimb_status_id) " + 
					"INNER JOIN ers_reimbursement_type et ON (e.reimb_type_id = et.reimb_type_id) " + 
					"LEFT JOIN ers_users eu ON (e.reimb_resolver = eu.ers_users_id) " + 
					"INNER JOIN ers_users eu2 ON (e.reimb_author = eu2.ers_users_id) " + 
					"WHERE e.reimb_author = ?"
					+ "ORDER BY e.reimb_submitted DESC;";
			
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, accountId);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Reimbursement reimb = new Reimbursement(rs.getString(1), rs.getString(2),rs.getString(3) +" " + rs.getString(4), rs.getString(5) + " " + rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13));
				allReimbursList.add(reimb);
				logger.info("Got query and added Reimbursement object:\n" + reimb + "\n to the list");
			}
			
		} catch (SQLException e) {
			logger.error(e);
		}
		return allReimbursList;
		
	}

	@Override
	public List<Reimbursement> selectAllReimb() {
		List<Reimbursement> allPendingReimburs = new ArrayList<Reimbursement>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "SELECT e.reimb_id , e.reimb_amount, eu2.user_first_name, eu2.user_last_name " + 
					"	, eu.user_first_name , eu.user_last_name, es.reimb_status, et.reimb_type " + 
					"	, e.reimb_submitted , e.reimb_resolved , e.reimb_description, e.reimb_receipt, eu2.ers_username " + 
					"FROM ers_reimbursement e " + 
					"INNER JOIN ers_reimbursement_status es ON (e.reimb_status_id = es.reimb_status_id) " + 
					"INNER JOIN ers_reimbursement_type et ON (e.reimb_type_id = et.reimb_type_id) " + 
					"LEFT JOIN ers_users eu ON (e.reimb_resolver = eu.ers_users_id) " + 
					"INNER JOIN ers_users eu2 ON (e.reimb_author = eu2.ers_users_id); ";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String authorName = rs.getString(3) + " " + rs.getString(4);
				String reimburserName = rs.getString(5) + " " + rs.getString(6);
				Reimbursement reimb = new Reimbursement(rs.getString(1), rs.getString(2),authorName, reimburserName, rs.getString(7), rs.getString(8), rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13));
				allPendingReimburs.add(reimb);
				logger.info("Got query and added Reimbursement object:\n" + reimb + "\n to the list");
			}
			
		} catch(SQLException e) {
			logger.error(e);
		}
		return allPendingReimburs;
		
	}

	@Override
	public boolean updateReimb(Reimbursement reimbObj, int reimbStatus, Accounts a) {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "UPDATE ers_reimbursement "
					+ "SET reimb_resolved = current_timestamp, reimb_resolver = ?, reimb_status_id = ? "
					+ "FROM ers_reimbursement_status "
					+ "WHERE reimb_id = ? AND ? = ers_reimbursement_status.reimb_status_id "
					+ "RETURNING reimb_resolved, reimb_resolver, ers_reimbursement_status.reimb_status;";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getUserId());
			ps.setInt(2, reimbStatus);
			ps.setInt(3, reimbObj.getReimbursementId());
			ps.setInt(4, reimbStatus);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				reimbObj.setReimbursementResolved(rs.getString(1));
				reimbObj.setReimbursementResolver(a.getFirstName() + " " + a.getLastName());
				reimbObj.setReimbursementStatusId(rs.getString(2));
				logger.info(reimbObj);
				return true;
			}
		} catch(SQLException e) {
			logger.error(e);
		}
		return false;
	}

	@Override
	public void init() {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "CREATE TABLE ers_user_roles (\r\n" + 
					"	ers_user_role_id SERIAL PRIMARY KEY\r\n" + 
					"	, user_role VARCHAR NOT NULL\r\n" + 
					");\r\n" + 
					"\r\n" + 
					"CREATE TABLE ers_reimbursement_status (\r\n" + 
					"	reimb_status_id INTEGER\r\n" + 
					"	, reimb_status VARCHAR(10) NOT NULL\r\n" + 
					"	, CONSTRAINT reimb_status_pk PRIMARY KEY (reimb_status_id)\r\n" + 
					");\r\n" + 
					"\r\n" + 
					"CREATE TABLE ers_reimbursement_type (\r\n" + 
					"	reimb_type_id INTEGER\r\n" + 
					"	, reimb_type VARCHAR(20) NOT NULL\r\n" + 
					"	, CONSTRAINT reimb_type_pk PRIMARY KEY (reimb_type_id)\r\n" + 
					");\r\n" + 
					"\r\n" + 
					"CREATE TABLE ers_users (\r\n" + 
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
					");\r\n" + 
					"\r\n" + 
					"CREATE TABLE ers_reimbursement (\r\n" + 
					"	reimb_id SERIAL PRIMARY KEY\r\n" + 
					"	, reimb_amount NUMERIC(30,2) NOT NULL\r\n" + 
					"	, reimb_submitted TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\r\n" + 
					"	, reimb_resolved TIMESTAMP\r\n" + 
					"	, reimb_description VARCHAR(250)\r\n" + 
					"	, reimb_receipt VARCHAR(250)\r\n" + 
					"	, reimb_author INTEGER\r\n" + 
					"	, CONSTRAINT ers_user_fk_auth FOREIGN KEY (reimb_author)\r\n" + 
					"		REFERENCES ers_users (ers_users_id)\r\n" + 
					"		ON DELETE CASCADE\r\n" + 
					"	, reimb_resolver INTEGER\r\n" + 
					"	, reimb_status_id INTEGER\r\n" + 
					"	, CONSTRAINT ers_reimbursment_status_id FOREIGN KEY (reimb_status_id)\r\n" + 
					"		REFERENCES ers_reimbursement_status (reimb_status_id)\r\n" + 
					"		ON DELETE CASCADE\r\n" + 
					"	, reimb_type_id INTEGER\r\n" + 
					"	, CONSTRAINT ers_reimbursement_type_fl FOREIGN KEY (reimb_type_id)\r\n" + 
					"		REFERENCES ers_reimbursement_type (reimb_type_id)\r\n" + 
					"		ON DELETE CASCADE\r\n" + 
					");\r\n" + 
					"--insert rows\r\n" + 
					"INSERT INTO ers_user_roles VALUES (1, 'f_manager'), (2,'employee');\r\n" + 
					"\r\n" + 
					"INSERT INTO ers_reimbursement_status VALUES(0, 'PENDING'), (1, 'APPROVED'), (2,'DENIED');\r\n" + 
					"\r\n" + 
					"INSERT INTO ers_reimbursement_type VALUES (1, 'LODGING'), (2, 'TRAVEL'), (3, 'FOOD'), (4, 'OTHER');\r\n" + 
					"\r\n" + 
					"INSERT INTO ers_users VALUES (DEFAULT, 'david', 'asdf', 'David', 'Lyu', 'davidlyu55@gmail.com', 2);\r\n" + 
					"INSERT INTO ers_users VALUES (DEFAULT, 'david1', 'asdf', 'David', 'Lyu', 'davidlyu@gmail.com', 1);\r\n" + 
					"\r\n" + 
					"INSERT INTO ers_reimbursement \r\n" + 
					"	VALUES (DEFAULT, 100.235, DEFAULT, NULL, NULL, NULL, 1, 5, 0, 1); ";
			Statement s = conn.createStatement();
			s.execute(sql);
		} catch (SQLException e) {
		System.out.println(e);
		}
	}

	@Override
	public void destroy() {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "DROP TABLE ers_reimbursement;"
					+ "DROP TABLE ers_users;"
					+ "DROP TABLE ers_user_roles;"
					+ "DROP TABLE ers_reimbursement_type;"
					+ "DROP TABLE ers_reimbursement_status;";
			Statement s = conn.createStatement();
			s.execute(sql);
		}catch(SQLException e) {
			System.out.println(e);
		}
		
	}

}
