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

public class MapContainer extends BeanItemContainer<Map> 
  implements Serializable {
  private static Connection conn=null;
  public MapContainer() throws InstantiationException, 
    IllegalAccessException {
 
	super (Map.class);
  }
  
  public static MapContainer getMyMaps(String placeName)
  {
	  MapContainer mapContainer = null;
	  try
	  {
	    if (conn== null) {
			  conn=getConn();
		    }
	    
	    mapContainer = new MapContainer();
	    
		Statement select = conn.createStatement();
		String selectStatement= "SELECT map.Image_Url, map.Description, map.Width, map.Height from map, Place where map.Place_id = place.Id and place.Name="+
			 "\""+ placeName + "\";";
		System.out.println(selectStatement);
		ResultSet result = select.executeQuery(selectStatement);
		
		while (result.next()) {		
			 System.out.println(result.getString(1));
			 System.out.println(result.getString(2));
		     Map map = new Map();
		     map.setImageUrl(result.getString(1));
		     map.setMapIcon(new Embedded("Map", new ThemeResource(map.getImageUrl())));
		     map.setMapDescription(result.getString(2));
		     map.setMapWidth(result.getInt(3));
		     map.setMapHeight(result.getInt(4));
		     mapContainer.addBean(map);
		}
	  }  	
	  catch (SQLException e){
	      e.printStackTrace();
	  } catch (IllegalAccessException e) {
		       e.printStackTrace();    
      } catch (InstantiationException e) {
		    e.printStackTrace(); 
	       } 
      return mapContainer;
  }
  
  public static Map getMap(String mapImageUrl)
  {
	  Map map = new Map();
	  
	  try
	  {
	    if (conn== null) {
			  conn=getConn();
		    }
	    
		Statement select = conn.createStatement();
		String selectStatement= "SELECT map.Image_Url, map.Description, map.Width, map.Height, map.Scale from map where map.Image_Url="+
			 "\""+ mapImageUrl + "\";";
		System.out.println(selectStatement);
		ResultSet result = select.executeQuery(selectStatement);

		while (result.next()) {
			
		map.setImageUrl(result.getString(1));
		map.setMapIcon(new Embedded("Map", new ThemeResource(map.getImageUrl())));
		map.setMapDescription(result.getString(2));
		map.setMapWidth(result.getInt(3));
		map.setMapHeight(result.getInt(4));
	    map.setMapScale(result.getFloat(5));
		}
	  }  	
	  catch (SQLException e){
	      e.printStackTrace();
	  }
      return map;
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
