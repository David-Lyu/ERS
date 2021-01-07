package com.example.forwardingservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.forwardingcontroller.AdminController;
import com.example.forwardingcontroller.EmployeeController;
import com.example.forwardingcontroller.LoginController;
import com.example.servicelayer.ReimbService;
import com.example.servicelayer.ReimbServiceImpl;
import com.example.servicelayer.UserService;
import com.example.servicelayer.UserServiceImpl;

public class RequestHelper {
	
	public static Logger logger = Logger.getLogger(RequestHelper.class);
	
	public static void process(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		logger.info(req.getRequestURI());
		
		UserService usi = new UserServiceImpl();
		ReimbService rsi = new ReimbServiceImpl();
		
		switch(req.getRequestURI()) {
		case "/Project1/api/login": 
			LoginController.login(req, res, usi);
			break;
		case "/Project1/api/employee":
			EmployeeController.getEmployeePage(req, res);
			break;
		case "/Project1/api/admin":
			AdminController.getAdminPage(req, res);
			break;
		case "/Project1/api/logout":
			LoginController.logout(req,res);
		default: 
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			break;
		}
	}
}
