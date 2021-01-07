package com.example.servicelayer;

import java.util.List;

import org.apache.log4j.Logger;

import com.example.dao.ReimbDAOImpl;
import com.example.model.Accounts;
import com.example.model.Reimbursement;

public class ReimbServiceImpl implements ReimbService {
	
	private Logger logger = Logger.getLogger(ReimbServiceImpl.class);
	private ReimbDAOImpl reimbDaoImpl = new ReimbDAOImpl();

	@Override
	public Reimbursement addReimbursement(Accounts a, double reimbAmount, String reimbDescription, String reimbReceipt,
			int reimbType) {
		
		int reimbId = reimbDaoImpl.insertIntoReimb(reimbAmount, reimbDescription, reimbReceipt, a, reimbType);
		if(reimbId == -1) return null;
		 Reimbursement reimbObj = reimbDaoImpl.selectReimb(reimbId);
		 return reimbObj;
	}

	@Override
	public Reimbursement viewSingleReimb(int reimbId) {
		// TODO Auto-generated method stub
		Reimbursement reimbObj = reimbDaoImpl.selectReimb(reimbId);
		logger.info("inside viewSingleReimb");
		return reimbObj;
	}

	@Override
	public List<Reimbursement> viewAllReimb() {
		// TODO Auto-generated method stub
		List<Reimbursement> reimbList = reimbDaoImpl.selectAllReimb();
		if(reimbList == null || reimbList.size() == 0) {
			return null;
		}
		return reimbList;
	}

	@Override
	public List<Reimbursement> viewUserReimbs(Accounts a) {
		// TODO Auto-generated method stub
		List<Reimbursement> reimbList = reimbDaoImpl.selectUserReimbs(a.getUserId());
		if(reimbList == null || reimbList.size() == 0) {
			return null;
		}
		return reimbList;
	}

	@Override
	public Reimbursement changeReimbStatus(Reimbursement reimbObj, int reimbStatus, Accounts a) {
		// TODO Auto-generated method stub
		System.out.println("inside reimbDao");
		if(reimbDaoImpl.updateReimb(reimbObj, reimbStatus, a)) {
			return reimbObj;			
		} else {
			return null;
		}
	}
}

