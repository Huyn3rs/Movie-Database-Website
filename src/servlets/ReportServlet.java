package servlets;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class ReportServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/templates/reports.jsp").forward(request, response);
	}
}