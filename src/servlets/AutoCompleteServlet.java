package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modules.JDBC;

public class AutoCompleteServlet extends HttpServlet {
	
	private String getQuickSearchQuery(String query)
	{
		String result = "select m.title, m.id"
					  + " from movies as m" 
					  + " where match(m.title) against ('";
		
		if (query == null)
			return "";
		
		String[] parts = query.split(" ");

		for (String p: parts)
		{
			if (p.length() > 3 || parts.length == 1)
				result += "+" + p + "* ";
		}
		result += "' in boolean mode) order by m.title limit 10";
		return result;
		
	}
	
	private ResultSet executeAutoComplete(Connection connection, String query) throws Exception{
		Statement s = null;
		ResultSet rs = null;

		s = connection.createStatement();

		rs = s.executeQuery(query);
		return rs;
			

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
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String query = request.getParameter("q");
	    String statement = getQuickSearchQuery(query);

	    if(query != null && !statement.equals(""))
	    {

	    	try {
	    		ResultSet rs = executeAutoComplete(connection, statement);
				while (rs.next()) {
					String title = rs.getString(1);
					int id = rs.getInt(2);
					String output = "<a class=\"movie__autocomplete--link\" href= \"" + request.getContextPath() +"/view?movie=" + 
									id + "\"><div class=\"movie__autocomplete\">" + title + "</div></a>";
					out.println(output);
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	    }
	    		
	    		
	    		
	    		
	    		
	}

}
