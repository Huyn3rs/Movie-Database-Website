package modules;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Star {
	public int id;
	public String first_name;
	public String last_name;
	public String dob;
	public String photo_url;
	public ArrayList<Movie> movies;
	
	public Star(int id, String first_name, String last_name, String dob, String photo_url)
	{
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.dob = dob;
		this.photo_url = photo_url;
		this.movies = new ArrayList<Movie>();
	}
	public Star()
	{
		
	}
	
	public void getMovies(Connection connection)
	{
		String query = "select * "
	 		 	 + "from movies "
		 	     + "join stars_in_movies on movies.id = stars_in_movies.movie_id "
		 	     + "where stars_in_movies.star_id = '" + this.id + "'";
		
		//System.out.println(query);
		
		Statement s = null;
		ResultSet rs = null;
		try {
			query += " limit 10";
			s = connection.createStatement();
			rs = s.executeQuery(query);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				int year = rs.getInt("year");
				String director = rs.getString("director");
				String banner_url = rs.getString("banner_url");
				String trailer_url = rs.getString("trailer_url");
				Movie movie = new Movie(id, title, year, director, banner_url, trailer_url);
				movie.getGenres(connection);
//				movie.getActors(connection);
				movies.add(movie);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public String toString()
	{

		String str = "id = %d; first_name = %s; last_name = %s; dob = %s; photo_url = %s";
		
		return String.format(str, id, first_name, last_name, dob, photo_url);
		
	}
	
	public int getId()
	{
		return id;
	}
	public String getFirst_name()
	{
		return first_name;
	}
	public String getLast_name()
	{
		return last_name;
	}
	public String getDob()
	{
		return dob;
	}
	public String getPhoto_url()
	{
		return photo_url;
	}
	public ArrayList<Movie> getListOfMovies()
	{
		return movies;
	}
	
	public void setFirstName(String name)
	{
		this.first_name = name;
	}
	public void setLastName(String name)
	{
		this.last_name = name;
	}
	public void setDob(String dob)
	{
		this.dob = dob;
	}
}

