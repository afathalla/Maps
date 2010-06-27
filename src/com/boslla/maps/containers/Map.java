package com.boslla.maps.containers;

import com.vaadin.ui.Embedded;

public class Map {
	
  public void setMapName(String mapName) {
	this.mapName = mapName;
  }
  public String getMapName() {
		return mapName;
	  }

  public void setMapDescription(String mapDescription) {
	this.mapDescription = mapDescription;
  }
  public String getMapDescription() {
	return mapDescription;
  }
  
  public void setImageUrl(String imageUrl) {	
	  this.imageUrl = imageUrl;
}
public String getImageUrl() {
	return imageUrl;
}

public void setMapWidth(int mapWidth) {	
	  this.mapWidth = mapWidth ;
}
public int getMapWidth() {
	return mapWidth;
}

public void setMapHeight(int mapHeight) {	
	  this.mapHeight = mapHeight ;
}
public int getMapHeight() {
	return mapHeight;
}

public void setMapIcon(Embedded mapIcon) {
	this.mapIcon = mapIcon;
	this.mapIcon.setWidth("100");
	this.mapIcon.setHeight("100");
	}
public Embedded getMapIcon() {
			return mapIcon;
		  }	 

  private String mapName;
  private String mapDescription;
  private String imageUrl;
  private Embedded mapIcon;
  private int mapWidth;
  private int mapHeight;
}