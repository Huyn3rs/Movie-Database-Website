package servlets;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.*;
import javax.servlet.http.*;

import modules.JDBC;


public class BrowseServlet extends HttpServlet {
	private ArrayList<String> getGenres(Connection connection)
	{
		ArrayList<String> genres = new ArrayList<String>();
		String query = "select name from genres";
		ResultSet rs = null;
		Statement s;
		try {
			s = connection.createStatement();
			rs = s.executeQuery(query);
			
			while (rs.next())		
			{
				genres.add(rs.getString(1));
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return genres;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection)request.getSession().getAttribute("connection");
		if (connection == null)
		{	
			try {
				connection = JDBC.createConnection();
				request.getSession().setAttribute("connection", connection);
				//System.out.println("Connection created.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String genre = request.getParameter("genre");
		request.getSession().setAttribute("genre", genre);
		
		String title = request.getParameter("title");
		request.getSession().setAttribute("title", title);
		
		ArrayList<String> genres = (ArrayList<String>)request.getSession().getAttribute("genres");
		if (genres == null)
		{
			genres = getGenres(connection);
			Collections.sort(genres);
		}
		request.getSession().setAttribute("genres", genres);
		request.getRequestDispatcher("/WEB-INF/templates/browse.jsp").forward(request, response);
	}
}