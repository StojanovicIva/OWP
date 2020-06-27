package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TicketDAO;
import dao.UserDAO;
import model.Ticket;
import model.User;

public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			UserDAO userDao = new UserDAO();
			TicketDAO ticketDao = new TicketDAO();
			
			User user = userDao.findUserById(id);
			ArrayList<Ticket> tickets = ticketDao.getAllForUser(id);
			
			if(tickets.size() != 0) {
				userDao.deleteUserLogic(id);
			}else {
				userDao.delete(id);
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
