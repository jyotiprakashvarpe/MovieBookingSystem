package com.javaproject.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaproject.model.Movie;
import com.javaproject.service.AdminService;

@WebServlet(name="AdminController", urlPatterns={"/adminDashboard" })
public class AdminController extends HttpServlet {
	private AdminService adminService = new AdminService();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		processRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		processRequest(req, resp);
	};
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		List<Movie> movieSchedule = adminService.getMovieSchedule();
		System.out.println(movieSchedule);
		request.setAttribute("movieList", movieSchedule);
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/AdminDashboard.jsp");
		requestDispatcher.forward(request, response);
		
		
	}
	
}
