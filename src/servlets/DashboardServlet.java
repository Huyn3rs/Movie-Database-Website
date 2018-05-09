package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import modules.JDBC;
import modules.Star;

public class DashboardServlet extends HttpServlet {
	
	private String getStarString(String firstname, String lastname, String dob, String photo)
	{
		if(!firstname.isEmpty() && lastname.isEmpty())
		{
			lastname = firstname;
			firstname = "";
		}
		
		String command = "insert into stars(first_name, last_name, dob, photo_url)" +
							" values('" + firstname + "', '" + lastname + "', '" + dob +
							"', '" + photo + "')";
		
		System.out.println(command);
		return command;
	}
	
	private HashMap<String, HashMap<String, String>> getMetadata(Connection connection)
	{
		HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
		
		Statement select;
		String query = "select table_name from information_schema.tables where table_schema='moviedb';";
		try{
			select = connection.createStatement(); 
			ResultSet result = select.executeQuery(query);
			while(result.next())
			{
				data.put(result.getString(1), getTableData(connection, result.getString(1)));
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Retrieving Metadata failed.");
		}
		return data;
	}
	
	private HashMap<String, String> getTableData(Connection connection, String table_str)
	{
		HashMap<String, String> table = new HashMap<String, String>();
		
		try{
			Statement select = connection.createStatement(); 
			ResultSet result = select.executeQuery("show fields from " + table_str);
			while(result.next())
	        {
	            table.put(result.getString(1), result.getString(2));
	        }
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Retrieving Metadata failed.");
		}
        return table;
	}
	
	private String getMovieString(String title, String year, String director, String bannerURL, String trailerURL)
	{
		String command = "select add_movie('%s', %s, '%s', '%s', '%s')";
		String result = String.format(command, title, year, director, bannerURL, trailerURL);
		System.out.println(result);
		return result;
	}
	
	private String getMovie(Connection connection, String title, String year, String director)
	{
		String q = "SELECT m.id from movies as m where m.title = '%s' and m.year = %s and m.director = '%s'";
		String query = String.format(q, title, year, director);
		ResultSet rs = null;
		Statement s;
		try {
			s = connection.createStatement();
			rs = s.executeQuery(query);
			
			rs.next();
			String result = rs.getString(1);
			return result;		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	private String insertStar(Connection connection, String movie, String star)
	{
		ResultSet rs = null;
		Statement s;
		String q = "select add_star(%s, %s)";
		String query = String.format(q, movie, star);
		System.out.println(query);
		try {
			s = connection.createStatement();
			rs = s.executeQuery(query);
			
			rs.next();
			String result = rs.getString(1);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	private String insertGenre(Connection connection, String movie, String genre)
	{
		ResultSet rs = null;
		Statement s;
		String q = "select add_genre(%s, %s)";
		String query = String.format(q, movie, genre);
		System.out.println(query);
		try {
			s = connection.createStatement();
			rs = s.executeQuery(query);
			
			rs.next();
			String result = rs.getString(1);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection connection = (Connection)request.getSession().getAttribute("connection");
		if (connection == null)
		{	
			try {
				connection = JDBC.createConnection();
//				connection = JDBC.createInsertConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String email = (String)request.getSession().getAttribute("eemail");
		String password = (String)request.getSession().getAttribute("epassword");
		if (email == null || password == null)
		{
			response.sendRedirect("dashboard/login"); 
		}
		else{
			HashMap<String, HashMap<String, String>> data = (HashMap<String, HashMap<String, String>>)request.getSession().getAttribute("data");
			if (data == null)
			{
				data = getMetadata(connection);
			}
			request.getSession().setAttribute("data", data);
			request.getRequestDispatcher("/WEB-INF/templates/dashboard.jsp").forward(request, response);
			
			request.getSession().setAttribute("insert", null);
	        request.getSession().setAttribute("success", "no");
	        request.getSession().setAttribute("star_success", null);
	        request.getSession().setAttribute("genre_success", null);
	        request.getSession().setAttribute("metadata", "no"); 
		}
		
    }
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
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
		
		//to add star
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String dob = request.getParameter("dob");
		String photo = request.getParameter("photo");
				
		//to print metadata
		request.getSession().setAttribute("metadata", "no");
		
		//to add movie / add into movie
		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		String banner = request.getParameter("banner_url");
		String trailer = request.getParameter("trailer_url");
		String starID = request.getParameter("star");
		String genreID = request.getParameter("genre");
		
		request.getSession().setAttribute("insert", null);
		request.getSession().setAttribute("success", "no");
		String statement = "";

//		request.getSession().setAttribute("firstname", firstname);
//		request.getSession().setAttribute("lastname", lastname);
//		request.getSession().setAttribute("dob", dob);
//		request.getSession().setAttribute("photo", photo);
//		
//		request.getSession().setAttribute("title", title);
//		request.getSession().setAttribute("year", year);
//		request.getSession().setAttribute("director", director);
//		request.getSession().setAttribute("banner", banner);
//		request.getSession().setAttribute("trailer", trailer);
//		request.getSession().setAttribute("starID", starID);
//		request.getSession().setAttribute("genreID", genreID);
		
		if(firstname != null && title == null)
		{
			request.getSession().setAttribute("insert", "star");
			statement = getStarString(firstname, lastname, dob, photo);
			if(statement != "")
			{
				try
				{
					Statement s = connection.createStatement();
					s.executeUpdate(statement);
					request.getSession().setAttribute("success", "yes");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					request.getSession().setAttribute("success", "no");
				}
			}
		}
		
		else if(title != null && firstname == null)
		{
			request.getSession().setAttribute("insert", "movie");
			String query = getMovieString(title, year, director, banner, trailer);
			ResultSet rs = null;
			Statement s;
			try {
				s = connection.createStatement();
				rs = s.executeQuery(query);
				
				rs.next();
				String result = rs.getString(1);
				
				System.out.println(result);
			
				request.getSession().setAttribute("success", result);
			


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String movie = getMovie(connection, title, year, director);
			System.out.println(movie);
			String star = insertStar(connection, movie, starID);
			System.out.println(star);
			String genre = insertGenre(connection, movie, genreID);
			System.out.println(genre);

			request.getSession().setAttribute("star_success", star);
			request.getSession().setAttribute("genre_success", genre);
		
		}
		

		response.sendRedirect("dashboard");
		
	}
}
