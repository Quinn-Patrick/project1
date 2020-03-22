package com.revature.servlets;

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

}
