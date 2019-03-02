package com.javaproject.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaproject.dao.DBInitializer;
import com.javaproject.dao.MovieListDAO;
import com.javaproject.dao.UserDAO;
import com.javaproject.model.ShowTime;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  
@WebServlet(urlPatterns = {"/MovieBookingController"})
public class MovieBookingController extends HttpServlet{
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
	{
		Statement stmt;
		 PreparedStatement pstmtone,pstmttwo;
		 Connection connection;
		DBInitializer database= new DBInitializer();
		connection = database.initializeDB();
		String queryone,querytwo;
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        int movieid = (Integer)session.getAttribute("movieid");
        ShowTime showtime= new MovieListDAO().getMovieShowTimeForToday(movieid);	
        ServletContext sc = this.getServletContext();
        String isUserExists =null;
        int requriedSeats = Integer.parseInt(request.getParameter("seats"));
        int availableSeats = showtime.getAvailableSeats();
        
        
        System.out.println("In movie booking controller");
        

        if((availableSeats - requriedSeats)>0)
		{
        	System.out.println("Inside seat booking");
        	int newSeats = (availableSeats - requriedSeats);
        	int showid = showtime.getId();
    		try {
    			System.out.println("Inside queryone execution"+newSeats);
    			queryone = "update showtime set seats = ? where movieid = ? and showid = ?";
    			System.out.println(queryone);
    			
    			System.out.println("Iquery creation"+queryone+" and");
    			pstmtone = connection.prepareStatement(queryone);
    			System.out.println("after preparestatment");
    			pstmtone.setInt(1, newSeats);
    			pstmtone.setInt(2, movieid);
    			pstmtone.setInt(3, showid);
    			System.out.println(pstmtone.toString());
    			pstmtone.executeUpdate(); 
    			System.out.println("After queryone execution");
    		    
    		}
    		catch(Exception ex){
    			ex.getStackTrace();
    		}
    		/*String to = "jxv84500@ucmo.edu";//change accordingly  
    	      String from = "jxv84500@ucmo.edu";//change accordingly  
    	      String host = "localhost";//or IP address  
    	      //final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    	     //Get the session object  
    	      Properties properties = System.getProperties();  
    	      properties.setProperty("mail.smtp.host", "192.168.1.164");
    	      //properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
    	      //properties.setProperty("mail.smtp.socketFactory.fallback", "false");
    	      //properties.setProperty("mail.smtp.port", "465");
    	     // properties.setProperty("mail.smtp.socketFactory.port", "465");
    	      //properties.put("mail.smtp.auth", "true");
    	      //properties.put("mail.debug", "true");
    	      //properties.put("mail.store.protocol", "pop3");
    	      //properties.put("mail.transport.protocol", "smtp");
    	      //final String username = "jxv84500@ucmo.edu";//
    	      //final String password = "Jv!123456";
    	      //Session session2 = Session.getDefaultInstance(properties, 
                //      new Authenticator(){
                 // protected PasswordAuthentication getPasswordAuthentication() {
                   //  return new PasswordAuthentication(username, password);
                  //}});  
    	      Session session2 = Session.getDefaultInstance(properties); 
    	     //compose the message  
    	      try{  
    	         MimeMessage message = new MimeMessage(session2);  
    	         message.setFrom(new InternetAddress(from));  
    	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
    	         message.setSubject("Ping");  
    	         message.setText("Hello, Your Union Movie tickets are booked  ");  
    	  
    	         // Send message  
    	         Transport.send(message);  
    	         System.out.println("message sent successfully....");  
    	  
    	      }catch (MessagingException mex) {mex.printStackTrace();}  */
    		MovieListDAO movieDAO = new MovieListDAO();
    		movieDAO.addBookingEntry(request,response,sc);
        	RequestDispatcher rd = sc.getRequestDispatcher("/pages/movieBooking.jsp");
        	
	        rd.forward(request, response);
		}
		else 
		{
			RequestDispatcher rd = sc.getRequestDispatcher("/pages/booking.jsp");
			request.setAttribute("alertMsg", "Number of seats not available");
	        rd.forward(request, response);
	        out.print("<p>Invalid userid or password</p>");
			//response.sendRedirect("/pages/admin.jsp");
			//return;
		}
		
        
        
        
    }

	

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
