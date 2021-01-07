package com.example.forwardingcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.Accounts;
import com.example.model.Login;
import com.example.model.Reimbursement;
import com.example.servicelayer.ReimbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class EmployeeController {
	
	public static void getEmployeePage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.sendRedirect("http://localhost:8999/Project1/resources/html/loggedIn.html?type=employee");
	}

	
}
