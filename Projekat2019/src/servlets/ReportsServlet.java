package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Hall;
import model.Movie;
import model.ProjectType;
import model.Projection;
import model.Report;
import model.User;
import dao.HallDAO;
import dao.MovieDAO;
import dao.ProjectTypeDAO;
import dao.ProjectionDAO;
import dao.ReportsDAO;
import dao.UserDAO;

public class ReportsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			
			Date FromDateAndTime;
			Date ToDateAndTime;
			try {
				java.util.Date fromDate = dateFormat.parse(request.getParameter("fromDateAndTime"));
				java.util.Date toDate = dateFormat.parse(request.getParameter("toDateAndTime"));
				FromDateAndTime = fromDate;
				ToDateAndTime = toDate;
			} catch (Exception ex) {
				FromDateAndTime = null;
				ToDateAndTime = null;
			}					
			
			ReportsDAO dao = new ReportsDAO();
			ProjectionDAO pro = new ProjectionDAO();
			
			ArrayList<Report> reports = dao.getReportsForSelectedDateAndTime(FromDateAndTime, ToDateAndTime);
			
			
			int numberOfProjections = 0;
			int numberOfSoldTickets = 0;
			int totalPrice = 0;
			
			for(Report report : reports){
				numberOfProjections += report.getNumberOfProjections();
				numberOfSoldTickets += report.getNumberOfSoldTickets();
				totalPrice += report.getTotalPrice();
			}
																			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			data.put("numberOfProjections", numberOfProjections);
			data.put("numberOfSoldTickets", numberOfSoldTickets);
			data.put("totalPrice", totalPrice);
			
			data.put("reports", reports);
						
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);

		}catch(Exception e) {
			request.getRequestDispatcher("./FailServlet").forward(request, response);

			e.printStackTrace();
		}
	}
	}