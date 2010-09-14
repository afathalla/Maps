package com.boslla.maps.containers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Time;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.vaadin.data.util.BeanItemContainer;

public class ReviewContainer extends BeanItemContainer<Review> 
implements Serializable  {

private static Connection conn=null;

	public ReviewContainer() throws InstantiationException, 
	IllegalAccessException {

		super (Review.class);
		
	}

	public static boolean insertReview(Review review)
	{
		ReviewContainer reviewContainer = null;
		PreparedStatement pstmt = null;
		  try{
			reviewContainer = new ReviewContainer();  
		    if (conn== null) {
			  conn=getConn();
		    }
		    String insertStatement= "INSERT INTO reviews(Date, Time, Name, Email, Phone, Unit_Id, Description, Rate) values (?,?,?,?,?,?,?,?)";
		    System.out.println(insertStatement);
		    
		    pstmt = conn.prepareStatement(insertStatement);
			 
		    Date sqlDate = new Date(new java.util.Date().getTime());
		    Time sqlTime = new Time (new java.util.Date().getTime());
		    pstmt.setDate(1, sqlDate); // set input parameter 1
		    pstmt.setTime(2, sqlTime); // set input parameter 2
		    pstmt.setString(3, review.getName()); // set input parameter 3
		    pstmt.setString(4, review.getEmail()); // set input parameter 4
		    pstmt.setString(5, review.getPhone()); // set input parameter 5		    
		    pstmt.setInt(6, review.getUintId()); // set input parameter 6
		    pstmt.setString(7, review.getDescription()); // set input parameter 7
		    pstmt.setInt(8, review.getRate()); // set input parameter 8
		   
		    pstmt.executeUpdate(); // execute insert statement
			  
		  } catch (SQLException e){
		      e.printStackTrace();
		      return false;
		    } catch (InstantiationException e) {
			    e.printStackTrace();
			    return false;
		       } catch (IllegalAccessException e) {
			       e.printStackTrace();  
			       return false;
		         }
		    
		  return true;			  
	}
	
	public static ReviewContainer getReviews(int unitId) { 
		  ReviewContainer reviewContainer = null;
		  try{
			reviewContainer = new ReviewContainer();  
		    if (conn== null) {
			  conn=getConn();
		    }
			  Statement select = conn.createStatement();
			  String selectStatement= "SELECT Date, Time, Name, Email, Phone, Unit_Id, Description, Rate from reviews where Unit_Id =" +
	            "\""+ unitId + "\";";
			  
			  System.out.println(selectStatement);
			  ResultSet result = select.executeQuery(selectStatement);
			  int resultCounter = 1;
			   
			   while (result.next()) {
			     Review review = new Review();
			     
			     review.setDate(result.getDate(1));
			     review.setTime(result.getTime(2));
			     review.setName(result.getString(3));
			     review.setEmail(result.getString(4));
			     review.setPhone(result.getString(5));
			     review.setUnitId(result.getInt(6));
			     review.setDescription(result.getString(7));
			     review.setRate(result.getInt(8));
			     resultCounter++;
			     
			     reviewContainer.addBean(review);
			   }
		  } catch (SQLException e){
		      e.printStackTrace();
		    } catch (InstantiationException e) {
			    e.printStackTrace(); 
		       } catch (IllegalAccessException e) {
			       e.printStackTrace();    
		         }
		   //notifyListeners();
		   return reviewContainer;
	  }
	  
	public static int getReviewsCount(String unitName) { 
			
		int reviewsCount=0;

		try{
  
		    if (conn== null) {
			  conn=getConn();
		    }
			  Statement select = conn.createStatement();
			  String selectStatement= "SELECT count(*) from reviews, unit where reviews.Unit_Id = unit.Id and unit.name =" +
	            "\""+ unitName + "\";";
			  
			  System.out.println(selectStatement);
			  ResultSet result = select.executeQuery(selectStatement);
			  
			   
			   while (result.next()) {
				     
				   reviewsCount = result.getInt(1);
			   }
		  } catch (SQLException e){
		      e.printStackTrace();
		  }
		   //notifyListeners();
		       
		  return reviewsCount;
	  }
	  
	private static Connection getConn() {
		Connection conn = null;
	    String url = "jdbc:mysql://localhost:3306/";
	    String db = "makany_dev";
	    String driver = "com.mysql.jdbc.Driver";
	    String user = "root";
	    String pass = "";
	    try {
	      Class.forName(driver).newInstance();
	    } catch (InstantiationException e) {
	        e.printStackTrace();
	    } catch (IllegalAccessException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
		try {       
		    conn = DriverManager.getConnection(url+db, user, pass);
		} catch (SQLException e) {
		    System.err.println("Mysql Connection Error: ");
		    e.printStackTrace();
		}
		return conn;
	}
}
