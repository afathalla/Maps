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

public class UnitContainer extends BeanItemContainer<Unit> 
  implements Serializable {
  private static Connection conn=null;
  public UnitContainer() throws InstantiationException, 
    IllegalAccessException {
 
	super (Unit.class);
  }
  
  public static UnitContainer getSimilarUnits(String unitName) { 
	  UnitContainer unitContainer = null;
	  try{
		unitContainer = new UnitContainer();  
	    if (conn== null) {
		  conn=getConn();
	    }
		  Statement select = conn.createStatement();
		  String selectStatement= "SELECT unit.Name, X, Y, unit.Description,unit.Image_Url, map.Image_Url, map.Description, unit_type.Name from unit, map, unit_type where unit.Map_ID=map.Id and unit.Unit_type_id = unit_type.Id and unit.name LIKE " +
            "\"%"+ unitName + "%\";";
		   System.out.println(selectStatement);
		   ResultSet result = select.executeQuery(selectStatement);
		   int resultCounter = 1;
		   
		   while (result.next()) {
			 System.out.println(result.getString(1));
			 System.out.println(result.getString(2));
			 System.out.println(result.getString(3));
		     Unit similarPlace = new Unit();
		     similarPlace.setUnitName(result.getString(1));
		     similarPlace.setX(result.getInt(2));
		     similarPlace.setY(result.getInt(3));
		     similarPlace.setDescription(result.getString(4));
		     similarPlace.setImageUrl(new Embedded(null,new ThemeResource(result.getString(5))));
		     similarPlace.setMapImageUrl(result.getString(6));
		     similarPlace.setMapDescription(result.getString(7));
		     similarPlace.setUnitType(result.getString(8));
		     similarPlace.setUnitIconUrl("numbers/location_"+resultCounter+".png");
		     similarPlace.setUnitIcon(new Embedded(null,new ThemeResource("numbers/location_"+resultCounter+".png")));
		     resultCounter++;
		     
		     unitContainer.addBean(similarPlace);
		   }
	  } catch (SQLException e){
	      e.printStackTrace();
	    } catch (InstantiationException e) {
		    e.printStackTrace(); 
	       } catch (IllegalAccessException e) {
		       e.printStackTrace();    
	         }
	   //notifyListeners();
	   return unitContainer;
  }
  
  public static Unit getUnit(String unitName) { 
	  
	  Unit unit = new Unit();
		
	  try{
		
	    if (conn== null) {
		  conn=getConn();
	    }
		  Statement select = conn.createStatement();
		  int resultCounter = 1;		  
			  
		   String selectStatement= "SELECT unit.Name, X, Y, unit.Description,unit.Image_Url, map.Image_Url, map.Description, unit_type.Name from unit, unit_type, map where unit.Unit_type_id = unit_type.Id and unit.Map_ID=map.Id and unit.name = " + "\"" + unitName +"\"";
		   System.out.println(selectStatement);
		   ResultSet result = select.executeQuery(selectStatement);
		   
		   while (result.next()) {
			 System.out.println(result.getString(1));
			 System.out.println(result.getString(2));
			 System.out.println(result.getString(3));

		     unit.setUnitName(result.getString(1));
		     unit.setX(result.getInt(2));
		     unit.setY(result.getInt(3));
		     unit.setDescription(result.getString(4));
		     unit.setImageUrl(new Embedded(null,new ThemeResource(result.getString(5))));
		     unit.setMapImageUrl(result.getString(6));
		     unit.setMapDescription(result.getString(7));
		     unit.setUnitType(result.getString(8));
		     unit.setUnitIconUrl("numbers/location_"+resultCounter+".png");
		     unit.setUnitIcon(new Embedded(null,new ThemeResource("numbers/location_"+resultCounter+".png")));
		     resultCounter++;

		   }

	  } catch (SQLException e){
	      e.printStackTrace();
	    } 
	   //notifyListeners();
	   return unit;
  }
  
  public static UnitContainer getAllUnits() { 
	  UnitContainer unitContainer = null;
	  try{
		unitContainer = new UnitContainer();  
	    if (conn== null) {
		  conn=getConn();
	    }
	    Statement select = conn.createStatement();
	    String selectStatement= "SELECT unit.Name, X, Y, unit.Description,unit.Image_Url, map.Image_Url, map.Description, unit_type.Name from unit, unit_type, map where unit.Unit_type_id = unit_type.Id and unit.Map_ID=map.Id";
		System.out.println(selectStatement);
		ResultSet result = select.executeQuery(selectStatement);
		   
		   int resultCounter = 1;
		   
		   while (result.next()) {
			   
		     Unit unit= new Unit();
		     unit.setUnitName(result.getString(1));
		     unit.setX(result.getInt(2));
		     unit.setY(result.getInt(3));
		     unit.setDescription(result.getString(4));
		     unit.setImageUrl(new Embedded(null,new ThemeResource(result.getString(5))));
		     unit.setMapImageUrl(result.getString(6));
		     unit.setMapDescription(result.getString(7));
		     unit.setUnitType(result.getString(8));
		     unit.setUnitIconUrl("numbers/location_"+resultCounter+".png");
		     unit.setUnitIcon(new Embedded(null,new ThemeResource("numbers/location_"+resultCounter+".png")));
		     resultCounter++;
		     
		     unitContainer.addBean(unit);
		   }
	  } catch (SQLException e){
	      e.printStackTrace();
	    } catch (InstantiationException e) {
		    e.printStackTrace(); 
	       } catch (IllegalAccessException e) {
		       e.printStackTrace();    
	         }
	   //notifyListeners();
	   return unitContainer;
  }
  
  public static UnitContainer addUnits(Unit unit1, Unit unit2)
  {
	  UnitContainer unitContainer = null;
	  try {
		  unit1.setUnitIconUrl("numbers/location_"+ 1 + ".png");
		  unit2.setUnitIconUrl("numbers/location_"+ 2 + ".png");
		  unit1.setUnitIcon(new Embedded(null,new ThemeResource("numbers/location_"+1+".png")));
		  unit2.setUnitIcon(new Embedded(null,new ThemeResource("numbers/location_"+2+".png")));
		  unitContainer = new UnitContainer();
		  unitContainer.addBean(unit1);
		  unitContainer.addBean(unit2); 
		  
	  }  catch (InstantiationException e) {
		    e.printStackTrace(); 
	       } catch (IllegalAccessException e) {
		       e.printStackTrace();    
	         }
	  return unitContainer;
  }

// private static void notifyListeners() {
//      ArrayList<ItemSetChangeListener> cl = (ArrayList<ItemSetChangeListener>) listeners.clone();
//      ItemSetChangeEvent event = new ItemSetChangeEvent() {
//          public Container getContainer() {
//              return UnitContainer.this;
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
