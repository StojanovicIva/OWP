package servlets;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MovieDAO;
import model.Movie;

public class AddMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String name = request.getParameter("name");
			String director = request.getParameter("director");
			String actors = request.getParameter("actors");
			String style = request.getParameter("style");
			int duration = Integer.parseInt(request.getParameter("duration"));
			String distributor = request.getParameter("distributor");
			String country = request.getParameter("country");
			int year = Integer.parseInt(request.getParameter("year"));
			String description = request.getParameter("description");
			
			Movie movie = new Movie(name, director, actors, style, duration, distributor, country, year, description, 0);
			
			System.out.println(movie);
			MovieDAO dao = new MovieDAO();
			dao.addMovie(movie);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);

		}catch(Exception e) {
			request.getRequestDispatcher("./FailServlet").forward(request, response);
			e.printStackTrace();
		}
	}

}
