package com.example.maps.containers;

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
  public static PlaceContainer getSimilarPlaces(String placeName) { 
	  PlaceContainer placeContainer = null;
	  try{
		placeContainer = new PlaceContainer();  
	    if (conn== null) {
		  conn=getConn();
	    }
		  Statement select = conn.createStatement();
		  String selectStatement= "SELECT place_name,x,y from places where place_name LIKE " +
            "\"%"+ placeName + "%\";";
		   System.out.println(selectStatement);
		   ResultSet result = select.executeQuery(selectStatement);
		   int resultCounter = 1;
			while (result.next()) {
			 System.out.println(result.getString(1));
			 System.out.println(result.getString(2));
			 System.out.println(result.getString(3));
		     Place similarPlace = new Place();
		     similarPlace.setPlaceName(result.getString(1));
		     similarPlace.setX(result.getInt(2));
		     similarPlace.setY(result.getInt(3));
		     similarPlace.setPlaceIcon(new Embedded("Number", new ThemeResource("icons/number_"
		    		                   + resultCounter + ".png")));
		     resultCounter++;
		     placeContainer.addBean(similarPlace);
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
  
// private static void notifyListeners() {
//      ArrayList<ItemSetChangeListener> cl = (ArrayList<ItemSetChangeListener>) listeners.clone();
//      ItemSetChangeEvent event = new ItemSetChangeEvent() {
//          public Container getContainer() {
//              return PlaceContainer.this;
//          }
//      };
//
//      for (ItemSetChangeListener listener : cl) {
//          listener.containerItemSetChange(event);
//      }
//}

  
  private static Connection getConn() {
	Connection conn = null;
    String url = "jdbc:mysql://localhost:3306/";
    String db = "makany_dev";
    String driver = "com.mysql.jdbc.Driver";
    String user = "root";
    String pass = "root";
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
