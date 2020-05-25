package servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MovieDAO;
import model.Movie;


public class UpdateMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			Integer id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String director = request.getParameter("director");
			String actors = request.getParameter("actors");
			String style = request.getParameter("style");
			Integer duration =Integer.parseInt(request.getParameter("duration"));
			String distributor = request.getParameter("distributor");
			String country = request.getParameter("country");
			Integer year = Integer.parseInt(request.getParameter("year"));
			String description = request.getParameter("description");
			
			MovieDAO dao = new MovieDAO();
			Movie updatedMovie = dao.findMovieById(id);
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			updatedMovie.setId(id);
			updatedMovie.setName(name);
			updatedMovie.setDirector(director);
			updatedMovie.setActors(actors);
			updatedMovie.setStyle(style);
			updatedMovie.setDuration(duration);
			updatedMovie.setDistributor(distributor);
			updatedMovie.setCountry(country);
			updatedMovie.setYear(year);
			updatedMovie.setDescription(description);
			
			dao.update(updatedMovie);
			
			data.put("updatedMovie", updatedMovie);
			request.setAttribute("data", data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
	
		}catch(Exception e){
			request.getRequestDispatcher("./FailServlet").forward(request, response);
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
