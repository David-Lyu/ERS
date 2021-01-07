package com.example.forwardingcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.model.Accounts;
import com.example.model.Login;
import com.example.servicelayer.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginController {
	
	/**
	 * This method is called when someone if the front-end tries to login in. This return an Account object containing id , username,
	 * 		password, first name, last name, email, and role. If the credential is incorrect it will send null and not create a session
	 * @param req: HttpServletRequest object from TomCat
	 * @param res: HttpServletResponse object from TomCat
	 * @param usi: UserServiceImpl object that was instantiated in the JsonRequestHelper
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws ServletException 
	 */
	public static void login(HttpServletRequest req, HttpServletResponse res, UserService usi) 
	throws JsonProcessingException, IOException, ServletException {

		System.out.println("inside login");
		if(!req.getMethod().equals("POST")) {
			System.out.println("not post");
			res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		res.setContentType("application/json");
		
		ObjectMapper mapper = new ObjectMapper();
		Login login = mapper.readValue(req.getReader(),Login.class);
		System.out.println(login.getUsername() +" "+ login.getPassword());
		String username = login.getUsername();
		String password = login.getPassword();
		Accounts acc = usi.getUser(username, password);
		if(acc == null) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			req.getRequestDispatcher("/resources/html/badlogin.html");
			return;
		};
		
		req.getSession().setAttribute("account", acc);
		if(acc.getRole().equals("employee")) {
			req.getRequestDispatcher("/api/employee").forward(req, res);;			
		} else {
			System.out.println("inside admin");
			req.getRequestDispatcher("/api/admin").forward(req, res);
		}
	}

	public static void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// TODO Auto-generated method stub
		req.getSession().invalidate();
		res.sendRedirect("http://localhost:8999/Project1/index.html?logout=true");
		
	}
}
