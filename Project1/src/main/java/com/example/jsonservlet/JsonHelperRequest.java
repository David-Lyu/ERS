package com.example.jsonservlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.forwardingcontroller.LoginController;
import com.example.jsoncontroller.JsonAdminReimb;
import com.example.jsoncontroller.JsonEmployeeReimb;
import com.example.servicelayer.ReimbService;
import com.example.servicelayer.ReimbServiceImpl;
import com.example.servicelayer.UserService;
import com.example.servicelayer.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonHelperRequest {
	
	public static void process(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		System.out.println(req.getRequestURI());
		
		UserService usi = new UserServiceImpl();
		ReimbService rsi = new ReimbServiceImpl();
		
		switch(req.getRequestURI()) {
		case "/Project1/json/employee":
			JsonEmployeeReimb.getEmployeeReimb(req, res, rsi);
			break;
		case "/Project1/json/add":
			JsonEmployeeReimb.addEmployeeReimb(req,res, rsi);
			break;
		case "/Project1/json/view-pending":
			JsonAdminReimb.viewAllReimb(req, res, rsi);
			break;
		case "/Project1/json/update":
			JsonAdminReimb.updateReimb(req, res, rsi);
			break;
		default:
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "did not send right request");
			return;
		}
	}
}
