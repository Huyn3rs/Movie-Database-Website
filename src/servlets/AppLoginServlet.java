package servlets;

import java.io.*;

import org.json.JSONObject;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import modules.JDBC;
import modules.VerifyUtils;
public class AppLoginServlet extends HttpServlet {
	
	Connection connection = null;
	
	public AppLoginServlet() throws Exception
	{
		super();
		connection = JDBC.createConnection();	
	}
	
	public boolean checkAccount(HttpServletRequest request, HttpServletResponse response, String email, String password)
	{
		try
		{
			request.getSession().setAttribute("connection", connection);
			
			Statement s = connection.createStatement();
			String query = "Select email, password, first_name, last_name, address from customers where email = '" + email + "' and password = '" + password +"'";
			ResultSet result = s.executeQuery(query);
			int count = 0;
			while (result.next())
        	{
                System.out.println("email = " + result.getString(1));
                System.out.println("password = " + result.getString(2));
                request.getSession().setAttribute("firstname", result.getString(3));
                request.getSession().setAttribute("lastname", result.getString(4));
                request.getSession().setAttribute("address", result.getString(5));
                count++;
        	}
			return count == 1;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
	    
	    JSONObject json = new JSONObject();
	    
		System.out.println("Login failed?: " + request.getSession().getAttribute("alert"));
		String email = request.getParameter("email");
		String password = request.getParameter("password"); 
		String message;
		
		if(email != null || password != null)
		{
			json.put("email", email);
			json.put("password", password);
			
			if (checkAccount(request, response, email, password))
			{
				json.put("success", "yes");
			}
			else
			{
				json.put("success", "no");
			}
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
			message = json.toString();
//			System.out.println(message);
			out.println(message);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		doGet(request, response);
	}
	   
}
