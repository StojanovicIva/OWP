package status;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SuccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> data = (Map<String, Object>) request.getAttribute("data");
		if (data == null)
			data = new LinkedHashMap();

		data.put("status", "success");

		ObjectMapper objectMapper = new ObjectMapper();

		String jsonPodaci = objectMapper.writeValueAsString(data);
		System.out.println(jsonPodaci);

		response.setContentType("application/json");
		response.getWriter().write(jsonPodaci);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
