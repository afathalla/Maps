package com.boslla.maps.containers;

import com.vaadin.ui.Embedded;
import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdGeneratorStrategy;


@PersistenceCapable
public class Map implements java.io.Serializable{
	
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

  public void setMapScale(float mapScale) {	
	  this.mapScale = mapScale ;
  }
  public float getMapScale() {
	return mapScale;
  }

//public void setMapIcon(Embedded mapIcon) {
//	this.mapIcon = mapIcon;
//	this.mapIcon.setWidth("100");
//	this.mapIcon.setHeight("100");
//}
//public Embedded getMapIcon() {
//	return mapIcon;
//}	 
  public Key getKey() {
	return key;
  }
  public void setKey(Key key) {
	this.key = key;
  }

  public void setPlace(Place place) {
	  this.place = place;
  }
  public Place getPlace() {
	  return place;
  }

@PrimaryKey
@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
private Key key;
@Persistent
private String mapName;
@Persistent
private String mapDescription;
@Persistent
private String imageUrl;
//@Persistent
//private Embedded mapIcon;
@Persistent
private int mapWidth;
@Persistent
private int mapHeight;
@Persistent
private float mapScale;
@Persistent
private Place place;
}