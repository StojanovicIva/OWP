package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SeatDAO;
import model.Seat;



public class CountSeatsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Integer hallId = Integer.parseInt(request.getParameter("id"));
			System.out.println("HALL ID: " + hallId);
			
			SeatDAO dao = new SeatDAO();			
			
			ArrayList<Seat> seats = dao.getAllSeats(hallId);
						
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put("seats", seats);
			request.setAttribute("data", data);
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
