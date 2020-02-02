package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String user = request.getParameter("user");
			Integer id = Integer.parseInt(request.getParameter("id"));
			
			UserDAO dao = new UserDAO();			
			
			if(user.equals("user")) {
				String pass = request.getParameter("pass");
				System.out.println("pass: " + pass);
				dao.updatePass(id, pass);
			
			}else if(user.equals("admin")) {
				String role = request.getParameter("role");
				
				System.out.println("role: " + role);
				dao.updateRole(id, role);
			}
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
