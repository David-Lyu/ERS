package com.example.forwardingcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminController {
	/**
	 * This method retrieves the admin page and sends it to the front-end.
	 * @param req : HttpServletRequest
	 * @param res : HttpServletResponse
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public static void getAdminPage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("adminpage");
		res.sendRedirect("http://localhost:8999/Project1/resources/html/loggedIn.html?type=admin");
	}
}
