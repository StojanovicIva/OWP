package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;


public class SearchUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
		System.out.println(username);
		UserDAO userDao = new UserDAO();
		
		ArrayList<User> users = userDao.findUser(username);
		System.out.println(users);
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		
		data.put("users", users);
		
		request.setAttribute("data", data);
		System.out.println(data);
		
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String value = request.getParameter("value");
		
		UserDAO userDao = new UserDAO();
		System.out.println("value: " + value);
		
		ArrayList<User> users = userDao.findUserByRole(value);
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		
		data.put("users", users);
		
		request.setAttribute("data", data);
		System.out.println(data);
		
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
	}

}
