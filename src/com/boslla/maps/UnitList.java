package com.boslla.maps;

import com.boslla.maps.containers.*;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class UnitList extends Table {
  public static final Object[] ALL_COLUMNS = new Object[]{"unitIcon","unitName","mapDescription"};
  public static final Object[] OPTION_COLUMNS = new Object[]{"unitName","mapDescription"};
  private  String[] Option_Alignment = new String[]{ALIGN_LEFT,ALIGN_LEFT};
  private  String[] All_Alignment = new String[]{ALIGN_LEFT,ALIGN_LEFT,ALIGN_LEFT};
  
  public UnitList (MapsApplication app) {	  
  }
  public void refreshDataSource(MapsApplication app, UnitContainer DataSource) {
    setContainerDataSource(DataSource);	  
    setVisibleColumns(ALL_COLUMNS);
    //setColumnHeaderMode(COLUMN_HEADER_MODE_HIDDEN);
    setColumnAlignments(All_Alignment);
    setColumnHeader("unitIcon","Position");
    setColumnHeader("unitName","Name");
    setColumnHeader("mapDescription","Location");
    setPageLength(0);
    
	setWidth(100, TextField.UNITS_PERCENTAGE);
    //setHeight(300, TextField.UNITS_PIXELS);
	setSelectable(true);
	setImmediate(true);
    setSortAscending(true);
  }
  public void refreshOptionsDataSource(MapsApplication app, UnitContainer DataSource) {
	    setContainerDataSource(DataSource);	  
	    setVisibleColumns(OPTION_COLUMNS);
	    //setColumnHeaderMode(COLUMN_HEADER_MODE_HIDDEN);
	    setColumnAlignments(Option_Alignment);
	    setColumnHeader("unitName","Name");
	    setColumnHeader("mapDescription","Location");
	    setPageLength(0);
	    
		setWidth(100, TextField.UNITS_PERCENTAGE);
	    //setHeight(300, TextField.UNITS_PIXELS);
		setSelectable(true);
		setImmediate(true);
	    setSortAscending(true);
	  }
}
