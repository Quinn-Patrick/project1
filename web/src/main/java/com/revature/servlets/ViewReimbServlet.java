package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimb;
import com.revature.models.User;

import services.ReimbService;

public class ViewReimbServlet extends HttpServlet{

	
	private static final long serialVersionUID = -4017824897732540121L;
	private static final ObjectMapper om = new ObjectMapper();
	private Logger log = Logger.getLogger(ViewReimbServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException {
		HttpSession session = req.getSession(true);
		User user = (User) session.getAttribute("currentUser");
		List<Reimb> reimbs = null;
		if(user.getRole() == 1) {
			reimbs = ReimbService.retrieveAllReimbs();
		}else {
			reimbs = ReimbService.retrieveAllUserReimbs(user.getUserId());
		}
		
		
		res.getWriter().println(om.writeValueAsString(reimbs));
		System.out.println(om.writeValueAsString(reimbs));

	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException {
		String jsonRaw = "";
		int id = -1;
		
		
		
		boolean approved = false;
		try(BufferedReader reader = req.getReader()){
			jsonRaw = reader.readLine();	
		}catch(Exception e) {
			log.error(e.getStackTrace());
		}
		System.out.println(jsonRaw);
		if(jsonRaw != null) {
			String[] jsonSplit = jsonRaw.split("\"");
			
			id = Integer.parseInt(jsonSplit[2].replace(":", "").replace(",", ""));
			approved = Boolean.parseBoolean(jsonSplit[4].replace(":", "").replace(",", "").replace("}", ""));
			int approver = Integer.parseInt(jsonSplit[6].replace(":", "").replace(",", "").replace("}", ""));
			System.out.println(jsonSplit[6].replace(":", "").replace(",", "").replace("}", ""));
			System.out.println(approved);
			Reimb targetReimb = ReimbService.retrieveReimb(id);
			if(approved) {
				targetReimb.setStatus(1);
				targetReimb.setResolver(approver);
				targetReimb.setResolved(LocalDateTime.now());
				log.info("Approved reimb " + id);
				ReimbService.storeReimb(targetReimb);
			}else {
				log.info("Denied and deleted reimb " + id);
				ReimbService.deleteReimb(id);
			}
		}	
	}
}
