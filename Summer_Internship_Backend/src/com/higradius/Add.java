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
 * Servlet implementation class Add
 */
@WebServlet("/Add")
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
		//Database Credentials
		 final String USER = "root";
		 final String PASSWORD = "root"; 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			
			String Title = request.getParameter("Title");
			int Year =Integer.parseInt(request.getParameter("Year"));
			String Special_Features = request.getParameter("Special_Features");
			String Rating = request.getParameter("Rating");
			String language = request.getParameter("language_id");
			String Director = request.getParameter("Director");
			String Description = request.getParameter("Description");
			
			
			String sql_statement = "INSERT INTO film (Title, release_year, special_features,rating,language_id,Director, description) values (?, ?, ?,?,?, ?, ?)";
			
			PreparedStatement st = con.prepareStatement(sql_statement);
			st.setString(1, Title);
			st.setInt(2, Year);
			st.setString(3, Special_Features);
			st.setString(4, Rating);
			st.setString(5, language);
			st.setString(6, Director);
			st.setString(7,  Description);
			
			//System.out.println(st);
			st.executeUpdate();
			con.close();
			st.close();
			con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}