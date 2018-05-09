package modules;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public class Movie {
	public int id;
	public String title;
	public int year;
	public String director;
	public String banner_url;
	public String trailer_url;
	public ArrayList<Star> actors;
	public ArrayList<String> genres;
	private int count;
	private double price;
	
	
	public Movie(int id, String title, int year, String director, String banner_url, String trailer_url)
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.banner_url = banner_url;
		this.trailer_url = trailer_url;
		this.actors = new ArrayList<Star>();
		this.genres = new ArrayList<String>();
		this.count = 1;
		this.price = 14.99;
	}
	public Movie()
	{
		this.genres = new ArrayList<String>();
	}
	
	public void getGenres(Connection connection)
	{
		String query = "select name "
			 		 + "from genres "
		 			 + "join genres_in_movies on genres.id = genres_in_movies.genre_id "
		 			 + "where movie_id = " + id;
		//System.out.println(query);
		
		Statement s = null;
		ResultSet rs = null;
		try {
			s = connection.createStatement();
			rs = s.executeQuery(query);
			
			while (rs.next()) {
				String genre = rs.getString(1);
				genres.add(genre);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getActors(Connection connection)
	{
		String query = "select * "
		 		 	 + "from stars "
	 		 	     + "join stars_in_movies on stars.id = stars_in_movies.star_id "
	 		 	     + "where stars_in_movies.movie_id = '" + this.id + "'";
//		System.out.println(query);
	
		Statement s = null;
		ResultSet rs = null;
		try {
			s = connection.createStatement();
			rs = s.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String dob = rs.getString("dob");
				String photo_url = rs.getString("photo_url");
				Star actor = new Star(id, first_name, last_name, dob, photo_url);
				actors.add(actor);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString()
	{
		String str = "id = %d; title = %s; year = %d; director = %s; banner_url = %s; trailer_url = %s genres = ";
		for (String g: this.genres)
			str += g + ", ";
		
		return String.format(str, id, title, year, director, banner_url, trailer_url);
	}
	public void setCount(int c)
	{
		count = c;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public int getId()
	{
		return id;
	}
	public double getPrice()
	{
		return price;
	}
	public double getTotalPrice()
	{
		return price * count;
	}
	public String getTotalPriceString()
	{
		double total = price * count;
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(total);
	}
	public String getTitle()
	{
		return title;
	}
	public int getYear()
	{
		return year;
	}
	public String getDirector()
	{
		return director;
	}
	public String getBanner_url()
	{
		return banner_url;
	}
	public String getTrailer_url()
	{
		return trailer_url;
	}
	public ArrayList<Star> getListOfActors()
	{
		return actors;
	}
	
	public ArrayList<String> getListOfGenres()
	{
		return genres;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public void setYear(int year){
		this.year = year;
	}
	public void setDirector(String director){
		this.director = director;
	}
	public void setGenres(ArrayList<String> genres)
	{
		this.genres = genres;
	}
}
