package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TicketDAO;
import model.Ticket;

public class TicketsForProjection extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			TicketDAO dao = new TicketDAO();			
			ArrayList<Ticket> tickets = dao.getTicketsForProjection(id);
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put("tickets", tickets);
			
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
