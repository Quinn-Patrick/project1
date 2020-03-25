package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;

import services.Hashing;
import services.UserService;

public class LoginServlet extends HttpServlet{

	private static final ObjectMapper om = new ObjectMapper();
	/**
	 * 
	 */
	private static final long serialVersionUID = 5901784448217028279L;
	
	@Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse res)
			  throws IOException, ServletException {
		res.setContentType("application/json");
		
		String username = "";
		String password = "";
		String jsonRaw = "";
		try(BufferedReader reader = req.getReader()){
			System.out.println("made it here.");
			
			jsonRaw = reader.readLine();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String[] jsonSplit = jsonRaw.split("\"");
		
		username = jsonSplit[3];
		password = jsonSplit[7];
		
		User u = UserService.retrieveUser(username);
		System.out.println("Attempting to pull user " + username);
		if(u == null) {
			System.out.println("Invalid User");
			res.getWriter().println(om.writeValueAsString(u));
		}else {
			try {
				if(Hashing.hash(password).equals(u.getPassword())) {
					HttpSession session = req.getSession();
					session.setAttribute("currentUser", u);
					res.getWriter().println(om.writeValueAsString(u));
					System.out.println(om.writeValueAsString(u));
					System.out.println("Login should have been successful.");
					//res.sendRedirect("http://localhost:8080/project1web/frontPage.html");
				}else {
					res.getWriter().println(om.writeValueAsString(null));
					System.out.println("Wrong password.");
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
	 }
}
