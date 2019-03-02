package com.javaproject.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaproject.dao.UserDAO;
@WebServlet(urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet{
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        int userid = Integer.parseInt(request.getParameter("userid"));
        String useridString = Integer.toString(userid);
        String password = request.getParameter("password");
        UserDAO userDAO =  new UserDAO();
        ServletContext sc = this.getServletContext();
        String isUserExists =null;
        isUserExists = userDAO.validateLoginInfo(userid, password);
        System.out.println("This user exists"+isUserExists);
        HttpSession session = request.getSession();
        
        System.out.println("In login controller");
        
//        try (PrintWriter out = response.getWriter()) {
//            
//            out.println("User Name: <b>"+ userid + "</b> Password: <b>"+ password+"</b><br>");
//            out.println("This user exists "+isUserExists);
//            out.close();
//        }
        if(isUserExists.equals("student"))
		{
        	RequestDispatcher rd = sc.getRequestDispatcher("/pages/booking.jsp");
        	session.setAttribute("userid", userid);
        	session.setAttribute("useridstring", useridString);
	        rd.forward(request, response);
			//response.sendRedirect("/pages/studentMovieList.jsp");
			//return;
		}
		else if(isUserExists.equals("admin"))
		{
			RequestDispatcher rd = sc.getRequestDispatcher("/adminDashboard");
	        rd.forward(request, response);
			//response.sendRedirect("/pages/admin.jsp");
			//return;
		}
		else {
			RequestDispatcher rd = sc.getRequestDispatcher("/pages/home.jsp");
			request.setAttribute("alertMsg", "Invalid userid or password");
	        rd.include(request, response);
	        //out.print("<p>Invalid userid or password</p>");
			
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
