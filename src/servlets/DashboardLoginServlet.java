package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modules.JDBC;
import modules.VerifyUtils;

public class DashboardLoginServlet extends HttpServlet {
	Connection connection = null;
	
	public DashboardLoginServlet() throws Exception
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
			
//			Statement s = connection.createStatement();
//			String query = "Select email, password, fullname from employees where email = '" + email + "' and password = '" + password +"'";
			
			PreparedStatement s = null;
			String query = "Select email, password, fullname from employees where email = ? and password = ?";
			s = connection.prepareStatement(query);
			s.setString(1, email);
			s.setString(2, password);
			
			ResultSet result = s.executeQuery();
			int count = 0;
			while (result.next())
        	{
                System.out.println("email = " + result.getString(1));
                System.out.println("password = " + result.getString(2));
                request.getSession().setAttribute("eemail", result.getString(1));
                request.getSession().setAttribute("epassword", result.getString(2));
                request.getSession().setAttribute("efullname", result.getString(3));
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
		request.getSession().setAttribute("eemail", null);
		request.getSession().setAttribute("epassword", null);
		request.getSession().setAttribute("cart", null);
        request.getRequestDispatcher("/WEB-INF/templates/dashboard_login.jsp").forward(request, response);
        request.getSession().setAttribute("alert", "no");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String email = request.getParameter("eemail");
		String password = request.getParameter("epassword"); 
		
		if (email.equals("") || password.equals(""))
		{
			request.getSession().setAttribute("alert", "yes");
			request.getRequestDispatcher("/WEB-INF/templates/dashboard_login.jsp").forward(request, response);
		}
		else if (checkAccount(request, response, email, password)  && reCaptchaCheck(request))
		{
			request.getSession().setAttribute("eemail", email);
			request.getSession().setAttribute("epassword", password);
			response.sendRedirect("../dashboard");
		}
		else
		{
			request.getSession().setAttribute("alert", "yes");
			response.sendRedirect("../dashboard/login");
			//request.getRequestDispatcher("/WEB-INF/templates/login.jsp").forward(request, response);
		}
	
	}
}
