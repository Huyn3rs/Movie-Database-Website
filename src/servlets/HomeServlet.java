package servlets;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

import modules.Cart;
import modules.JDBC;
import modules.Movie;

public class HomeServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String)request.getSession().getAttribute("email");
		String password = (String)request.getSession().getAttribute("password");
		Connection connection = (Connection)request.getSession().getAttribute("connection");
		if (connection == null)
		{	
			try {	         
				connection = JDBC.createConnection();
				//System.out.println("Connection created.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.getSession().setAttribute("connection", connection);
		
		int[] movies ={755009, 135012, 872002};
		ArrayList<Movie> result = new ArrayList<Movie>();
		for(int movie: movies)
		{
			String query = "select * from movies where id = ?";
			result.add(JDBC.executeMovieStatement(connection, query, Integer.toString(movie)));
		}
		request.getSession().setAttribute("result", result);
		
		if (email == null || password == null)
		{
			System.out.println("Why");
			response.sendRedirect("login");
		}
		else{
			request.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(request, response);
		}
		
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if (cart == null)
		{	
			Cart shop_cart = new Cart(); 
			System.out.println("Cart created.");
			request.getSession().setAttribute("cart", shop_cart);
		}
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		doGet(request, response);
	}
}
