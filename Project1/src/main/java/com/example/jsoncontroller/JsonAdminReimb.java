package com.example.jsoncontroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.Accounts;
import com.example.model.Login;
import com.example.model.Reimbursement;
import com.example.servicelayer.ReimbService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonAdminReimb {
	
	/**
	 * This method receives 3 parameters which first checks to see if there is a session and/or if the session account received 
	 * is the right type of accout(f_manager), or finance manager. Then it will call the ReimbServiceImpl's method viewAllPendingReimb and 
	 * return a List that holds all the info for the Reimbursement in the database where the status is pending and return it to the front-end.
	 * @param req : From HttpServletReqeust from TomCat.
	 * @param res : From HttpServletResponse from TomCat.
	 * @param rsi : The ReimbServiceImpl instantiated method from JsonRequestHelper.
	 * @throws IOException
	 */
	public static void viewAllReimb(HttpServletRequest req, HttpServletResponse res, ReimbService rsi) throws IOException {
		System.out.println("inside JsonAdminReimb viewpending");
		Accounts acc =(Accounts) req.getSession().getAttribute("account");
		if(acc == null || !acc.getRole().equals("f_manager")) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN, "Wrong account or no accounts in session");
			return;
		}
		
		List<Reimbursement> reimbList = rsi.viewAllReimb();
		if(reimbList == null || reimbList.size() == 0) {
			res.getWriter().write("null");
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		String data = "{";
		for(int i = 0; i < reimbList.size(); i++) {
			int reimbId = reimbList.get(i).getReimbursementId();
			if(i == reimbList.size() - 1) {
				data += "\"" + reimbId + "\": " + new ObjectMapper().writeValueAsString(reimbList.get(i)) + "}";
			} else {
				data += "\"" + reimbId + "\": " + new ObjectMapper().writeValueAsString(reimbList.get(i)) + ",";
			}
		}
		res.getWriter().write(data);
		res.setStatus(HttpServletResponse.SC_OK);
	}
	/**
	 * IMPORTANT***** The front-end should give String values of either 0, 1, 2 for pending approved or deny,respectively, for status Id. This method
	 *  will parse it into an integer.********* The Method updates the reimbursement. It checks to see if 
	 *  they can create the reimbObj from the inputs of the front-ends parameters sent. It also checks to see if there is a 
	 *  session and/or if the account has permission, f_manager(finance manager). Then update the specific reimbursement.
	 *  Codes: sends 200 if everything is okay else it'll send a 400 for bad request 0r a 403 for forbidden if acc is not correct.
	 * @param req : From HttpServletRequest
	 * @param res : From HttpServletResponse
	 * @param rsi : The ReimburseServiceImpl instance obj created in JsonHelperRequest.
	 * @throws IOException
	 */
	public static void updateReimb(HttpServletRequest req, HttpServletResponse res, ReimbService rsi) throws IOException {
		System.out.println("inside JsonAdminReimb updateReimb");
		ObjectMapper mapper = new ObjectMapper();
		Reimbursement reimbObj = null;
		try {
			reimbObj = mapper.readValue(req.getReader(),Reimbursement.class);
			System.out.println(reimbObj);
		}catch(Exception e) {
			e.printStackTrace();
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "There are 11 parmeters that should be sent (int, double, 9 strings");
		}
		Accounts acc = (Accounts) req.getSession().getAttribute("account");
		if(acc == null || !acc.getRole().equals("f_manager")) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN, "Wrong account or no accounts in session");
			return;
		}
		
		rsi.changeReimbStatus(reimbObj, Integer.parseInt(reimbObj.getReimbursementStatusId()), acc);
		res.setStatus(HttpServletResponse.SC_CREATED);
	}
}
