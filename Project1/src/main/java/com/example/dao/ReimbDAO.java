package com.example.dao;

import java.util.List;

import com.example.model.Accounts;
import com.example.model.Reimbursement;

public interface ReimbDAO {

	//create reimb
	public int insertIntoReimb(double reimbAmount, String reimbDescription, String reimbReceipt, Accounts a, int reimbType);

	//read
		//single reimb
	public Reimbursement selectReimb(int reimbId);
		//all reimb for single acc
	public List<Reimbursement> selectUserReimbs(int accountId);
		// all pending reimb
	public List<Reimbursement> selectAllReimb();
	
	//update
	public boolean updateReimb(Reimbursement reimbObj, int reimbStatus, Accounts a);
	
	public void init();
	public void destroy();
}
