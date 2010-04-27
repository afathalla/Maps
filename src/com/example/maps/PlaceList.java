package com.example.maps;

import com.example.maps.containers.*;
import com.vaadin.ui.Table;

public class PlaceList extends Table {
  public PlaceList (MapsApplication app) {
    setContainerDataSource(app.getPlaceDataSource());	  
  }
  public void refreshDataSource(MapsApplication app) {
	  setContainerDataSource(app.getPlaceDataSource());	  
  }
}
