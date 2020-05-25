package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProjectionDAO;
import dao.TicketDAO;
import model.Ticket;

public class DeleteProjectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			Integer id = Integer.parseInt(request.getParameter("id"));			
			
			TicketDAO ticketDao = new TicketDAO();
			ProjectionDAO ProjectionDao = new ProjectionDAO();
			
			ArrayList<Ticket> tickets = ticketDao.getTicketsForProjection(id);						
			
			if(tickets.size() != 0) {
				ProjectionDao.deleteProjectionWithTickets(id);
			}else {
				ProjectionDao.delete(id);
			}
			
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
