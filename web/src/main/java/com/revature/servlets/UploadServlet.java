package com.revature.servlets;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


import org.apache.log4j.Logger;

import services.ReimbService;

@MultipartConfig
public class UploadServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5229073680053047462L;

	private Logger log = Logger.getLogger(LoginServlet.class);

	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    		throws IOException, ServletException {

    	
		HttpSession session = req.getSession(true);
		
		Part image =  req.getPart("img");
		
		System.out.println(image);
		System.out.println(session.getAttribute("mostRecentReimb"));
		System.out.println(Integer.parseInt(session.getAttribute("mostRecentReimb").toString()));
		
		ReimbService.uploadImage((Integer) session.getAttribute("mostRecentReimb"), image);
		
		res.sendRedirect("http://localhost:8080/project1web/frontPage.html");
    }
}
