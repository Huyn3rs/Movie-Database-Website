package servlets;
import java.io.*;
import java.sql.Connection;
import javax.servlet.*;
import javax.servlet.http.*;

import modules.Cart;
import modules.JDBC;


public class CartServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String id = request.getParameter("id");
//		request.getSession().setAttribute("id", id);
//		
//		
		request.getRequestDispatcher("/WEB-INF/templates/cart.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection connection = (Connection)request.getSession().getAttribute("connection");
		if (connection == null)
		{	
			try {
				connection = JDBC.createConnection();
				//System.out.println("Connection created.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("id", id);
		
		String quantity = request.getParameter("quantity");
		request.getSession().setAttribute("quantity", quantity);
		
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		System.out.println(id);
		cart.add(Integer.parseInt(id), quantity, connection);
		
		request.getSession().setAttribute("cart", cart);
		
		response.sendRedirect("cart");
		//request.getRequestDispatcher("/WEB-INF/templates/cart.jsp").forward(request, response);
	}

}