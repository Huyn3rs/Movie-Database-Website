package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import modules.Cart;

public class ConfirmedServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = (String)request.getSession().getAttribute("firstName");
		String lastName = (String)request.getSession().getAttribute("lastName");
		String creditCard = (String)request.getSession().getAttribute("creditCard");
		String expDate = (String)request.getSession().getAttribute("expDate");
		
		if (firstName == null || lastName == null || creditCard == null || expDate == null)
		{
			response.sendRedirect("checkout");
		}
		else{
			request.getSession().setAttribute("cart", new Cart());
			request.getRequestDispatcher("/WEB-INF/templates/confirmed.jsp").forward(request, response);
		}
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		doGet(request, response);
	}
}