package com.javaproject.controller;

import java.io.File;
import java.io.IOException;
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
 * Servlet implementation class AdminEditDeleteMovie
 */
@WebServlet("/editMovie")
@MultipartConfig
public class AdminEditDeleteMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminService serv = new AdminService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminEditDeleteMovie() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("edit") != null) {
			editMovie(request, response);
		} else {
			deleteMovie(request, response);
		}

	}

	private void deleteMovie(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("movieId"));
		String title = request.getParameter("title");
		String file = AdminUtils.getPhotoPath() + File.separator + title + ".jpg";
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}
		int key = serv.deleteMovie(id);
		try {
			if (key == -1) {
				request.setAttribute("message", "Unable to delete Movie. Please contact system admin");
				getServletContext().getRequestDispatcher("/pages/AddMovieSuccess.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "Movie Deleted Successfully");
				getServletContext().getRequestDispatcher("/pages/AddMovieSuccess.jsp").forward(request, response);
			}
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void editMovie(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println(isMultipart);

		Collection<Part> parts;
		try {
			parts = request.getParts();

			for (Part part : parts) {
				System.out.println(part.getName());
			}
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			int id = Integer.parseInt(request.getParameter("movieId"));
			Part filePart = request.getPart("poster");
			// InputStream imageInputStream = filePart.getInputStream();
			// read imageInputStream

			String file = AdminUtils.getPhotoPath() + File.separator + title + ".jpg";
			File f = new File(file);
			if (f.exists()) {
				f.delete();
			}
			filePart.write(file);
			long generatedKey = serv.updateMovie(id, title, description, title);
			System.out.println("Generated key = " + generatedKey);
			System.out.println(file);
			if (generatedKey == -1) {
				request.setAttribute("message", "Unable to edit Movie. Please contact system admin");
				getServletContext().getRequestDispatcher("/pages/AddMovieSuccess.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "Movie Edited Successfully");
				request.setAttribute("movieId", generatedKey);
				request.setAttribute("movieTitle", title);
				request.setAttribute("movieDuration", request.getParameter("duration"));
				getServletContext().getRequestDispatcher("/pages/AddMovieSuccess.jsp").forward(request, response);

			}

		} catch (IllegalStateException | IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
