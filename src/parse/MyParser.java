package parse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import org.xml.sax.helpers.DefaultHandler;
import modules.Movie;
import modules.Star;
import modules.Cast;

public class MyParser extends DefaultHandler {
	private final int BATCH_SIZE = 1000;
	boolean XMLmovies = false;
	boolean XMLactors = false;
	boolean XMLcasts = false;
    List<Movie> movies;
    List<Star> actors;
    List<Cast> casts;
    Set<String> genres;
    private int m, a, c, g;
    private String tempVal;
    private String director_name = "";

    //to maintain context
    private Movie movie;
    private Star actor;
    private Cast cast;
    Connection connection = null;

    public MyParser() {
        movies = new ArrayList<Movie>();
        actors = new ArrayList<Star>();
        casts = new ArrayList<Cast>();
        genres = new HashSet<String>();
        
        try{
        	connection = createConnection();
        } catch(Exception e){
        	e.printStackTrace();
        }
    }
    public static Connection createConnection() throws Exception
	{	
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		return DriverManager.getConnection("jdbc:mysql:///moviedbtest?autoReconnect=true&useSSL=false&useServerPrepStmts=false&rewriteBatchedStatements=true","mytestuser", "mypassword");
	}

    public void run() {
        parseDocument();
    }

    private void parseDocument() {

        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();
            
            sp.parse("WebContent/resources/mains243.xml", this);
            printData();
            sp.reset();

            sp.parse("WebContent/resources/actors63.xml", this);
            printData();
            sp.reset();
            
          
            sp.parse("WebContent/resources/casts124.xml", this);
            printData();
            sp.reset();

           

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Iterate through the list and print
     * the contents
     */
    private void printData() {
    	
    	if(XMLmovies)
    	{
    		Iterator<Movie> it = movies.iterator();
    		while (it.hasNext()) {
    			System.out.println(it.next().toString());
    		}
    		System.out.println("No of Movies'" + m + "'.");
    		System.out.println("No of Genres'" + g + "'.");
    		XMLmovies = false;
        }
    	else if(XMLactors)
    	{
    		Iterator<Star> it = actors.iterator();
    		while (it.hasNext()) {
    			System.out.println(it.next().toString());
    		}
    		System.out.println("No of Actors'" + a + "'.");
    		XMLactors = false;
    	}
    	else if(XMLcasts)
    	{
    		Iterator<Cast> it = casts.iterator();
    		while (it.hasNext()) {
    			System.out.println(it.next().toString());
    		}
    		System.out.println("No of Casts'" + c + "'.");
    		XMLcasts = false;
    	}
    }
 
    //Event Handlers
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //reset
        tempVal = "";
        if (qName.equalsIgnoreCase("movies")){
        	XMLmovies = true;
        } else if (qName.equalsIgnoreCase("actors")){
        	XMLactors = true;
        } else if (qName.equalsIgnoreCase("casts")){
        	XMLcasts = true;
        }
        
        if (XMLmovies)
    		startElementMovie(uri, localName, qName, attributes);
    	else if(XMLactors)
    		startElementActor(uri, localName, qName, attributes);
    	else if (XMLcasts)
    		startElementCast(uri, localName, qName, attributes);
    }
    public void startElementMovie(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	if (qName.equalsIgnoreCase("directorfilms")) {
        }
        else if (qName.equalsIgnoreCase("film")){
            movie = new Movie();
        }
    }
    public void startElementActor(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	if (qName.equalsIgnoreCase("actors")) {
        }
    	if(qName.equalsIgnoreCase("actor"))
    	{
    		actor = new Star();
    	}
    }
    public void startElementCast(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	if(qName.equalsIgnoreCase("m"))
    	{
    		cast = new Cast();
    	}
    }
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException 
    {
//		if (qName.equalsIgnoreCase("movies")){
//			XMLmovies = false;
//		} else if (qName.equalsIgnoreCase("actors")){
//			XMLactors = false;
//		} else if (qName.equalsIgnoreCase("casts")){
//		 	XMLcasts = false;
//		}
//    	 
    	if (XMLmovies)
    		endElementMovie(uri, localName, qName);
    	else if(XMLactors)
    		endElementActor(uri, localName, qName);
    	else if (XMLcasts)
    		endElementCast(uri, localName, qName);
    }
    
