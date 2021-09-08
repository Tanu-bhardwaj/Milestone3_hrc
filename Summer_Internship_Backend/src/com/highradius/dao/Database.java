package com.highradius.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;

public class Database {
	ArrayList<Response>details = new ArrayList<>();
	
	public ArrayList<Response> details() {
		return details;
	}

	public void setMovieList(ArrayList<Response> details) {
		this.details = details;
	}
	
	public void fetchFilm() {
		 final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
 		//Database Credentials
 		 final String USER = "root";
 		 final String PASSWORD = "root"; 
 	  try {
			//registering the jdbc driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			//Statement smt = con.createStatement();
		   // Integer offset = Integer.parseInt(request.getParameter("start")), limit = Integer.parseInt(request.getParameter("limit"));  
			String query = "SELECT film_id,title, Description,Director,language_id, release_year, Rating, Special_features from film where isDeleted=0 LIMIT 10";//"+start+" OFFSET 25";
			PreparedStatement stmt=con.prepareStatement(query);
			
			//stmt.setInt(2, offset);
			//stmt.setInt(1, limit);
			ResultSet rs = stmt.executeQuery();
			
			
			Integer totalRows = 0;
			stmt = con.prepareStatement("SELECT COUNT(*) AS total FROM film where isDeleted=0 ");
			System.out.println(stmt.toString());
			
			 ResultSet rs1 = stmt.executeQuery();
			rs1.next();
			totalRows = rs1.getInt("total");
			
			while(rs.next()) {
				Response detail = new Response();
				
				
				
				
				
				
				
				detail.setFilm_id(rs.getInt(1));
				detail.setTitle(rs.getString(2));
				detail.setDescription(rs.getString(3));
				detail.setDirector(rs.getString(4));
				detail.setLanguage_id(rs.getString(5));
				detail.setYear(rs.getInt(6));
				
				detail.setRating(rs.getString(7));
				detail.setSpecial_Features(rs.getString(8));
				
				details.add(detail);
				}
//			HashMap<String, ArrayList<Response>> films = new HashMap<>();
//			films.put("films", details);
			rs.close();
			rs1.close();
		


//			
			Gson gson = new Gson();
//			JsonElement jsonElement = gson.toJsonTree(films);
//			jsonElement.getAsJsonObject().addProperty("total", totalRows);
//			//String data = gson.toJson(jsonElement);
//			
//			response.setContentType("application/json");
//			response.setCharacterEncoding("UTF-8");
//			PrintWriter out = response.getWriter();
			
		 
			String res = gson.toJson(details);
			
			System.out.println(res);
			//System.out.println(res);
			//response.setStatus(200);
			//out.flush();
			stmt.close();
			con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
 	  
	}
	
	
}
