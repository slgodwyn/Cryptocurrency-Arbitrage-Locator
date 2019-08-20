package api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = "/api")
public class API_Request extends HttpServlet {
	private static final long serialVersionUID = 1L;

  
    public API_Request() {
    	
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append(JsonOutput.getArbiJson());
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		response.getWriter().append(JsonOutput.getArbiJson()).append(request.getContextPath());
	}

}
