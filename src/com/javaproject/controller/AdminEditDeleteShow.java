package com.javaproject.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaproject.service.AdminService;

/**
 * Servlet implementation class AdminEditDeleteShow
 */
@WebServlet("/editShow")
public class AdminEditDeleteShow extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminService serv = new AdminService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEditDeleteShow() {
        super();
        // TODO Auto-generated constructor stub
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

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(request.getParameter("delete")!=null){
			int id = Integer.parseInt(request.getParameter("id"));
			int res = serv.deleteShow(id);
			if(res!=-1){
				request.setAttribute("message", "Show deleted successfully");
				getServletContext().getRequestDispatcher("/pages/AddMovieSuccess.jsp").forward(request, response);
				
			}else {
				request.setAttribute("message", "Show cannot be deleted");
				getServletContext().getRequestDispatcher("/pages/AddMovieSuccess.jsp").forward(request, response);
			}
		}else{
			editShow(request, response);
		}
	
	}

	private void editShow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()){
			String nextElement = paramNames.nextElement();
			System.out.println(nextElement + " : " + request.getParameter(nextElement) + " - " + request.getParameter(nextElement).getClass());
		}
		String date = request.getParameter("dateTime");
		int hours= Integer.parseInt(request.getParameter("hours"));
		int min = Integer.parseInt(request.getParameter("min"));
		int duration = Integer.parseInt(request.getParameter("duration"));
		String movieID = request.getParameter("movieID");
		int id = Integer.parseInt(request.getParameter("id"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startDate = sdf.parse(date);
			Date startTime = new Date(startDate.getTime());
			Calendar c = Calendar.getInstance();
			c.setTime(startTime);
			c.add(Calendar.HOUR_OF_DAY, hours);
			c.add(Calendar.MINUTE, min);
			startTime = c.getTime();
			c.add(Calendar.MINUTE, duration);
			Date endTime = c.getTime();
			boolean valid = serv.validateShowTiming(startTime, endTime);
			if(valid){
				serv.updateShow(id, movieID, startTime, endTime, 30, startDate);
				request.setAttribute("message", "Show edited successfully");
				getServletContext().getRequestDispatcher("/pages/AddMovieSuccess.jsp").forward(request, response);
				
			}else {
				request.setAttribute("message", "Show cannot be added as other show is scheduled for same time");
				getServletContext().getRequestDispatcher("/pages/AddMovieSuccess.jsp").forward(request, response);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
