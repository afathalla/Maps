package com.boslla.maps.containers;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.vaadin.ui.Embedded;

@PersistenceCapable
public class Place implements java.io.Serializable {
	
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

//public void setPlaceIcon(Embedded placeIcon) {
//	this.placeIcon = placeIcon;
//	this.placeIcon.setWidth("100");
//	this.placeIcon.setHeight("100");
//}
//public Embedded getPlaceIcon() {
//	return placeIcon;
//}
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
public Key getKey() {
	return key;
}
public void setKey(Key key) {
	this.key = key;
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
private double placeLongitude;
@Persistent
private double placeLatitude;

//@Persistent
//private Embedded placeIcon;
//@Persistent(mappedBy = "mapPlace")
//private List<Map> placeMaps;

}