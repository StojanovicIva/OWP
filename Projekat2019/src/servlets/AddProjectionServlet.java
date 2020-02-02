package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HallDAO;
import dao.MovieDAO;
import dao.ProjectTypeDAO;
import dao.ProjectionDAO;
import dao.UserDAO;
import model.Hall;
import model.Movie;
import model.ProjectType;
import model.Projection;
import model.User;

public class AddProjectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Integer movieId = Integer.parseInt(request.getParameter("movieId"));
			Integer projectionType = Integer.parseInt(request.getParameter("projectionType"));
			Integer hallId = Integer.parseInt(request.getParameter("hall"));
			Integer price = Integer.parseInt(request.getParameter("price"));
			Integer adminsName = Integer.parseInt(request.getParameter("adminsName"));
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			
			Date dateAndTime;
			System.out.println("DATE: " + request.getParameter("fromTheDate"));
			try {
				java.util.Date date = dateFormat.parse(request.getParameter("dateAndTime"));
				dateAndTime = date;
			} catch (Exception ex) {
				dateAndTime = null;
			}
			
			MovieDAO movieDao = new MovieDAO();
			Movie movie = movieDao.findMovieById(movieId);
			
			ProjectTypeDAO ptDao = new ProjectTypeDAO();
			ProjectType pt = ptDao.findProjectTypeById(projectionType);
			
			HallDAO hallDao = new HallDAO();
			Hall hall = hallDao.findHallById(hallId);
			
			UserDAO dao = new UserDAO();
			User user = dao.findUserById(adminsName);
			
			Projection projection = new Projection(movie, pt, hall, dateAndTime, price, user);
			
			System.out.println("Servlet PROJEKCIJA: " + projection);
			ProjectionDAO pDao = new ProjectionDAO();
			pDao.addProjection(projection);
			
			System.out.println("Dodao u dao");
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	}


