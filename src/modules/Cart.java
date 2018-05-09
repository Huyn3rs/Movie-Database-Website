package modules;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cart {
	public ArrayList<Movie> movies;
	
	public Cart()
	{
		movies = new ArrayList<Movie>();
	}
	
	public Movie retrieveMovie(int id, Connection connection)
	{
		Movie movie = null;
		
		ResultSet rs = null;
		Statement s;
		String query = "select * from movies where id = " + id;
		
		try {
			s = connection.createStatement();
			rs = s.executeQuery(query);
			
			rs.next();
			
			int mid = rs.getInt("id");
			String title = rs.getString("title");
			int year = rs.getInt("year");
			String director = rs.getString("director");
			String banner_url = rs.getString("banner_url");
			String trailer_url = rs.getString("trailer_url");
			movie = new Movie(mid, title, year, director, banner_url, trailer_url);
			movie.getGenres(connection);
			movie.getActors(connection);
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}
	
	
	public void add(int id, String quantity, Connection connection)
	{
		Movie movie = checkCart(id);
		
		if (movie == null)
		{
			Movie newMovie = retrieveMovie(id, connection);
			movies.add(newMovie);
		}
		else if(quantity.equals("new"))
		{
			movie.setCount(movie.getCount() + 1);
		}
		else 
		{
			int count = Integer.parseInt(quantity);	
			if(count == 0)
				movies.remove(movie);
			else
				movie.setCount(count);
		}
	}
	
	public Movie checkCart(int id)
	{
		for (int i = 0; i < movies.size(); i++)
		{
			 if (movies.get(i).getId() == id)
			 {
				 return movies.get(i);
			 }
		}
		return null;
	}
	
	public ArrayList<Movie> getCart()
	{
		return movies;
	}
	
	public int getSize()
	{
		int count = 0;
		for (Movie m: movies)
			count += m.getCount();
		return count;
	}
	
	public double getTotal()
	{
		double count = 0;
		for (Movie m: movies)
			count += m.getTotalPrice();
		return count;
	}
	
	public String getTotalString()
	{
		double count = 0;
		for (Movie m: movies)
			count += m.getTotalPrice();
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(count);
	}
	
	public ArrayList<Integer> getMovieIDs()
	{
		 ArrayList<Integer> ids = new ArrayList<Integer>();
		 for(Movie m: movies)
			 ids.add(m.getId());
		 return ids;
	}
	
	
	
	

}
