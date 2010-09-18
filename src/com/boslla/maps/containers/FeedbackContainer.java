package com.boslla.maps.containers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;

public class FeedbackContainer implements Serializable {

private static Connection conn=null;

	public FeedbackContainer() throws InstantiationException, 
	IllegalAccessException {

	}

	public static boolean insertFeedback(Feedback feedback)
	{
		FeedbackContainer feedbackContainer = null;
		PreparedStatement pstmt = null;
		  try{
			feedbackContainer = new FeedbackContainer();  
		    if (conn== null) {
			  conn=getConn();
		    }
		    String insertStatement= "INSERT INTO feedback(Date, Time, Name, Email, Type, Title, Description) values (?,?,?,?,?,?,?)";
		    System.out.println(insertStatement);
		    
		    pstmt = conn.prepareStatement(insertStatement);
			 
		    Date sqlDate = new Date(new java.util.Date().getTime());
		    Time sqlTime = new Time (new java.util.Date().getTime());
		    pstmt.setDate(1, sqlDate); // set input parameter 1
		    pstmt.setTime(1, sqlTime); // set input parameter 2
		    pstmt.setString(2, feedback.getName()); // set input parameter 3
		    pstmt.setString(3, feedback.getEmail()); // set input parameter 4
		    pstmt.setString(4, feedback.getType()); // set input parameter 5
		    pstmt.setString(5, feedback.getTitle()); // set input parameter 6		    
		    pstmt.setString(6, feedback.getDescription()); // set input parameter 7
		   
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
	
	public static String[] getFeedbackTypes()
	{
		FeedbackContainer feedbackContainer = null;
		String [] feedbackType = null;
		 
		  try{
			  	feedbackContainer = new FeedbackContainer();  
			  	ResultSet result;
			  	if (conn== null) {
			  		conn=getConn();
			  	}
			  	Statement select = conn.createStatement();
			  	String selectStatement= "SELECT Type from feedback_type";
			  	String countStatement= "SELECT count(*) from feedback_type";
			  
			  	System.out.println(selectStatement);
			  	result = select.executeQuery(countStatement);

			  	while (result.next()) {
			  	feedbackType = new String [result.getInt(1)];
			  	}
			  	
			  	result = select.executeQuery(selectStatement);
			  	
			  	int resultCounter = 0;
			  	while (result.next()) {
			  		
			  		feedbackType [resultCounter] = result.getString(1);
			  		resultCounter++;
			  	}
			  			  
		  } catch (SQLException e){
		      e.printStackTrace();
		    } catch (InstantiationException e) {
			    e.printStackTrace(); 
		       } catch (IllegalAccessException e) {
			       e.printStackTrace();    
		         }
			  
		return feedbackType;
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
