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

import org.json.JSONArray;
import org.json.JSONObject;

public class AppSearchServlet extends HttpServlet {
	
	private String getFullTextQuery(String query)
	{
		String result = "select m.*"
				  + " from movies as m" 
				  + " where match(m.title) against (concat(";

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
		
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		JSONArray jsonArr = new JSONArray();
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		
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

		
		String title = request.getParameter("title");
		String str_start = request.getParameter("start");
		
		
		int start = 0; //For pagination ('offset' in sql query)
		if (str_start != null)
			start = Integer.parseInt(str_start);

		
		request.getSession().setAttribute("title", title);
		request.getSession().setAttribute("start", start);
	
		
		request.getSession().setAttribute("connection", connection);
		
		ArrayList<Movie> result = null;
		String statement = "";
		PreparedStatement searchStatement = null;
		ResultSet rs = null;
		int result_size = 0;
		
		if(title != null) // Full-text search by movie title
		{
			statement = getFullTextQuery(title);
		}
		
		
		if(statement != "")
		{
			statement += JDBC.addToQuery(10, "", start);
			try
			{
				int strings = 1;
				searchStatement = connection.prepareStatement(statement);
				String[] parts = title.split(" ");
				for (String p: parts)
				{
					if (p.length() > 3 || parts.length == 1)
					{
						searchStatement.setString(strings, p);
						strings++;
					}
				}
				rs = searchStatement.executeQuery();
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = JDBC.executeSelectStatement(connection, rs);
			result_size = result.size();
		}
		
		if (result != null && result.size() != 0)
		{
			JSONObject moviesize = new JSONObject();
			moviesize.put("results", result_size);
			jsonArr.put(moviesize);
			for(Movie m: result)
			{
				JSONObject movieJSON = new JSONObject();
				movieJSON.put("id", m.getId());
				movieJSON.put("title", m.getTitle()); // result page can just be as simple as a list of titles
				jsonArr.put(movieJSON);
			}
			String output = jsonArr.toString();
			out.println(output);
		}
		else{
			out.println("");
		}
	}
}

