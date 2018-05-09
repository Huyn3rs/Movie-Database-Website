package servlets;

import java.io.*;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import modules.JDBC;
import modules.VerifyUtils;
public class LoginServlet extends HttpServlet {
	
	Connection connection = null;
	
	public LoginServlet() throws Exception
	{
		super();
		connection = JDBC.createConnection();	
	}
	public boolean reCaptchaCheck(HttpServletRequest request)
	{
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
		// Verify CAPTCHA.
		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
		return valid;
	}
	
	public boolean checkAccount(HttpServletRequest request, HttpServletResponse response, String email, String password)
	{
		try
		{
			request.getSession().setAttribute("connection", connection);
			
			//Statement s = connection.createStatement();
			PreparedStatement login =  null;
			//String query = "Select email, password, first_name, last_name, address from customers where email = '" + email + "' and password = '" + password +"'";
			
			String query = "Select email, password, first_name, last_name, address from customers where email = ? and password = ?";
			login = connection.prepareStatement(query);
			login.setString(1, email);
			login.setString(2, password);
			
			ResultSet result = login.executeQuery();
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
//			System.out.println("Connection was not made.");
			return false;
		}
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("email", null);
		request.getSession().setAttribute("password", null);
		request.getSession().setAttribute("cart", null);
        request.getRequestDispatcher("/WEB-INF/templates/login.jsp").forward(request, response);
        request.getSession().setAttribute("alert", "no");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		System.out.println("Login failed?: " + request.getSession().getAttribute("alert"));
		String email = request.getParameter("email");
		String password = request.getParameter("password"); 
		
		if (email.equals("") || password.equals(""))
		{
			request.getSession().setAttribute("alert", "yes");
			request.getRequestDispatcher("/WEB-INF/templates/login.jsp").forward(request, response);
		}
		else if (checkAccount(request, response, email, password) && reCaptchaCheck(request))
		{
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("password", password);

			response.sendRedirect("home");
		}
		else
		{
			request.getSession().setAttribute("alert", "yes");
			response.sendRedirect("login");
			//request.getRequestDispatcher("/WEB-INF/templates/login.jsp").forward(request, response);
		}
	}
}
