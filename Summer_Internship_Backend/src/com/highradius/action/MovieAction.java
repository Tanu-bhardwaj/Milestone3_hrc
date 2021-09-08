package com.highradius.action;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.highradius.dao.Database;
import com.highradius.dao.Response;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class MovieAction  extends ActionSupport{
	private String Title;
	private String Description;
	private String Director;
	private String language_id;
	private int Year;
	private int film_id;
	private String Rating;
	private String Special_Features;
	private int start;
	private int limit;
	ArrayList<Response>details = new ArrayList<>();
	
          public ArrayList<Response> getDetails() {
		return details;
	}

	public void setDetails(ArrayList<Response> details) {
		this.details = details;
	}

		public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	

		public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getDirector() {
		return Director;
	}

	public void setDirector(String director) {
		Director = director;
	}

	public String getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(String language_id) {
		this.language_id = language_id;
	}

	public int getYear() {
		return Year;
	}

	public void setYear(int year) {
		Year = year;
	}

	public int getFilm_id() {
		return film_id;
	}

	public void setFilm_id(int film_id) {
		this.film_id = film_id;
	}

	public String getRating() {
		return Rating;
	}

	public void setRating(String rating) {
		Rating = rating;
	}

	public String getSpecial_Features() {
		return Special_Features;
	}

	public void setSpecial_Features(String special_Features) {
		Special_Features = special_Features;                           // "+start+",
	}

	Integer totalRows = 0;
	
	
	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public String fetchData() {
		 final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
 		//Database Credentials
 		 final String USER = "root";
 		 final String PASSWORD = "root"; 
 	  try {
			//registering the jdbc driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			//Statement smt = con.createStatement();
		   // Integer offset = Integer.parseInt(getParameter("start")), limit = Integer.parseInt(getParameter("limit"));  
			String query = "SELECT film_id,title, Description,Director,language_id, release_year, Rating, Special_features from film where isDeleted=0 LIMIT "+start+",10";//"+start+" OFFSET 25";
			PreparedStatement stmt=con.prepareStatement(query);
			
			//stmt.setInt(2, offset);
			//stmt.setInt(1, limit);
			ResultSet rs = stmt.executeQuery();
			
			
			
			stmt = con.prepareStatement("SELECT COUNT(*) AS total FROM film where isDeleted=0 ");
			System.out.println(stmt.toString());
			
			 ResultSet rs1 = stmt.executeQuery();
			rs1.next();
			totalRows = rs1.getInt("total");
			
			while(rs.next()) {
				Response detail = new Response();
				film_id=rs.getInt(1);
				Title=rs.getString(2);
				Description=rs.getString(3);
				Director=rs.getString(4);
				language_id=rs.getString(5);
				Year=rs.getInt(6);
				Rating=rs.getString(7);
				Special_Features=rs.getString(8);
				detail.setFilm_id(film_id);
				detail.setTitle(Title);
				detail.setDescription(Description);
				detail.setDirector(Director);
				detail.setLanguage_id(language_id);
				detail.setYear(Year);
				
				detail.setRating(Rating);
				detail.setSpecial_Features(Special_Features);
				detail.setTotalRows(totalRows);
				details.add(detail);
				}
//			HashMap<String, ArrayList<Response>> films = new HashMap<>();
//			films.put("films", details);
			rs.close();
			rs1.close();
		


//			
			//Gson gson = new Gson();
//			JsonElement jsonElement = gson.toJsonTree(films);
//			jsonElement.getAsJsonObject().addProperty("total", totalRows);
//			//String data = gson.toJson(jsonElement);
//			
//			response.setContentType("application/json");
//			response.setCharacterEncoding("UTF-8");
//			PrintWriter out = response.getWriter();
			
		 
			//String res = gson.toJson(details);
			
			//System.out.println(gson);
			//System.out.println(res);
			//response.setStatus(200);
			//out.flush();
			stmt.close();
			con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
        	  return Action.SUCCESS;
          }
	public String addData() {
		final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
		//Database Credentials
		 final String USER = "root";
		 final String PASSWORD = "root"; 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			
//			String Title =this.setTitle();;
//			int Year =Integer.parseInt(getParameter("Year"));
//			String Special_Features = getParameter("Special_Features");
//			String Rating = getParameter("Rating");
//			String language = getParameter("language_id");
//			String Director = getParameter("Director");
//			String Description = getParameter("Description");
			
			
			String sql_statement = "INSERT INTO film (Title, release_year, special_features,rating,language_id,Director, description) values (?, ?, ?,?,?, ?, ?)";
			
			PreparedStatement st = con.prepareStatement(sql_statement);
			st.setString(1, Title);
			st.setInt(2, Year);
			st.setString(3, Special_Features);
			st.setString(4, Rating);
			st.setString(5, language_id);
			st.setString(6, Director);
			st.setString(7,  Description);
			System.out.println("added successfully");
			//System.out.println(st);
			st.executeUpdate();
			con.close();
			st.close();
			con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		return Action.SUCCESS;
	}
public String editData() {
	final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
	//Database Credentials
	 final String USER = "root";
	 final String PASSWORD = "root"; 
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//		
//		String Title = request.getParameter("Title");
//		int Year =Integer.parseInt(request.getParameter("Year"));
//		String Special_Features = request.getParameter("Special_Features");
//		String Rating = request.getParameter("Rating");
//		String language = request.getParameter("language_id");
//		String Director = request.getParameter("Director");
//		String Description = request.getParameter("Description");
		
		
		String sql_statement = "update film set Title=?, release_year=?, special_features=?,rating=?,language_id=?,Director=?, description=? where film_id=?";
		
		PreparedStatement st = con.prepareStatement(sql_statement);
		st.setString(1, Title);
		st.setInt(2, Year);
		st.setString(3, Special_Features);
		st.setString(4, Rating);
		st.setString(5, language_id);
		st.setString(6, Director);
		st.setString(7,  Description);
		st.setInt(8,film_id);
		//System.out.println(st);
		st.executeUpdate();
		con.close();
		st.close();
		con.close();
	} catch(Exception e) {
		System.out.println(e);
	}
	return Action.SUCCESS;
	}
public String delData(String film_id) {
	final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
	//Database Credentials
	 final String USER = "root";
 final String PASSWORD = "root"; 
 try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//			
//			// 885,884
//			
	//String[] film_id =("film_id");
//			
		//for(String id:film_id) {
			String sql_statement = "UPDATE film SET isDeleted=1  WHERE film_id in (?)";
//			
			PreparedStatement st = con.prepareStatement(sql_statement);
			System.out.println(film_id);
			st.setString(1, film_id);
			System.out.println(st.toString());
//		   
			st.executeUpdate();
			st.close();
		//}
		con.close();
//			
//			
		} catch(Exception e) {
			System.out.println(e);
		}
	
 return Action.SUCCESS;
}
}
