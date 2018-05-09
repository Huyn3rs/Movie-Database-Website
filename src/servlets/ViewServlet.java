package servlets;

import java.io.*;
import java.sql.Connection;

import javax.servlet.*;
import javax.servlet.http.*;

import modules.JDBC;
import modules.Movie;
import modules.Star;

public class ViewServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection)request.getSession().getAttribute("connection");
		if (connection == null)
		{	
			try {
				connection = JDBC.createConnection();
				System.out.println("Connection created.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String movie = request.getParameter("movie");
		request.getSession().setAttribute("movieid", movie);
		
		String star = request.getParameter("star");
		request.getSession().setAttribute("starid", star);
		
		Movie movie_info = null;
		Star star_info = null;
		
		int size = request.getParameterMap().size();
		
		if(movie != null && size == 1)
		{	
			String statement = "select * from movies where id = ?";
			movie_info = JDBC.executeMovieStatement(connection, statement, movie);
//			System.out.println(movie_info);
		}

		else if(star != null && size == 1)
		{
			String statement = "select * from stars where id = ?";
			star_info = JDBC.executeStarStatement(connection, statement, star);
//			System.out.println(star_info);
		}
		
		request.getSession().setAttribute("movie", movie_info);
		request.getSession().setAttribute("actor", star_info);
		
		request.getRequestDispatcher("/WEB-INF/templates/view.jsp").forward(request, response);
		
	}
	
	
}