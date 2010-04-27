package com.example.maps.containers;

public class Place {
  private String placeName="";
  public String getPlaceName() {
	return placeName;
}
public void setPlaceName(String placeName) {
	this.placeName = placeName;
}
public int getLat() {
	return lat;
}
public void setLat(int lat) {
	this.lat = lat;
}
public int getLng() {
	return lng;
}
public void setLng(int lng) {
	this.lng = lng;
}
private int lat;
  private int lng;
}
