package com.example.servicelayer;

import java.util.List;

import com.example.model.Accounts;
import com.example.model.Reimbursement;

public interface ReimbService {

	
	//create 
		//add reimbusement
	public Reimbursement addReimbursement(Accounts a,double reimbAmount, String reimbDescription, String reimbReceipt, int reimbType);
	
	//read
		//viewSingle
	public Reimbursement viewSingleReimb(int reimbId);
		//getAllpending
	public List<Reimbursement> viewAllReimb();
		//getAllPerUsers
	public List<Reimbursement> viewUserReimbs(Accounts a);
	
		//getAll
			//if time
	
	//change reimbursement status
		//approve/deny
	public Reimbursement changeReimbStatus(Reimbursement reimbObj, int reimbStatus, Accounts a);
	
}
