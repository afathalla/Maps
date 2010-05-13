package com.example.maps;

import com.example.maps.containers.*;
import com.vaadin.ui.Table;

public class PlaceList extends Table {
  public static final Object[] COLUMN_ORDER = new Object[]{"placeName"};
  
  public PlaceList (MapsApplication app) {
     
  }
  public void refreshDataSource(MapsApplication app) {
	  setContainerDataSource(app.getPlaceDataSource());	  
	  setVisibleColumns(COLUMN_ORDER);
  }
}
