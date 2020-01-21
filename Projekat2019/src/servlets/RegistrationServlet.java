package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.Role;
import model.User;

public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String repeatedPassword = request.getParameter("repeatedPassword");
			
			if(UserDAO.findUserByUserName(username) != null) {
				throw new Exception("This username already exists!");
			}
			if("".equals(username)) {
				throw new Exception("The username is blank!");
			}
			if("".equals(password) || "".equals(repeatedPassword)) {
				throw new Exception("The password is empty!");
			}
			if(!password.equals(repeatedPassword)) {
				throw new Exception("Passwords don't match!");
			}
			
			User user = new User(username, password );
			
			UserDAO dao = new UserDAO();
			dao.addUser(user);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch(Exception e) {
			request.getRequestDispatcher("./FailServlet").forward(request, response);

			e.printStackTrace();
		}
		
	}

}