    private void endElementMovie(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equalsIgnoreCase("dirname")) {
            director_name = tempVal;

        } else if (qName.equalsIgnoreCase("t")) {
            movie.setTitle(tempVal);
        } else if (qName.equalsIgnoreCase("year")) {
        	try{
        	movie.setYear(Integer.parseInt(tempVal)); 
        	}
        	catch (NumberFormatException e)
        	{
        		movie.setYear(1900);
        	}
        } 
        else if(qName.equalsIgnoreCase("cat"))
        {
        	movie.getListOfGenres().add(tempVal);
        	genres.add(tempVal);
        }
        else if (qName.equalsIgnoreCase("film")){
            movie.setDirector(director_name);
            movies.add(movie);
            if(movies.size() == BATCH_SIZE){
            	MovieBatchInsert();
            	GenreBatchInsert();
            	GenreInMoviesBatchInsert();
            	m += movies.size();
            	g += genres.size();
            	System.out.println("Movies: " + m);
            	System.out.println("Genres: " + g);
            	movies.clear();
            	genres.clear();
            }
        }
        else if (qName.equalsIgnoreCase("movies")) {
        	MovieBatchInsert();
        	GenreBatchInsert();
        	GenreInMoviesBatchInsert();
        }
        
    }
    
    private void endElementActor(String uri, String localName, String qName) throws SAXException
    {
    	String[] names;
    	if(qName.equalsIgnoreCase("stagename"))
    	{
    		names = tempVal.split(" ");
    		if(names.length == 1)
    		{
    			actor.setFirstName("");
    			actor.setLastName(names[0]);
    		}
    		else if(names.length == 2)
    		{
    			actor.setFirstName(names[0]);
    			actor.setLastName(names[1]);
    		}
    		else if(names.length == 3)
    		{
    			actor.setFirstName(names[0] + " " + names[1]);
    			actor.setLastName(names[2]);
    		}
    		else
    		{
    			actor.setFirstName(names[0]);
    			actor.setLastName(names[names.length-1]);
    		}
    		
    	}
    	else if(qName.equalsIgnoreCase("dob"))
    	{
 
    		try{
            	
            	if(tempVal.length() == 4)
        		{
            		int Temp = Integer.parseInt(tempVal);
        			tempVal +="-01-01";
        			actor.setDob(tempVal);
        		}
            	else{
            		actor.setDob("1000-01-01");
            	}
        		
            }
            catch (NumberFormatException e)
            {
            	actor.setDob("1000-01-01");
            }
    		
    			
       	}
    	else if(qName.equalsIgnoreCase("actor"))
    	{
//    		System.out.println(actor.toString());
    		actors.add(actor);
    		if (actors.size() == BATCH_SIZE)
    		{
    			ActorBatchInsert();
    			a += actors.size();
    			System.out.println("Actors: " + a);
    			actors.clear();
    		}
    	}
    	
    	else if (qName.equalsIgnoreCase("actors"))
    	{
			ActorBatchInsert();
    		
    	}
    	
    }
    
    private void endElementCast(String uri, String localName, String qName) throws SAXException
    {
    	String[] names;
    	if(qName.equalsIgnoreCase("t"))
    	{
    		cast.setMovieTitle(tempVal);
    	}
    	else if(qName.equalsIgnoreCase("is"))
    	{
    		director_name = tempVal;
    	}
    	else if(qName.equalsIgnoreCase("a"))
    	{
    		names = tempVal.split(" ");
    		if(names.length == 1)
    		{
    			cast.setFirstName("");
    			cast.setLastName(names[0]);
    		}
    		else if(names.length == 2)
    		{
    			cast.setFirstName(names[0]);
    			cast.setLastName(names[1]);
    		}
    		else if(names.length == 3)
    		{
    			cast.setFirstName(names[0] + " " + names[1]);
    			cast.setLastName(names[2]);
    		}
    		else
    		{
    			cast.setFirstName(names[0]);
    			cast.setLastName(names[names.length-1]);
    		}
    	}
    	else if(qName.equalsIgnoreCase("m"))
    	{
    		//System.out.println(cast.toString());
    		cast.setDirector(director_name);
    		casts.add(cast);
    		if (casts.size() == BATCH_SIZE)
    		{
    			StarsInMoviesBatchInsert();
    			c += casts.size();
    			System.out.println("Casts: " + c);
    			casts.clear();
    		}
    	}
    	else if(qName.equalsIgnoreCase("filmc"))
    	{
    	}
    	else if(qName.equalsIgnoreCase("casts"))
    	{
    		StarsInMoviesBatchInsert();
    	}
    }
    

    private int getMovieID(Movie m)
    {	
    	PreparedStatement getMovieSelect = null;
    	String getMovieQuery = "select id from movies where title = ? and year = ? and director = ?";
    	try{
    		getMovieSelect = connection.prepareStatement(getMovieQuery);
    		getMovieSelect.setString(1, m.getTitle());
    		getMovieSelect.setInt(2, m.getYear());
    		getMovieSelect.setString(3, m.getDirector());
    		
    		ResultSet rs = getMovieSelect.executeQuery();
    		while(rs.next())
    		{
    			int movieid = rs.getInt(1);
    			return movieid;
    		}
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
		return -1;
    }
    private int getGenreID(String g)
    {
    	PreparedStatement getGenreSelect = null;
    	String getMovieQuery = "select id from genres where name = ?";
    	try{
    		getGenreSelect = connection.prepareStatement(getMovieQuery);
    		getGenreSelect.setString(1, g);
    		
    		
    		ResultSet rs = getGenreSelect.executeQuery();
    		while(rs.next())
    		{
    			int genreid = rs.getInt(1);
    			return genreid;
    		}
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
		return -1;
    }
    
    private void GenreInMoviesBatchInsert()
    {
    	PreparedStatement genreInMovieInsert=null;

        String GenreInMovie=null;
       
        GenreInMovie=  "insert into genres_in_movies (genre_id, movie_id)" +
						" select g, m" + 
						" from (select ? as g, ? as m) as gm" +
						" where not exists(select * from genres_in_movies where genre_id = ? and movie_id = ?)";
        try {
			connection.setAutoCommit(false);

			genreInMovieInsert = connection.prepareStatement(GenreInMovie);
            
			for(Movie m: movies)
			{
				int movieid = getMovieID(m);
				for(String g: m.getListOfGenres())
	            {
					int genreid = getGenreID(g);
	    			genreInMovieInsert.setInt(1, genreid);
	    			genreInMovieInsert.setInt(2, movieid);
	    			genreInMovieInsert.setInt(3, genreid);
	    			genreInMovieInsert.setInt(4, movieid);
	    			genreInMovieInsert.addBatch();
	            }
			}
            genreInMovieInsert.executeBatch();
			connection.commit();
			
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(genreInMovieInsert != null) genreInMovieInsert.close();
//          if(connection != null) connection.close();
      } catch(Exception e) {
          e.printStackTrace();
      }
  }

    private void ActorBatchInsert()
    {
    	PreparedStatement starInsert=null;
    	String query=null;
    	
    	query="insert into stars (first_name, last_name, dob) values (?, ?, ?)";
    	try {
    		connection.setAutoCommit(false);
    		
    		starInsert = connection.prepareStatement(query);
    		
    		for(Star s: actors)
    		{
    			starInsert.setString(1, s.getFirst_name());
    			starInsert.setString(2, s.getLast_name());
    			starInsert.setString(3, s.getDob());
    			starInsert.addBatch();
    		}
    		
    		starInsert.executeBatch();
			connection.commit();
    			
    	}
    	catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	try {
            if(starInsert != null) starInsert.close();
//            if(connection != null) connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void GenreBatchInsert()
    {
    	PreparedStatement genreInsert=null;

        String addGenre=null;
       
        addGenre=  "insert into genres (name)" +
					" select g" + 
					" from (select ? as g) as m" +
					" where not exists(select * from genres where name = ?)";
        try {
			connection.setAutoCommit(false);

            genreInsert = connection.prepareStatement(addGenre);
            for(String g: genres)
            {
    			genreInsert.setString(1, g);
    			genreInsert.setString(2, g);
    			genreInsert.addBatch();
            }

			genreInsert.executeBatch();
			connection.commit();
			
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(genreInsert != null) genreInsert.close();
//            if(connection != null) connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void MovieBatchInsert()
    {
    	PreparedStatement movieInsert=null;
        String query=null;
        
        /*query=  "insert into movies (title, year, director)" +
        		" select t, y, d" + 
        		" from (select ? as t, ? as y, ? as d) as m" +
        		" where not exists(select * from movies where title = ? and year = ? and director = ?)";*/
        query = "insert into movies (title, year, director) values(?, ?, ?)";
        try {
			connection.setAutoCommit(false);

            movieInsert = connection.prepareStatement(query);
            for(Movie m: movies)
            {
                movieInsert.setString(1, m.getTitle());
            	movieInsert.setInt(2, m.getYear());
            	movieInsert.setString(3, m.getDirector());
//            	movieInsert.setString(4, m.getTitle());
//            	movieInsert.setInt(5, m.getYear());
//            	movieInsert.setString(6, m.getDirector());
            	//System.out.println(movieInsert.toString());
                movieInsert.addBatch();
            }
			movieInsert.executeBatch();
			connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(movieInsert != null) movieInsert.close();
//            if(connection != null) connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private int getMovieIDSimple(Cast c)
    {	
    	PreparedStatement getMovieSelect = null;
    	String getMovieQuery = "select id from movies where title = ? and director = ?";
    	try{
    		getMovieSelect = connection.prepareStatement(getMovieQuery);
    		getMovieSelect.setString(1, c.getMovieTitle());
    		getMovieSelect.setString(2, c.getDirector());
//    		getMovieSelect.setInt(2, m.getYear());
//    		getMovieSelect.setString(3, m.getDirector());
    		
    		ResultSet rs = getMovieSelect.executeQuery();
    		while(rs.next())
    		{
    			int movieid = rs.getInt(1);
    			return movieid;
    		}
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
		return -1;
    }
    
    private int getStarID(Cast c)
    {	
    	PreparedStatement getMovieSelect = null;
    	String getMovieQuery = "select id from stars where first_name = ? and last_name = ?";
    	try{
    		getMovieSelect = connection.prepareStatement(getMovieQuery);
    		getMovieSelect.setString(1, c.getFirstName());
    		getMovieSelect.setString(2, c.getLastName());
    		
    		ResultSet rs = getMovieSelect.executeQuery();
    		while(rs.next())
    		{
    			int starid = rs.getInt(1);
    			return starid;
    		}
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
		return -1;
    }
    
    private void StarsInMoviesBatchInsert()
    {
    	PreparedStatement starInMovieInsert=null;
        String query=null;

        query="insert into stars_in_movies (star_id, movie_id) " +
			" select si, mi" + 
			" from (select ? as si, ? as mi) as m" +
			" where not exists(select * from stars_in_movies where star_id = ? and movie_id = ?)";
        try {
			connection.setAutoCommit(false);

            starInMovieInsert = connection.prepareStatement(query);


            for(Cast c: casts)
            {
            	int starid = getStarID(c);
            	int movieid = getMovieIDSimple(c);
            	if(starid != -1 && movieid != -1)
            	{
            		starInMovieInsert.setInt(1, starid);
	    			starInMovieInsert.setInt(2, movieid);
	    			starInMovieInsert.setInt(3, starid);
	    			starInMovieInsert.setInt(4, movieid);
	    			starInMovieInsert.addBatch();
            	}
            }
			starInMovieInsert.executeBatch();
			connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(starInMovieInsert != null) starInMovieInsert.close();
//            if(connection != null) connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    


    public static void main(String[] args) {
        MyParser spe = new MyParser();
        long startTime = System.currentTimeMillis();

        spe.run();

        long endTime = System.currentTimeMillis();

        System.out.println("That took " + (endTime - startTime) + " milliseconds");
    }

}
