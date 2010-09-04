package com.boslla.maps.containers;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.vaadin.ui.Embedded;

public class Unit {
	
  public String getUnitName() {
	return unitName;
  }
  
  public void setUnitName(String unitName) {
	this.unitName = unitName;
  }
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
  public void setUnitIconUrl(String unitIconUrl) {
	this.unitIconUrl = unitIconUrl;
  }
  public String getUnitIconUrl() {
	return unitIconUrl ;
  }
  
  public void setUnitIcon (Embedded unitIcon) {
		this.unitIcon = unitIcon;
	  }
  public Embedded getUnitIcon() {
		return unitIcon;
	  }
	  
  public void setDescription(String description) {
	this.description = description;
  }
  public String getDescription() {
	return description;
  }
  public void setImageUrl(Embedded imageUrl) {
	this.imageUrl = imageUrl;
}
 public Embedded getImageUrl() {
	return imageUrl;
}

public void setMapDescription (String mapDescription)
{
	this.mapDescription = mapDescription;
}
public String getMapDescription() {
	return mapDescription;
} 

public void setMapImageUrl(String mapImageUrl) {
		this.mapImageUrl = mapImageUrl;
}
public String getmapImageUrl() {
		return mapImageUrl;
}
  
public Key getKey() {
	return key;
}

@PrimaryKey
@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
private Key key;
@Persistent
private int x;
@Persistent
private int y;
@Persistent
private String unitName;
@Persistent
private String description;
@Persistent
private String mapImageUrl;
@Persistent
private String mapDescription;
@Persistent
private Embedded imageUrl;
@Persistent
private String unitIconUrl;
@Persistent
private Embedded unitIcon;
  
}