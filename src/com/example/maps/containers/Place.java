package com.example.maps.containers;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.gwt.client.ui.Icon;
import com.vaadin.ui.Embedded;

public class Place {
  public String getPlaceName() {
	return placeName;
  }
  public void setPlaceName(String placeName) {
	this.placeName = placeName;
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
  public void setPlaceIcon(Embedded placeIcon) {
	this.placeIcon = placeIcon;
  }
  public Embedded getPlaceIcon() {
	return placeIcon;
  }
  private int x;
  private int y;
  private String placeName;
  private Embedded placeIcon;
}
