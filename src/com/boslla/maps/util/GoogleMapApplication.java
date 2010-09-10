package com.boslla.maps.util;

import java.awt.geom.Point2D;
import org.vaadin.hezamu.googlemapwidget.GoogleMap;
import org.vaadin.hezamu.googlemapwidget.overlay.BasicMarker;

import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class GoogleMapApplication extends Application{
	
	public void init()
	{		
	    Window mainWindow = new Window("Google Maps Application");
	    Label label = new Label("Hello Vaadin uservvvv");
	    mainWindow.addComponent(label);
	    setMainWindow(mainWindow);
	    
	    GoogleMap googleMap = new GoogleMap(this, new Point2D.Double(22.3, 60.4522), 8);
		googleMap.setWidth("640px");
		googleMap.setHeight("480px");

		// Create a marker at the IT Mill offices
		googleMap.addMarker(new BasicMarker(1L, new Point2D.Double(22.3, 60.4522), "Test marker"));
		
		mainWindow.addComponent(googleMap);
	}
}
