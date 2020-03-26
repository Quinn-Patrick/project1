package com.revature.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import services.ReimbService;

@MultipartConfig
public class UploadServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5229073680053047462L;
	private String filepath;
	private Logger log = Logger.getLogger(LoginServlet.class);
	private File file;
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    		throws IOException, ServletException {
		filepath = getServletContext().getInitParameter("file-upload");
    	
		HttpSession session = req.getSession(true);
		
		Part image =  req.getPart("img");
		
		System.out.println(image);
		System.out.println(session.getAttribute("mostRecentReimb"));
		System.out.println(Integer.parseInt(session.getAttribute("mostRecentReimb").toString()));
		
		ReimbService.uploadImage((Integer) session.getAttribute("mostRecentReimb"), image);
		
		
    	DiskFileItemFactory factory = new DiskFileItemFactory();
    	
    	
    	
    	//max file size
    	factory.setSizeThreshold(4096);
    	//excessive size location
    	factory.setRepository(new File("c:\\temp"));
    	//file upload handler
    	ServletFileUpload upload = new ServletFileUpload(factory);
    	// maximum file size to be uploaded.
        upload.setSizeMax( 50*1024 );
        try { 
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(req);
            
            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            System.out.println("Made it here.");
            while ( i.hasNext () ) {
                FileItem fi = (FileItem)i.next();
                if ( !fi.isFormField () ) {
                   // Get the uploaded file parameters
                   String fieldName = fi.getFieldName();
                   String fileName = fi.getName();
                   String contentType = fi.getContentType();
                   boolean isInMemory = fi.isInMemory();
                   long sizeInBytes = fi.getSize();
                
                   // Write the file
                   if( fileName.lastIndexOf("\\") >= 0 ) {
                      file = new File( filepath + fileName.substring( fileName.lastIndexOf("\\"))) ;
                   } else {
                      file = new File( filepath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                   }
                   System.out.println(file);
                   fi.write( file ) ;
                   
                }
             }
        }catch(Exception e) {
        	log.error("Error with the image (no surprise there)" + e.getStackTrace());
        	e.printStackTrace();
        	
        	
        }
    }
}
