package servlets;
import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.*;
import javax.servlet.http.*;

import modules.Cart;
import modules.JDBC;


public class CheckoutServlet extends HttpServlet {
	
	Connection connection = null;
	
	public CheckoutServlet() throws Exception
	{
		super();
		connection = JDBC.createConnection();	
	}
	
	public boolean checkAccount(HttpServletRequest request, HttpServletResponse response, String firstName, String lastName, String creditCard, String expDate)
	{
		try
		{
			request.getSession().setAttribute("connection", connection);
//			Statement s = connection.createStatement();
//			String query = "Select cc.* from creditcards as cc"
//					+ " where cc.first_name = '" + firstName
//					+ "' and cc.last_name = '" + lastName 
//					+ "' and cc.id = '" + creditCard
//					+ "' and cc.expiration = '" + expDate + "'";
			
			PreparedStatement s = null;
			String query = "Select cc.* from creditcards as cc"
					+ " where cc.first_name = ?"
					+ " and cc.last_name = ?"
					+ " and cc.id = ?" 
					+ " and cc.expiration = ?";
			s = connection.prepareStatement(query);
			s.setString(1, firstName);
			s.setString(2, lastName);
			s.setString(3, creditCard);
			s.setString(4, expDate);
			//System.out.println(query);
			ResultSet result = s.executeQuery();
			
			int count = 0;
			while (result.next())
        	{
				//System.out.println("id = " + result.getString(1));
                //System.out.println("name = " + result.getString(2) + " " + result.getString(3));
                //System.out.println("expiration = " + result.getString(4));
                count++;
        	}
			System.out.println(count);
			return count == 1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("firstName", null);
		request.getSession().setAttribute("lastName", null);
		request.getSession().setAttribute("creditCard", null);
		request.getSession().setAttribute("expDate", null);	
		request.getRequestDispatcher("/WEB-INF/templates/checkout.jsp").forward(request, response);
		request.getSession().setAttribute("confirmation", "yes");	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String creditCard = request.getParameter("creditCard");
		String expDate = request.getParameter("expDate");
		
		if (firstName == null || lastName == null || creditCard == null || expDate == null)
		{
			request.getSession().setAttribute("confirmation", "no");
			request.getRequestDispatcher("/WEB-INF/templates/checkout.jsp").forward(request, response);
		}
		else if (checkAccount(request, response, firstName, lastName, creditCard, expDate))
		{
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("lastName", lastName);
			request.getSession().setAttribute("creditCard", creditCard);
			request.getSession().setAttribute("expDate", expDate);
			request.getSession().setAttribute("cofirmation", "yes");
			
			Cart cart = (Cart)request.getSession().getAttribute("cart");
			try
			{
				request.getSession().setAttribute("connection", connection);
				Statement s = connection.createStatement();
				String id = "";
				String getID = "select id from customers where first_name = '" 
					+ firstName + "' and last_name = '" + lastName + "'";
				//System.out.println(getID);
				ResultSet result = s.executeQuery(getID);
				
				while (result.next())
				{
					id = result.getString(1);
					//System.out.println(id);
				}
				
				String date = "";
				LocalDate localDate = LocalDate.now();
			    date = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate);
	
				for(int i = 0; i < cart.movies.size(); i++)
				{
					for(int j = 0; j < cart.movies.get(i).getCount(); j++)
					{
						String insert = "insert into sales(customer_id, movie_id, sale_date) " 
							+ "values(" + id + ", " + cart.movies.get(i).id + ", '" + date + "')";
						//System.out.println(insert);
						s.executeUpdate(insert);
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		
			response.sendRedirect("confirmed");
		}
		else
		{
			request.getSession().setAttribute("confirmation", "no");
			response.sendRedirect("checkout");
		}
	}
}