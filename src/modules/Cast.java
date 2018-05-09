package modules;

public class Cast{
	private String movieTitle;
	private String Director;
	private String starFirstName;
	private String starLastName;
	
	public Cast()
	{
		
	}
	
	public void setMovieTitle(String title)
	{
		this.movieTitle = title;
	}
	public void setDirector(String d)
	{
		this.Director = d;
	}
	
	public void setFirstName(String first)
	{
		this.starFirstName = first;
	}
	
	public void setLastName(String last)
	{
		this.starLastName = last;
	}

	public String getMovieTitle()
	{
		return movieTitle;
	}
	
	public String getFirstName()
	{
		return starFirstName;
	}
	
	public String getLastName()
	{
		return starLastName;
	}
	public String getDirector()
	{
		return Director;
	}
	@Override
	public String toString()
	{

		String str = "first_name = %s; last_name = %s; movie_title = %s";
		
		return String.format(str, starFirstName, starLastName, movieTitle);
		
	}

}


