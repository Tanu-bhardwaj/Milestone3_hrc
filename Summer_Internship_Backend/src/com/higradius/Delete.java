package com.higradius;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
		//Database Credentials
		 final String USER = "root";
		 final String PASSWORD = "root"; 
		 try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
				
				// 885,884
				
				 String[] film_id =request.getParameterValues("film_id");
				
				for(String id:film_id) {
					String sql_statement = "UPDATE film SET isDeleted=1  WHERE film_id = ?";
				
					PreparedStatement st = con.prepareStatement(sql_statement);
				
					st.setString(1, id);
					System.out.println(st.toString());
			   
					st.executeUpdate();
					st.close();
				}
				con.close();
				
				
			} catch(Exception e) {
				System.out.println(e);
			}
		}

	}
	
		 