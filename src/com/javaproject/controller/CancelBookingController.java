package com.javaproject.controller;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

@WebServlet(urlPatterns = {"/CancelBookingController"})
public class CancelBookingController extends HttpServlet{
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
	{
		Statement stmt;
		 PreparedStatement pstmtone,pstmttwo,pstmtthree,pstmtfour;
		 Connection connection;
		DBInitializer database= new DBInitializer();
		connection = database.initializeDB();
		ServletContext sc = this.getServletContext();
		String query1, query2, query3;
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
       response.setContentType("text/html;charset=UTF-8");
       
       int showid = 0 , seatsBooked = 0, availableSeats = 0, newSeats = 0;
       
       
       String[] selectedBookingIds = request.getParameterValues("bookingID");
       System.out.println("In cancel booking controller");
       

       for(String bookingid:selectedBookingIds)
		{
       	System.out.println("Inside seat booking");
       	int selectedid = Integer.parseInt(bookingid);
   		try {
   			query2 = "select seatsbooked, showid from booking where bookingid = ?";
   			pstmttwo = connection.prepareStatement(query2);
   			pstmttwo.setInt(1, selectedid);
   			ResultSet rset2 = pstmttwo.executeQuery();
   			if(rset2.next()) {
   				seatsBooked = rset2.getInt(1);
   				showid = rset2.getInt(2);
   			}
   			String query4 = "select seats from showtime where showid = ?";
   			pstmtfour = connection.prepareStatement(query4);
   			pstmtfour.setInt(1, showid);
   			ResultSet rset4 = pstmtfour.executeQuery();
   			if(rset4.next()) {
   				availableSeats = rset2.getInt(1);
   				
   			}
   			System.out.println("Inside queryone execution"+selectedid);
   			query1 = "delete from booking where bookingid= ?";
   			System.out.println(query1);
   			pstmtone = connection.prepareStatement(query1);
   			System.out.println("after preparestatment");
   			pstmtone.setInt(1, selectedid);
   			pstmtone.executeUpdate(); 
   			
   			newSeats = availableSeats + seatsBooked;
   			query3 = "update showtime set seats = ? where showid = ?";
   			
   			pstmtthree = connection.prepareStatement(query3);
   			pstmtthree.setInt(1, newSeats);
   			pstmtthree.setInt(2, selectedid);
   			pstmtthree.executeUpdate();
   			
   			System.out.println("After queryone execution");
   		    
   		}
   		catch(Exception ex){
   			ex.getStackTrace();
   		}
   		
   		
       	RequestDispatcher rd = sc.getRequestDispatcher("/pages/cancelBooking.jsp");
       	
	        rd.forward(request, response);
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
