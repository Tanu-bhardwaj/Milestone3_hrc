package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.highradius.dao.Response;


/**
 * Servlet implementation class dummyServlet
 */
@WebServlet("/dummyServlet")
public class DummyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DummyServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
	//Database Credentials
	 final String USER = "root";
	 final String PASSWORD = "root"; 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		 
		try {
			//registering the jdbc driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			//Statement smt = con.createStatement();
			Integer offset = Integer.parseInt(request.getParameter("start")), limit = Integer.parseInt(request.getParameter("limit"));  
			String query = "SELECT film_id,title, Description,Director,language_id, release_year, Rating, Special_features from film where isDeleted=0 LIMIT ? OFFSET ?";
			PreparedStatement stmt=con.prepareStatement(query);
			
			stmt.setInt(2, offset);
			stmt.setInt(1, limit);
			ResultSet rs = stmt.executeQuery();
			
			ArrayList<Response> details = new ArrayList<>();
			while(rs.next()) {
				Response detail = new Response();
				detail.setFilm_id(rs.getInt("film_id"));
				detail.setTitle(rs.getString("title"));
				detail.setDescription(rs.getString("Description"));
				detail.setDirector(rs.getString("Director"));
				detail.setLanguage_id(rs.getString("language_id"));
				detail.setYear(rs.getInt("release_year"));
				
				detail.setRating(rs.getString("Rating"));
				detail.setSpecial_Features(rs.getString("Special_features"));
				
				details.add(detail);
				}
			HashMap<String, ArrayList<Response>> films = new HashMap<>();
			films.put("films", details);
			
			Integer totalRows = 0;
			stmt = con.prepareStatement("SELECT COUNT(*) AS total FROM film where isDeleted=0 ");
			System.out.println(stmt.toString());
			
			 rs = stmt.executeQuery();
			rs.next();
			totalRows = rs.getInt("total");
			
			Gson gson = new Gson();
			JsonElement jsonElement = gson.toJsonTree(films);
			jsonElement.getAsJsonObject().addProperty("total", totalRows);
			//String data = gson.toJson(jsonElement);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			
		 
			String res = gson.toJson(jsonElement);
			
			out.print(res);
			//System.out.println(res);
			//response.setStatus(200);
			out.flush();
			stmt.close();
			con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}


		
		