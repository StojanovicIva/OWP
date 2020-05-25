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
import model.Movie;

public class SearchMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			String criterium = request.getParameter("criterium");
			String input = request.getParameter("input");
			
			MovieDAO dao = new MovieDAO();
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			ArrayList<Movie> movies = null;
			
			if(criterium.equals("name")) {
				movies = dao.findMovieByName(input);
							
			}
			else if(criterium.equals("style")) {
				movies = dao.findMovieByStyle(input);
				
			}
			else if(criterium.equals("distributor")) {
				movies = dao.findMovieByDistributor(input);

			}
			else if(criterium.equals("country")) {
				movies = dao.findMovieByCountry(input);

			}else if(criterium.contentEquals("time")) {
				int fromThe = Integer.parseInt(request.getParameter("fromThe"));
				int to = Integer.parseInt(request.getParameter("to"));
				movies = dao.findMovieByRangeOfTime(fromThe, to);
				
			}else if(criterium.contentEquals("date")) {
				int fromThe = Integer.parseInt(request.getParameter("fromThe"));
				int to = Integer.parseInt(request.getParameter("to"));
				movies = dao.findMovieByRangeOfDate(fromThe, to);
			}
			
			data.put("movies", movies);
			
			request.setAttribute("data", data);
			System.out.println(data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);	
		}catch(Exception e){
			request.getRequestDispatcher("./FailServlet").forward(request, response);
			e.printStackTrace();
		}
		
			}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
