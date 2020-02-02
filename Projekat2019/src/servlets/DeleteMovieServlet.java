package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MovieDAO;
import dao.ProjectionDAO;
import model.Movie;
import model.Projection;

public class DeleteMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			MovieDAO MovieDao = new MovieDAO();
			ProjectionDAO ProjectionDao = new ProjectionDAO();
			
			Movie movie = MovieDao.findMovieById(id);
			ArrayList<Projection> projections = ProjectionDao.findProjectionByMovie(movie.getName());
			
			if(projections.size() != 0) {
				MovieDao.deleteMovieWithProjection(id);
			}else {
				MovieDao.delete(id);
			}
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
