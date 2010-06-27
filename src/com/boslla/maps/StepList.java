package com.boslla.maps;

import com.boslla.maps.containers.*;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class StepList extends Table {
	
	  public static final Object[] COLUMN_ORDER = new Object[]{"step","direction"};
	  private  String[] Alignment = new String[]{ALIGN_LEFT,ALIGN_LEFT};

	  public StepList (MapsApplication app) {	  
	  }

	  public void refreshDataSource(MapsApplication app, StepContainer DataSource) {
		    setContainerDataSource(DataSource);	  
		    setVisibleColumns(COLUMN_ORDER);
		    setColumnHeaderMode(COLUMN_HEADER_MODE_HIDDEN);
		    setColumnAlignments(Alignment);
		    setImmediate(true);

		    setPageLength(0);
			setWidth(100, TextField.UNITS_PERCENTAGE);
		  }
}
