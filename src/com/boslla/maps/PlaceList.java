package com.boslla.maps;

import com.boslla.maps.containers.*;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class PlaceList extends Table {
  public static final Object[] COLUMN_ORDER = new Object[]{"placeIcon","placeType","placeLocation","placeDescription"};
  private  String[] Alignment = new String[]{ALIGN_LEFT,ALIGN_LEFT,ALIGN_LEFT,ALIGN_LEFT};
  
  public PlaceList (MapsApplication app) {
  }
  public void refreshDataSource(MapsApplication app, PlaceContainer DataSource) {
    setContainerDataSource(DataSource);
    setVisibleColumns(COLUMN_ORDER);
    //setColumnHeaderMode(COLUMN_HEADER_MODE_HIDDEN);
    setColumnHeader("placeIcon","Place");
    setColumnHeader("placeType","Category");
    setColumnHeader("placeDescription","Description");
    setColumnHeader("placeLocation","Location");
    setColumnAlignments(Alignment);
    setPageLength(0);
    
	setWidth(100, TextField.UNITS_PERCENTAGE);
    //setHeight(400, TextField.UNITS_PIXELS);
    
	setSelectable(true);
	setImmediate(true);
    setSortAscending(true);
  }
}