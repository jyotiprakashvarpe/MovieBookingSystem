package com.javaproject.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.javaproject.service.AdminService;
import com.javaproject.util.AdminUtils;

/**
 * Servlet implementation class AddMovieServlet
 */
@WebServlet(description = "Controller to add movie page", urlPatterns = { "/addMovie" })
@MultipartConfig
public class AddMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    AdminService serv = new AdminService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovieServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#processRequest(HttpServletRequest, HttpServletResponse)
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			System.out.println(isMultipart);
		
		Collection<Part> parts = request.getParts();
	      for(Part part:parts){
	    	  System.out.println(part.getName());
	      }
	      String title = request.getParameter("title");
	      String description = request.getParameter("description");
	      int duration = Integer.parseInt(request.getParameter("duration"));
	      Part filePart = request.getPart("poster");
//	      InputStream imageInputStream = filePart.getInputStream();
	      //read imageInputStream
	      
	      String file = AdminUtils.getPhotoPath()+File.separator+title+".jpg";
	      File f = new File(file);
	      if(f.exists()){
	    	  f.delete();
	      }
		filePart.write(file);
	      long generatedKey = serv.addMovie(title, description, title, duration);
	      System.out.println("Generated key = " + generatedKey);
	      System.out.println(file);
	      if(generatedKey==-1){
	    	  request.setAttribute("message", "Unable to add Movie. Please contact system admin");
	    	  getServletContext().getRequestDispatcher("/pages/AddMovieSuccess.jsp").forward(request, response);
	      } else {
	    	  request.setAttribute("message", "Movie Added Successfully");
	    	  request.setAttribute("movieId", generatedKey);
	    	  request.setAttribute("movieTitle", title);
	    	  request.setAttribute("movieDuration", duration);
	    	  getServletContext().getRequestDispatcher("/pages/AddMovieSuccess.jsp").forward(request, response);
	    	  
	      }
	    	  
	      //can also write the photo to local storage
	      
//	      //Read Name, String Type 
//	       Part namePart = request.getPart("name");
//	       if(namePart.getSize() > 20){
//	           //write name cannot exceed 20 chars
//	       }
//	       //use nameInputStream if required        
//	       InputStream nameInputStream = namePart.getInputStream();
//	       //name , String type can also obtained using Request parameter 
//	       String nameParameter = request.getParameter("name");
//
//	       //Similialrly can read age properties
//	       Part agePart = request.getPart("age");
//	       int ageParameter = Integer.parseInt(request.getParameter("age"));



		
	}

}
