package servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import modules.JDBC;
import modules.Movie;

public class SearchServlet extends HttpServlet {
	
	private int getNumOfParameters(HttpServletRequest request, String sort, String display, String start)
	{
		int size = request.getParameterMap().size();
		if(sort != null)
			size--;
		if(display != null)
			size--;
		
		if(start != null)
			size--;
		return size;
	}
	private String getQuickSearchQuery(String query, String type)
	{
		String result = "select m.*"
					  + " from movies as m" 
					  + " where match(m.title) against (concat(";

		if(type.equals("title"))
		{
			String[] parts = query.split(" ");
			for (String p: parts)
			{
				if (p.length() > 3 || parts.length == 1)
				{
					result += "'+', ?, '*'";
					if(p != parts[parts.length-1])
						result += ",";
				}
			}
			result += ") in boolean mode)";
			return result;
		}
		return "";
	}
	
	private String getAdvancedSearchQuery(String title, String year, String director, String star)
	{
		String result = "select m.* from movies as m where";
		String star_first = "";
		String star_last = "";
		String starQ = "";
		String and = " and ";
		
		if(!star.isEmpty())
		{
			if (star.contains(" "))
			{
				 star_first = star.split(" ")[0];
				 star_last = star.split(" ")[1];
			}
			else
			{
				star_first = star;
				star_last = star;
			}
			result = "select m.* from movies as m, stars as s, stars_in_movies as sm where";
			starQ = " (s.first_name like concat(?, '%') or s.last_name like concat(?, '%'))"
					+ " and s.id = sm.star_id and m.id = sm.movie_id";
			result += starQ;
		}
		
		if(!title.isEmpty())
		{
			if (!star.isEmpty())
				result += and;
			result += " m.title like concat('%', ?, '%')";
		}
			
		if(!year.isEmpty())
		{
			if (!title.isEmpty() || !star.isEmpty())
				result += and;
			result += " m.year = ?";
		}
		
		if(!director.isEmpty())
		{
			if(!year.isEmpty() || !title.isEmpty() || !star.isEmpty())
				result += and;
			result += " m.director like concat('%', ?, '%')";
		}
			
		if(title.isEmpty() && year.isEmpty() && director.isEmpty() && star.isEmpty())
			return "";
		//System.out.println(result);
		return result;
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
		String genre = request.getParameter("genre");
		String title = request.getParameter("title");
		
		String query = request.getParameter("q");
		String type = request.getParameter("type");
		
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		String star = request.getParameter("star");
		
		String sort = request.getParameter("sort");
		String str_display = request.getParameter("display");
		String str_start = request.getParameter("start");
		
		int start = 0; //For pagination ('offset' in sql query)
		if (str_start != null)
			start = Integer.parseInt(str_start);

		int display = 10; // number of results on each page
		if (str_display != null)
			display = Integer.parseInt(str_display);
		
		request.getSession().setAttribute("genre", genre);
		request.getSession().setAttribute("title", title);
		
		request.getSession().setAttribute("q", query);
		request.getSession().setAttribute("type", type);
		
		request.getSession().setAttribute("year", year);
		request.getSession().setAttribute("director", director);
		request.getSession().setAttribute("star", star);
		
		request.getSession().setAttribute("sort", sort);
		request.getSession().setAttribute("display", display);
		request.getSession().setAttribute("start", start);
		
		
		request.getSession().setAttribute("search", "no");
		
	   
		request.getSession().setAttribute("connection", connection);
		
		ArrayList<Movie> result = null;
		String statement = "";
		int result_size = 0;
		
		PreparedStatement searchStatement = null;
		ResultSet rs = null;
		
//		long startTimeTJ = System.nanoTime();
//		long startTimeTS = 0, endTimeTS = 0;
		
		int size = getNumOfParameters(request, sort, str_display, str_start);
		request.getSession().setAttribute("size", size);
		
		if(genre != null &&  size == 1) //SEARCH BY GENRE
		{
			statement = "select m.id, m.title, m.year, m.director, m.banner_url, m.trailer_url "
					 + "from movies as m, genres as g, genres_in_movies as gm "
				 	 + "where name = ? and g.id = gm.genre_id and m.id = gm.movie_id";
			
			statement += JDBC.addToQuery(display, sort, start);
			
			try {
				System.out.println(statement);
				searchStatement = connection.prepareStatement(statement);
				searchStatement.setString(1, genre);
				System.out.println(searchStatement);
				
//				startTimeTS = System.nanoTime();
				rs = searchStatement.executeQuery();
//				endTimeTS = System.nanoTime();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(title != null && size == 1) //SEARCH BY TITLE (Alphabetical)
		{	
			statement = "select m.* "
			 + "from movies as m "
			 + "where m.title regexp concat('^[', ?, ']')";
			
			statement += JDBC.addToQuery(display, sort, start);
			
			try {
				searchStatement = connection.prepareStatement(statement);
				searchStatement.setString(1, title);
				
//				startTimeTS = System.nanoTime();
				rs = searchStatement.executeQuery();
//				endTimeTS = System.nanoTime();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		else if (query != null && type != null &&  size == 2) //QUICK SEARCH BAR
		{
			statement = getQuickSearchQuery(query, type);
			statement += JDBC.addToQuery(display, sort, start);
			try
			{
				System.out.println(statement);
				int strings = 1;
				searchStatement = connection.prepareStatement(statement);
				String[] parts = query.split(" ");
				for (String p: parts)
				{
					if (p.length() > 3 || parts.length == 1)
					{
						searchStatement.setString(strings, p);
						strings++;
					}
				}
				System.out.println(searchStatement);
				
//				startTimeTS = System.nanoTime();
				rs = searchStatement.executeQuery();
//				endTimeTS = System.nanoTime();
				
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (size == 0)
		{
			request.getSession().setAttribute("search", "yes");
		}
		else if(title != null || year != null || director != null || star != null)
		{

			statement = getAdvancedSearchQuery(title, year, director, star);
			statement += JDBC.addToQuery(display, sort, start);
			int count = 0;
			
			try
			{
				searchStatement = connection.prepareStatement(statement);
			
				if(!star.isEmpty())
				{
					count++;
					if (star.contains(" "))
					{
						String star_first = star.split(" ")[0];
						String star_last = star.split(" ")[1];
						searchStatement.setString(count, star_first);
						count++;
						searchStatement.setString(count, star_last);
					}
					else
					{
						searchStatement.setString(count, star);
						count++;
						searchStatement.setString(count, star);
					}
				
				}
				if(!title.isEmpty())
				{
					count++;
					searchStatement.setString(count, title);
				}
				if(!year.isEmpty())
				{
					count++;
					searchStatement.setString(count, year);
				}
				if(!director.isEmpty())
				{
					count++;
					searchStatement.setString(count, director);
				}
				
//				startTimeTS = System.nanoTime();
				rs = searchStatement.executeQuery();
//				endTimeTS = System.nanoTime();
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}

		}

		
		if(rs != null)
		{
			//System.out.println(statement);
			int results = 0;
			ResultSet resSet = null;
			result = JDBC.executeSelectStatement(connection, rs);
			try {
				resSet = searchStatement.executeQuery();
				while(resSet.next())
					results++;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			result_size = results;
		}
		request.getSession().setAttribute("result", result);
		request.getSession().setAttribute("result_size", result_size);
//		long endTimeTJ = System.nanoTime();
		
		/*
		if (result != null)
		{
			System.out.println(result.size());
			for (Movie m: result)
			{
				System.out.println(m);
				for (Star a: m.actors)
				{
					System.out.println("\t" + a);
				}
				for (String s: m.genres)
				{
					System.out.println("Genre: " +  s);
				}
				
			}
		}*/

//		JDBC.WritetoPerformanceFile(startTimeTJ, endTimeTJ, startTimeTS, endTimeTS);
		request.getRequestDispatcher("/WEB-INF/templates/search.jsp").forward(request, response);
	}
}

