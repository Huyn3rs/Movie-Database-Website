package modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JDBC {
	
	private static String setLimit(int display)
	{
		return " limit " + display;
	}
	
	private static String setSort(String sort)
	{
		if (sort == null)
			return " order by m.id";
		switch(sort)
		{
		case "title-asc":
			return " order by m.title";
		case "title-desc":
			return " order by m.title desc";
		case "year-asc":
			return " order by m.year";
		case "year-desc":
			return " order by m.year desc";
		default:
			return " order by m.id";
		}
	}
	
	private static String setStart(int start)
	{
		return " offset " + start;
	}
	
	public static Connection createConnection() throws Exception
	{	
//		Class.forName("com.mysql.jdbc.Driver").newInstance();
//		return DriverManager.getConnection("jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false","mytestuser", "mypassword");
		Connection dbcon = null;
		
		Context initCtx = new InitialContext();
		 if (initCtx == null)
			 System.out.println("initCtx is NULL");

        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        if (envCtx == null)
            System.out.println("envCtx is NULL");
    
        DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
        if (ds == null)
        	System.out.println("ds is null.");
        else
        	System.out.println("Found data source");
        
        try{
        	dbcon = ds.getConnection(); //something wrong here
        	if (dbcon == null)
        		System.out.println("dbcon is null.");
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        catch(Exception e)
        {
        	System.out.println("Something else wrong");
        }
        
        return dbcon;
        
	}
	
	public static Connection createInsertConnection() throws Exception
	{	
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		return DriverManager.getConnection("jdbc:mysql://54.67.118.82:3306/moviedb?autoReconnect=true&useSSL=false","mytestuser", "mypassword");
	}
	
	public static int sizeOfResults(Connection connection, ResultSet rs)
	{
		int count = 0;
//		Statement s = null;
//		ResultSet rs = null;
		try {
//			s = connection.createStatement();
////			System.out.println(query);
//			rs = s.executeQuery(query);
			while (rs.next()) {
				count++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static String addToQuery(int display, String sort, int start)
	{
		String ret = "";
		ret += setSort(sort);
		ret += setLimit(display);
		ret += setStart(start);
		return ret;
	}
	
	public static ArrayList<Movie> executeSelectStatement(Connection connection, ResultSet rs){
		ArrayList<Movie> result = new ArrayList<Movie>();
//		Statement s = null;
//		ResultSet rs = null;
		try {
//			query += setSort(sort);
//			query += setLimit(display);
//			query += setStart(start);
//			//System.out.println(query);
//			s = connection.createStatement();
//			rs = s.executeQuery(query);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				int year = rs.getInt("year");
				String director = rs.getString("director");
				String banner_url = rs.getString("banner_url");
				String trailer_url = rs.getString("trailer_url");
				Movie movie = new Movie(id, title, year, director, banner_url, trailer_url);
				movie.getGenres(connection);
				movie.getActors(connection);
				result.add(movie);
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return result;
	}
	
	public static Star executeStarStatement(Connection connection, String query, String starID){
		Star star = null;
			
		ResultSet rs = null;
		PreparedStatement s =  null;
		try {
			s = connection.prepareStatement(query);
			s.setString(1, starID);
			rs = s.executeQuery();
			
			rs.next();
		
			int id = rs.getInt("id");
			String first_name = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			String dob = rs.getString("dob");
			String photo_url = rs.getString("photo_url");
			star = new Star(id, first_name, last_name, dob, photo_url);
			star.getMovies(connection);


		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		return star;
		
		
		
	}
	public static Movie executeMovieStatement(Connection connection, String query, String movieID){
		Movie movie = null;
			
		ResultSet rs = null;
		PreparedStatement s = null;
		try {
			s = connection.prepareStatement(query);
			s.setString(1, movieID);
			rs = s.executeQuery();
			
			rs.next();
			
			int id = rs.getInt("id");
			String title = rs.getString("title");
			int year = rs.getInt("year");
			String director = rs.getString("director");
			String banner_url = rs.getString("banner_url");
			String trailer_url = rs.getString("trailer_url");
			movie = new Movie(id, title, year, director, banner_url, trailer_url);
			movie.getGenres(connection);
			movie.getActors(connection);

		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return movie;
		
		
	}
	
	public static void WritetoPerformanceFile(long ts1, long ts2, long tj1, long tj2)
	{
		BufferedWriter out = null;
		FileWriter fstream = null;
		File file = new File("performance.txt");
		if (!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				fstream = new FileWriter(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
		{
			try {
				fstream = new FileWriter(file, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
			
		try{
			out = new BufferedWriter(fstream);
			
			
			out.write("TS: " + (ts2 - ts1) + " TJ: " + (tj2 - tj1) + "\n");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		if (out != null)
		{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	
	
}
