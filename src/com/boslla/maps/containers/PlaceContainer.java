package com.boslla.maps.containers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;

public class PlaceContainer extends BeanItemContainer<Place> 
  implements Serializable {
  private static Connection conn=null;
  public PlaceContainer() throws InstantiationException, 
    IllegalAccessException {
 
	super (Place.class);
  }
  
  public static PlaceContainer getAllPlaces() { 
	  
	  PlaceContainer placeContainer = null;
	  try{
		placeContainer = new PlaceContainer();  
	    if (conn== null) {
		  conn=getConn();
	    }
		  Statement select = conn.createStatement();
		  String selectStatement= "SELECT Name, Type, Location, Description, place.Image_Url, place.Longitude, place.Latitude from place, place_type where place_type_id = place_type.Id";
		  
		   System.out.println(selectStatement);
		   ResultSet result = select.executeQuery(selectStatement);
		   
		   while (result.next()) {
		     Place place= new Place();
		     place.setPlaceName(result.getString(1));
		     place.setPlaceType(result.getString(2));
		     place.setPlaceLocation(result.getString(3));
		     place.setPlaceDescription(result.getString(4));
		     place.setPlaceIcon(new Embedded(null, new ThemeResource(result.getString(5))));
		     place.setPlaceLongitude(result.getDouble(6));
		     place.setPlaceLatitude(result.getDouble(7));

		     placeContainer.addBean(place);
		   }
	  } catch (SQLException e){
	      e.printStackTrace();
	    } catch (InstantiationException e) {
		    e.printStackTrace(); 
	       } catch (IllegalAccessException e) {
		       e.printStackTrace();    
	         }
	   //notifyListeners();
	   return placeContainer;
  }

  public static PlaceContainer getPlace(String placeName) {
	  
	  PlaceContainer placeContainer = null;
	  try{
		placeContainer = new PlaceContainer();  
	    if (conn== null) {
		  conn=getConn();
	    }
		  Statement select = conn.createStatement();
		  String selectStatement= "SELECT Name, Type, Location, Description, place.Image_Url, place.Longitude, place.Latitude from place, place_type where place_type_id = place_type.Id and place.Name = " + "\""+placeName+"\"";
		  
		   System.out.println(selectStatement);
		   ResultSet result = select.executeQuery(selectStatement);
		   
		   while (result.next()) {
		     Place place= new Place();
		     place.setPlaceName(result.getString(1));
		     place.setPlaceType(result.getString(2));
		     place.setPlaceLocation(result.getString(3));
		     place.setPlaceDescription(result.getString(4));
		     place.setPlaceIcon(new Embedded(null, new ThemeResource(result.getString(5))));
		     place.setPlaceLongitude(result.getDouble(6));
		     place.setPlaceLatitude(result.getDouble(7));

		     placeContainer.addBean(place);
		   }
	  } catch (SQLException e){
	      e.printStackTrace();
	    } catch (InstantiationException e) {
		    e.printStackTrace(); 
	       } catch (IllegalAccessException e) {
		       e.printStackTrace();    
	         }
	   //notifyListeners();
	   return placeContainer;
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