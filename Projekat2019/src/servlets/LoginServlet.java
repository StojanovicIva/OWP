package servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Object loggedinUser = request.getSession().getAttribute("loggedinUser");
		
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		
		data.put("loggedinUser", loggedinUser);
		
		request.setAttribute("data", data);
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			
			if(username.equals("") || password.equals("")) {
				throw new Exception("Username or password was incorect!");
			}
			
			UserDAO dao = new UserDAO();			
			User user = dao.login(username, password);
			
			if(user == null) {
				throw new Exception("Try again! There is no user with this username or password!");
			}
			
			request.getSession().setAttribute("loggedinUser", user);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
		}catch(Exception e) {
			request.getRequestDispatcher("./FailServlet").forward(request, response);
			e.printStackTrace();
		}
	}

}
