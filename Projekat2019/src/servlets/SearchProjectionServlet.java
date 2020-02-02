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

import dao.MovieDAO;
import dao.ProjectionDAO;
import model.Movie;
import model.Projection;

public class SearchProjectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String criterium = request.getParameter("criterium");
		
		ProjectionDAO dao = new ProjectionDAO();
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		
		ArrayList<Projection> projections = null;
		
		if(criterium.equals("movieName")) {
			String input = request.getParameter("input");
			projections = dao.findProjectionByMovie(input);
						
		}
		else if(criterium.equals("price")) {
			int fromThe = Integer.parseInt(request.getParameter("fromThe"));
			int to = Integer.parseInt(request.getParameter("to"));
			projections = dao.findProjectionByPrice(fromThe, to);
			
		}else if(criterium.contentEquals("nameAndDate")) {
			String input = request.getParameter("input");
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			
			Date fromTheDate;
			System.out.println("DATE: " + request.getParameter("fromTheDate"));
			try {
				java.util.Date date = dateFormat.parse(request.getParameter("fromTheDate"));
				fromTheDate = date;
			} catch (Exception ex) {
				fromTheDate = null;
			}
			
			Date toDate;
			try {
				java.util.Date date = dateFormat.parse(request.getParameter("toDate"));
				toDate = date;
			} catch (Exception ex) {
				toDate = null;
			}
			projections = dao.findProjectionByDateAndName(input, fromTheDate, toDate);
			System.out.println("Projekcija ima: " + projections.size());
		
		}else if(criterium.contentEquals("nameAndPrice")) {
			
			String input = request.getParameter("input");
			int fromThe = Integer.parseInt(request.getParameter("fromThe"));
			int to = Integer.parseInt(request.getParameter("to"));
			
			projections = dao.findProjectionByPriceAndName(input, fromThe, to);
		
		}else if(criterium.contentEquals("dateAndPrice")) {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			
			Date fromTheDate;
			System.out.println("DATE: " + request.getParameter("fromTheDate"));
			try {
				java.util.Date date = dateFormat.parse(request.getParameter("fromTheDate"));
				fromTheDate = date;
			} catch (Exception ex) {
				fromTheDate = null;
			}
			
			Date toDate;
			try {
				java.util.Date date = dateFormat.parse(request.getParameter("toDate"));
				toDate = date;
			} catch (Exception ex) {
				toDate = null;
			}
			
			int fromThe = Integer.parseInt(request.getParameter("fromThe"));
			int to = Integer.parseInt(request.getParameter("to"));
			
			projections = dao.findProjectionByPriceAndDate(fromTheDate, toDate, fromThe, to);
		}
		
		data.put("projections", projections);
		
		request.setAttribute("data", data);
		System.out.println(data);
		
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String criterium = request.getParameter("criterium");

		ProjectionDAO dao = new ProjectionDAO();
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		
		ArrayList<Projection> projections = null;
		
		if(criterium.equals("type")) {
			int value = Integer.parseInt(request.getParameter("value"));
			projections = dao.findProjectionByType(value);
		}
		else if(criterium.contentEquals("hall")) {
			int value = Integer.parseInt(request.getParameter("value"));
			projections = dao.findProjectionByHall(value);
		
		}else if(criterium.contentEquals("dateAndTime")) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			
			Date fromTheDate;
			System.out.println("DATE: " + request.getParameter("fromTheDate"));
			try {
				java.util.Date date = dateFormat.parse(request.getParameter("fromTheDate"));
				fromTheDate = date;
			} catch (Exception ex) {
				fromTheDate = null;
			}
			
			Date toDate;
			try {
				java.util.Date date = dateFormat.parse(request.getParameter("to"));
				toDate = date;
			} catch (Exception ex) {
				toDate = null;
			}
			
			
			projections = dao.findProjectionByDate(fromTheDate, toDate);
		}
		
		data.put("projections", projections);
		
		request.setAttribute("data", data);
		System.out.println(data);
		
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);

	}

}
