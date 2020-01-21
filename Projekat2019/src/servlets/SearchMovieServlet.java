package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MovieDAO;
import dao.UserDAO;
import model.Movie;
import model.User;

public class SearchMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String criterium = request.getParameter("criterium");
		String input = request.getParameter("input");
		
		MovieDAO dao = new MovieDAO();
		Map<String, Object> data = new LinkedHashMap<String, Object>();

		System.out.println("Stigne: " + criterium + "+" + input);
		
		if(criterium.equals("name")) {
			System.out.println("Upao u name criterium;");
			ArrayList<Movie> movies = dao.findMovieByName(input);
			
			System.out.println(movies);
			
			data.put("movies", movies);
			
			request.setAttribute("data", data);
			System.out.println(data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			return;
			
		}
		else if(criterium.equals("style")) {
			ArrayList<Movie> movies = dao.findMovieByStyle(input);
			
			System.out.println(movies);
			
			data.put("movies", movies);
			
			request.setAttribute("data", data);
			System.out.println(data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			return;
		}
		else if(criterium.equals("distributor")) {
			ArrayList<Movie> movies = dao.findMovieByDistributor(input);
			
			System.out.println(movies);
			
			data.put("movies", movies);
			
			request.setAttribute("data", data);
			System.out.println(data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			return;
		}
		else if(criterium.equals("country")) {
			ArrayList<Movie> movies = dao.findMovieByCountry(input);
			
			System.out.println(movies);
			
			data.put("movies", movies);
			
			request.setAttribute("data", data);
			System.out.println(data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
