package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ReimbService;

public class LoadImageServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1166855760858664183L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
	    throws IOException, ServletException {
		
		res.setContentType("application/json");
		String jsonRaw = "1";
		try(BufferedReader reader = req.getReader()){
			System.out.println("made it here.");
			jsonRaw = reader.readLine();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(jsonRaw);
		int id = Integer.parseInt(jsonRaw);
		
		
		res.setContentType("image/png");
		
		OutputStream os;
		
        
        
        os = res.getOutputStream();
        
        byte[] buf = ReimbService.downloadImage(id);
        res.setContentLength((int) buf.length);
        
        os.write(buf);
        
        
        
        os.close();
		
	}
}
