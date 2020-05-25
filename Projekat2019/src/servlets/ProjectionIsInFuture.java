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
import dao.ProjectionDAO;
import dao.SeatDAO;
import model.Hall;
import model.Movie;
import model.Projection;

public class ProjectionIsInFuture extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			int unableSeats = 0;
			int seatsInHall = 0;
			int numberOfFreeSeats = 0;
			
			ProjectionDAO dao = new ProjectionDAO();			
			Projection projection = new Projection();
			
			SeatDAO seatDao = new SeatDAO();
			Hall hall = new Hall();
			
			projection = dao.isItInFuture(id);
			unableSeats = seatDao.CountFreeSeats(id);
			
			hall = projection.getHall();
			seatsInHall = seatDao.CountNumberOfSeatsInHall(hall.getId());
			
			numberOfFreeSeats = seatsInHall - unableSeats;
		
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put("projection", projection);
			data.put("numberOfFreeSeats", numberOfFreeSeats);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(Exception e) {
			request.getRequestDispatcher("./FailServlet").forward(request, response);
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//MOVIE ID
			Integer id = Integer.parseInt(request.getParameter("id"));
			ArrayList<Projection> projections  = new ArrayList<Projection>();
			
			int unableSeats = 0;
			int seatsInHall = 0;
			int numberOfFreeSeats = 0;
	
			ProjectionDAO dao = new ProjectionDAO();			
			Projection projection = new Projection();
			
			SeatDAO seatDao = new SeatDAO();
			Hall hall = new Hall();
			
			projections = dao.findProjectionByMovieId(id);
			for (Projection pro : projections){
				int projectionId = pro.getId();
			
				projection = dao.isItInFuture(projectionId );
				unableSeats = seatDao.CountFreeSeats(projectionId );
				
				hall = projection.getHall();
				seatsInHall = seatDao.CountNumberOfSeatsInHall(hall.getId());
				
				numberOfFreeSeats = seatsInHall - unableSeats;
			
			}
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put("projection", projection);
			data.put("projections", projections);
			data.put("numberOfFreeSeats", numberOfFreeSeats);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(Exception e){
			request.getRequestDispatcher("./FailServlet").forward(request, response);
			e.printStackTrace();
		}
	}

}
