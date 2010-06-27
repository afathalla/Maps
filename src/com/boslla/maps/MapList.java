package com.boslla.maps;

import com.boslla.maps.containers.*;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class MapList extends Table {
  public static final Object[] COLUMN_ORDER = new Object[]{"mapIcon","mapDescription"};
  private  String[] Alignment = new String[]{ALIGN_LEFT,ALIGN_LEFT};
  
  public MapList (MapsApplication app) {	  
  }
  public void refreshDataSource(MapContainer DataSource) {
    setContainerDataSource(DataSource);	  
    setVisibleColumns(COLUMN_ORDER);
    //setColumnHeaderMode(COLUMN_HEADER_MODE_HIDDEN);
    setColumnAlignments(Alignment);
    
    setColumnHeader("mapIcon","Map");
    setColumnHeader("mapDescription","Description");
    
    setPageLength(0);
	setWidth(100, TextField.UNITS_PERCENTAGE);
    //setHeight(400, TextField.UNITS_PIXELS);
	setSelectable(true);
	setImmediate(true);
  }
}
