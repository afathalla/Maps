package com.boslla.maps.containers;

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

public void setPlaceLongitude(double placeLongitude) {	
	  this.placeLongitude = placeLongitude ;
}
public double getPlaceLongitude() {
	return placeLongitude;
}

public void setPlaceLatitude(double placeLatitude) {	
	  this.placeLatitude = placeLatitude ;
}
public double getPlaceLatitude() {
	return placeLatitude;
}

  private String placeName;
  private String placeDescription;
  private String placeLocation;
  private String placeType;
  private Embedded placeIcon;
  private double placeLongitude;
  private double placeLatitude;
  
}