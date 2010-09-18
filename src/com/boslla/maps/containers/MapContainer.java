package com.boslla.maps.containers;

import com.boslla.maps.containers.*;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;

public class MapContainer extends BeanItemContainer<Map> 
  implements Serializable {
  private static PersistenceManager pm = PMF.get().getPersistenceManager();

  public MapContainer() throws InstantiationException, 
    IllegalAccessException {
	super(Map.class);
  }
  
  public static MapContainer getMyMaps(String placeName)
  {
	  return null;
//	  Query query = pm.newQuery(Map.class);
//	  query.setFilter("imageUrl == imageUrlParam");
//	  query.declareParameters("String imageUrlParam");
//	  
//	  try {
//		  List<Map> results = (List<Map>) query.execute(imageUrl);
//		  if (results.iterator().hasNext()) {
//			  for (Map map: results) {
//				  return map;
//			  }
//		  }
//	  } finally {
//		  query.closeAll();
//	  }
//	  
//	  return null;
//	  mapContainer.addBean(map);
	  
//	  MapContainer mapContainer = null;
//	  try
//	  {    
//	    mapContainer = new MapContainer();
//	    
//		Statement select = conn.createStatement();
//		String selectStatement= "SELECT map.Image_Url, map.Description, map.Width, map.Height from map, Place where map.Place_id = place.Id and place.Name="+
//			 "\""+ placeName + "\";";
//		System.out.println(selectStatement);
//		ResultSet result = select.executeQuery(selectStatement);
//		
//		while (result.next()) {		
//			 System.out.println(result.getString(1));
//			 System.out.println(result.getString(2));
//		     Map map = new Map();
//		     map.setImageUrl(result.getString(1));
//		//     map.setMapIcon(new Embedded("Map", new ThemeResource(map.getImageUrl())));
//		     map.setMapDescription(result.getString(2));
//		     map.setMapWidth(result.getInt(3));
//		     map.setMapHeight(result.getInt(4));
//		     mapContainer.addBean(map);
//		}
//	  }  	
//	  catch (SQLException e){
//	      e.printStackTrace();
//	  } catch (IllegalAccessException e) {
//		       e.printStackTrace();    
//      } catch (InstantiationException e) {
//		    e.printStackTrace(); 
//	       } 
//      return mapContainer;
  }
  
  public static Map getMap(String imageUrl)
  {
	  
	  Query query = pm.newQuery(Map.class);
	  query.setFilter("imageUrl == imageUrlParam");
	  query.declareParameters("String imageUrlParam");
	  
	  try {
		  List<Map> results = (List<Map>) query.execute(imageUrl);
		  if (results.iterator().hasNext()) {
			  for (Map map: results) {
				  return map;
			  }
		  }
	  } finally {
		  query.closeAll();
	  }
	  
	  return null;
	  
//	  Map map = new Map();
//	  try
//	  {
//	    if (conn== null) {
//			  conn=getConn();
//		    }
//	    
//		Statement select = conn.createStatement();
//		String selectStatement= "SELECT map.Image_Url, map.Description, map.Width, map.Height, map.Scale from map where map.Image_Url="+
//			 "\""+ mapImageUrl + "\";";
//		System.out.println(selectStatement);
//		ResultSet result = select.executeQuery(selectStatement);
//
//		
//		while (result.next()) {
//			
//		map.setImageUrl(result.getString(1));
//		//map.setMapIcon(new Embedded("Map", new ThemeResource(map.getImageUrl())));
//		map.setMapDescription(result.getString(2));
//		map.setMapWidth(result.getInt(3));
//		map.setMapHeight(result.getInt(4));
//	    map.setMapScale(result.getFloat(5));
//		}
//	  }  	
//	  catch (SQLException e){
//	      e.printStackTrace();
//	  }
  //    return map;
  }
  
 public static Boolean saveMap(Map map) {
	 Key k = KeyFactory.createKey(Map.class.getSimpleName(), map.getMapName());
	 map.setKey(k);
	 try {
		 pm.makePersistent(map);
	 } finally {
		 pm.close();
	 }
	 return true;
 }

}
