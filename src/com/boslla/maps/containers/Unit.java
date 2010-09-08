package com.boslla.maps.containers;

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

public void setUnitType(String unitType) {
	this.unitType = unitType;
  }
  public String getUnitType() {
	return unitType;
  }
  
  private int x;
  private int y;
  private String unitName;
  private String description;
  private String mapImageUrl;
  private String mapDescription;
  private Embedded imageUrl;
  private String unitIconUrl;
  private Embedded unitIcon;
  private String unitType;
  
}