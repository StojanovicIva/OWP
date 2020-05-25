package servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProjectionDAO;
import dao.SeatDAO;
import model.Hall;
import model.Projection;

public class ProjectionServlet extends HttpServlet {
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
			
			projection = dao.findProjectionById(id);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
