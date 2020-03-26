package com.revature.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.models.Reimb;
import com.revature.models.User;

import services.ReimbService;

public class NewReimbServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1935950909486779986L;
	
	private Logger log = Logger.getLogger(LoginServlet.class);
	
	@Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse res)
	    throws IOException, ServletException {

	}
	
	
    @Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse res)
	    throws IOException, ServletException {

    	
    	 HttpSession session = req.getSession(true);
    	 
    	 if(session.getAttribute("currentUser") == null) res.sendRedirect("http://localhost:8080/project1web/login.html");
    	 
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
		 
		 Reimb r = new Reimb(amount, LocalDateTime.now(),  null, desc, ((User) session.getAttribute("currentUser")).getUserId(), -1, 0, type);
		 ReimbService.storeReimb(r);
		 log.info("Created new reimbursement.");
		 
		 
	 }

}
