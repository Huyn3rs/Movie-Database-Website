package servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;


import modules.JDBC;
import modules.Movie;
import modules.Star;

public class AutoPopUpServlet extends HttpServlet
{
	private String getMovieInfo(String id)
	{
		String query = "select * from movies where id = " + id;
		return query;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Connection connection = (Connection)request.getSession().getAttribute("connection");
		if (connection == null)
		{	
			try {
				connection = JDBC.createConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Statement s = null;
		ResultSet rs = null;
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		
		try
		{
			String q = getMovieInfo(id);
			s = connection.createStatement();
			rs = s.executeQuery(q);
			rs.next();
			int movieID = rs.getInt("id");			
			String title = rs.getString("title");
			int year = rs.getInt("year");
			String director = rs.getString("director");
			String banner_url = rs.getString("banner_url");
			String trailer_url = rs.getString("trailer_url");
			Movie m  = new Movie(movieID, title, year, director, banner_url, trailer_url);
			m.getGenres(connection);
			m.getActors(connection);

			
			String output = "<a class=\"movie__trailer\" href=\"" + trailer_url + "\">Trailer &nbsp;&nbsp;</a>" +
			"<p class=\"movie__year\">(" + year + ")</p>" +
			"<p class=\"movie__director\">Director: " + director + "</p>" +
			"<div class=\"movie__genres\">" +
				"<span> Genres: </span>";
			
			for(String genre: m.getListOfGenres())
			{
				output += "<a href=\"" + request.getContextPath() + "/search?genre=" + genre + "\">" + genre + "&nbsp;&nbsp;</a>";
			}
		
			output += "</div> <div class=\"movie__actors\"> <span> Actors: </span>";
			
			for(Star actor: m.getListOfActors())
			{
				output += "<a href=\"" + request.getContextPath() + "/view?star=" + actor.id + "\">" + actor.getFirst_name() + " " + actor.getLast_name() + "&nbsp;</a>";
			}
			
			output += "</div><div class=\"fabflix__add-to-cart\">" +
					  "<p class=\"fabflix__price\">" + m.getPrice() + "</p>" +
					  "<form id=\"add-to-cart\" action=\"" + request.getContextPath() + "/cart\"" + " method=\"post\">" +
					  "<input type=\"hidden\" name=\"id\" value=" + id + ">" +
					  "<input type=\"hidden\" name=\"quantity\" value=\"new\">" +
					  "<button type=\"submit\" class=\"btn btn-primary\">Add to Cart</button>" +
					  "</form></div>";
			//System.out.println(output);
			out.println(output);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}