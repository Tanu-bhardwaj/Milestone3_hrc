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
 * Servlet implementation class Edit
 */
@WebServlet("/Edit")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
		//Database Credentials
		 final String USER = "root";
		 final String PASSWORD = "root"; 
		 try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
				int film_id =Integer.parseInt(request.getParameter("film_id"));
				String Title = request.getParameter("title");
				int Year =Integer.parseInt(request.getParameter("releaseyear"));
				String Special_Features = request.getParameter("namefeature");
				String Rating = request.getParameter("ratings");
				String language = request.getParameter("language_id");
				String Director = request.getParameter("director");
				String Description = request.getParameter("description");
				
				
				String sql_statement = "UPDATE film SET Title = ?,release_year = ?,special_features=?,rating=?,language_id=?,Director=?,Description=? WHERE film_id = ?";
				
				PreparedStatement st = con.prepareStatement(sql_statement);

				st.setString(1, Title);
				st.setInt(2, Year);
				st.setString(3, Special_Features);
				st.setString(4, Rating);
				st.setString(5, language);
				st.setString(6, Director);
				st.setString(7, Description);
				st.setInt(8, film_id);
				
				System.out.println(st.toString());
				st.executeUpdate();
				con.close();
				st.close();
				con.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		}

			
	