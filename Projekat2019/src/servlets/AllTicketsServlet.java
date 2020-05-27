package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TicketDAO;
import model.Ticket;

public class AllTicketsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		try {
			Integer userId = Integer.parseInt(request.getParameter("id"));
			
			if (userId == null) {
				throw new AuthenticationException();
			}
			
			TicketDAO dao = new TicketDAO();
			
			ArrayList<Ticket> tickets = dao.getAllForUser(userId);
		
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put("tickets", tickets);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(IllegalArgumentException e) {
			request.getRequestDispatcher("./FailServlet").forward(request, response);
			e.printStackTrace();
		}catch(AuthenticationException e) {
			request.getRequestDispatcher("./UnauthorizedServlet").forward(request, response);
			e.printStackTrace();
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
