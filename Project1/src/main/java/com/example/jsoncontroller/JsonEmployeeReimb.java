package com.example.jsoncontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.Accounts;
import com.example.model.Reimbursement;
import com.example.servicelayer.ReimbService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;

public class JsonEmployeeReimb {
	
	/**
	 * This method receives a post method of the front-end, which then calls the addReimbursement method from the ReimbServiceLayer.
	 * It inputs the data from the front-end as arguements towards the method. It then returns the newly created reimbursement object to 
	 * the frontend.
	 * @param req : From JsonMasterServlet's HttpServletRequset which is passed down to the JsonHelperRequest.
	 * @param res : From JsonMasterServlet's HttpServletRequset which is passed down to the JsonHelperRequest.
	 * @param rsi : The instantiated ReimbServiceImpl class from JsonHelperRequest.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void addEmployeeReimb(HttpServletRequest req, HttpServletResponse res, ReimbService rsi) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> reimb = mapper.readValue(req.getReader(), new TypeReference<Map<String, String>>(){});
		if(reimb.size() !=4) {
			res.getWriter().write("null");
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		System.out.println(reimb);
		Accounts a =(Accounts)req.getSession().getAttribute("account");
		if(a == null) {
			res.getWriter().write("null");
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		Reimbursement newReimb = rsi.addReimbursement(a, Double.parseDouble(reimb.get("reimbursementAmount")), reimb.get("reimbursementDescription"), reimb.get("reimbursementReceipt"), Integer.parseInt(reimb.get("reimbursementTypeId")));
		if(newReimb == null) {
			res.getWriter().write("null");
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		res.getWriter().write(new ObjectMapper().writeValueAsString(newReimb));
	}
	
	/**
	 * This method receives a username from the client and checks for an active sessions.
	 * 		After the check it will get all the users corresponding reimbursements and return them to
	 * 		the client via an array.
	 * @param req : From the HttpServletRequest given by TomCat
	 * @param res : From the HttpServletResponse given by TomCat
	 * @param rsi : The object ReimbService instantiated from the Helper Class
	 * @throws IOException
	 */
	public static void getEmployeeReimb(HttpServletRequest req, HttpServletResponse res, ReimbService rsi)
			throws IOException {
		System.out.println("inside getEmployReimb");
		PrintWriter writer = res.getWriter();
		Accounts a;
		a = (Accounts) req.getSession().getAttribute("account");
		System.out.println("JsonEmployeeReimb: line 52: \n" + a);
		if (a == null) {
			writer.write("null");
			writer.flush();
			writer.close();
//			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "no session found");
			return;
		}

		List<Reimbursement> userReimb = rsi.viewUserReimbs(a);
		if(userReimb == null || userReimb.size() == 0) {
			writer.write("[null]");
			writer.flush();
			writer.close();
			return;
		}
		String string = "[";
		for (int i = 0; i < userReimb.size(); i++) {
			if (i == userReimb.size() - 1) {
				string += new ObjectMapper().writeValueAsString(userReimb.get(i)) + "]";
			} else {
				string += new ObjectMapper().writeValueAsString(userReimb.get(i)) + ", ";
			}
		}
		System.out.println(string);
		writer.write(string);
		writer.flush();
		writer.close();
		res.setStatus(HttpServletResponse.SC_ACCEPTED);

	}
}
