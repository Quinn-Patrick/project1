package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimb;

import services.ReimbService;

public class ViewReimbServlet extends HttpServlet{

	
	private static final long serialVersionUID = -4017824897732540121L;
	private List<Reimb> reimbs = ReimbService.retrieveAllReimbs();
	private static final ObjectMapper om = new ObjectMapper();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		    throws IOException, ServletException {
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
			e.printStackTrace();
		}
		System.out.println(jsonRaw);
		if(jsonRaw != null) {
			String[] jsonSplit = jsonRaw.split("\"");
			
			id = Integer.parseInt(jsonSplit[2].replace(":", "").replace(",", ""));
			approved = Boolean.parseBoolean(jsonSplit[4].replace(":", "").replace(",", ""));
			
			Reimb targetReimb = ReimbService.retrieveReimb(id);
			if(approved) {
				targetReimb.setStatus(1);
				ReimbService.storeReimb(targetReimb);
			}else {
				ReimbService.deleteReimb(id);
			}
		}
		
	}
}
