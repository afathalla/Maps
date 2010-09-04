package com.boslla.maps.containers;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.vaadin.ui.Embedded;

public class Place {
	
  public String getPlaceName() {
	return placeName;
  }
  
  public void setPlaceName(String placeName) {
	this.placeName = placeName;
  }

  public void setPlaceDescription(String placeDescription) {
	this.placeDescription = placeDescription;
  }
  public String getPlaceDescription() {
	return placeDescription;
  }
  
  public void setPlaceType(String placeType) {
		this.placeType = placeType;
	  }
	  public String getPlaceType() {
		return placeType;
	  }

public void setPlaceLocation(String placeLocation) {
		this.placeLocation = placeLocation;
	  }
public String getPlaceLocation() {
		return placeLocation;
	  }

public void setPlaceIcon(Embedded placeIcon) {
	this.placeIcon = placeIcon;
	this.placeIcon.setWidth("100");
	this.placeIcon.setHeight("100");
}
public Embedded getPlaceIcon() {
	return placeIcon;
}
public Key getKey() {
	return key;
}

@PrimaryKey
@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
private Key key;
@Persistent
private String placeName;
@Persistent
private String placeDescription;
@Persistent
private String placeLocation;
@Persistent
private String placeType;
@Persistent
private Embedded placeIcon;
  
}