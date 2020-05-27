package servlets;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProjectionDAO;
import dao.SeatDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Projection;
import model.Seat;
import model.Ticket;
import model.User;

public class BuyTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			int projectionId = Integer.parseInt(request.getParameter("projectionId"));
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			Integer userId = Integer.parseInt(request.getParameter("user"));		
			
			ProjectionDAO projectionDao = new ProjectionDAO();
			Projection projection = projectionDao.findProjectionById(projectionId);
			
			SeatDAO seatDao = new SeatDAO();
			Seat seat = seatDao.findSeatById(seatId);
			
			UserDAO userDao = new UserDAO();
			User user = userDao.findUserById(userId);
			
			Ticket ticket = new Ticket(projection, seat, user);
			TicketDAO dao = new TicketDAO();
			dao.addTicket(ticket);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);

		}catch(Exception e) {
			request.getRequestDispatcher("./FailServlet").forward(request, response);
			e.printStackTrace();
		}

	}
}
