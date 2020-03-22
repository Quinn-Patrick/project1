package com.revature.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.models.Reimb;

import services.ReimbService;

public class NewReimbServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1935950909486779986L;
	
	@Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse res)
	    throws IOException, ServletException {

	}
	
	
    @Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse res)
	    throws IOException, ServletException {

		 res.setContentType("application/json");
		 double amount = Double.parseDouble(req.getParameter("amount"));
		 int type = 0;
		 switch(req.getParameter("type")) {
		 case("food"):type = 0;
		 break;
		 case("lodging"):type = 1;
		 break;
		 case("travel"):type = 2;
		 break;
		 case("other"):type = 3;
		 break;
		 }
		 
		 String desc = req.getParameter("desc");
		 
		 
		 Reimb r = new Reimb(amount, LocalDateTime.now(),  null, desc, 0, -1, 0, type);
		 ReimbService.storeReimb(r);
		 
	 }

}
