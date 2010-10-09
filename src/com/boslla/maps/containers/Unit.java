package com.boslla.maps.containers;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.vaadin.ui.Embedded;

@PersistenceCapable
public class Unit implements java.io.Serializable {
	
  public String getName() {
	return name;
  }
  
  public void setName(String unitName) {
	this.name = unitName;
  }
  
//  public int getId() {
//		return id;
//  }
// 
//  public void setId(int unitId) {
//	this.id = unitId;
//  }
  
  public int getX() {
	return x;
  }
  public void setX(int x) {
	this.x = x;
  }
  public int getY() {
	return y;
  }
  public void setY(int y) {
	this.y = y;
  }
  public void setIconUrl(String unitIconUrl) {
	this.iconUrl = unitIconUrl;
  }
  public String getIconUrl() {
	return iconUrl ;
  }
  
//  public void setUnitIcon (Embedded unitIcon) {
//		this.unitIcon = unitIcon;
//	  }
//  public Embedded getUnitIcon() {
//		return unitIcon;
//	  }
	  
  public void setDescription(String description) {
	this.description = description;
  }
  public String getDescription() {
	return description;
  }
  public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
 public String getImageUrl() {
	return imageUrl;
}

//public void setMapDescription (String mapDescription)
//{
//	this.mapDescription = mapDescription;
//}
//public String getMapDescription() {
//	return mapDescription;
//} 

//public void setMapImageUrl(String mapImageUrl) {
//		this.mapImageUrl = mapImageUrl;
//	}
//public String getmapImageUrl() {
//		return mapImageUrl;
//	}

public void setType(String unitType) {
	this.type = unitType;
  }
  public String getType() {
	return type;
  }
  
  public Key getKey() {
		return key;
  }
  public void setKey(Key key) {
		this.key = key;
  }
  
  public void setMap(Map map) {
	this.map = map;
  }

  public Map getMap() {
	return map;
  }

//  public void setPlaceName(String placeName) {
//	  	this.placeName = placeName;
//	}
//  public String getPlaceName() {
//		return placeName;
//	}
//	  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;
  @Persistent
  private int x;
  @Persistent
  private int y;
  @Persistent
  private String name;
  @Persistent
  private String description;
 // private String mapImageUrl;
  //private String mapDescription;
  @Persistent
  private String imageUrl;
  
  private String iconUrl;
//  private Embedded unitIcon;
  @Persistent
  private String type;
//  private String placeName;
//  @Persistent
//  private int id;
  @Persistent
  private Map map;
  
}