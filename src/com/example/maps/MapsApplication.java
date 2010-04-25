package com.example.maps;


import java.sql.ResultSet;
import java.sql.Statement;

import com.vaadin.Application;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MapsApplication extends Application implements Button.ClickListener{
	  private SplitPanel horizontalSplit = new SplitPanel(
	            SplitPanel.ORIENTATION_HORIZONTAL);
	  private Button searchButton = new Button("Search");
	  private Button logoutButton = new Button("Logout");
	  private TextField startText= new TextField();
	  private TextField endText= new TextField();
	  private Label locationLabel= new Label();
	  private Label searchLabel= new Label ("<h2>Find Directions</h2>",Label.CONTENT_XHTML);
	  private ProgressIndicator progressIndicator= new ProgressIndicator(new Float(0.0));
	//  private MapPanel mapPanel = new MapPanel();
	  private MapView mapView= new MapView();
	  private Connection conn=null;
	  
	  @Override
	public void init() {
		buildLayout();
	
	}
	
	private void buildLayout(){
		Window mainWindow = new Window("Maps Application");
		VerticalLayout layout= new VerticalLayout();
		layout.setSizeFull();
		
		horizontalSplit.setFirstComponent(buildSearchBox());
		horizontalSplit.setSplitPosition(200, SplitPanel.UNITS_PIXELS);
		
		layout.addComponent(horizontalSplit);
		layout.setExpandRatio(horizontalSplit, 1);
		
		
		
		mainWindow.setContent(layout);
		setMainWindow(mainWindow);
		
	}
	
	private VerticalLayout buildSearchBox()
	{
		try {
		  if (conn==null) {
		    Connection conn = getConn();
		  }
		  Statement select = conn.createStatement();
		  ResultSet result = select.executeQuery("SELECT * from places order by id asc;");
		  while (result.next()) {
			  locationLabel.setCaption(result.getString(2));
		  }
		result.close();
		conn.close();
		} catch (Exception e) {
		  System.err.println("Error executing Select Statement");
		  e.printStackTrace();
		}
		VerticalLayout searchLayout= new VerticalLayout();
		startText.setInputPrompt("Enter Start Location");
		endText.setInputPrompt("Enter End Location");
		startText.setWidth(175, TextField.UNITS_PIXELS);
		endText.setWidth(175, TextField.UNITS_PIXELS);
		searchLayout.setSpacing(true);
		searchLayout.addComponent(searchLabel);
		searchLayout.addComponent(startText);
		searchLayout.addComponent(endText);
		searchLayout.addComponent(searchButton);
		searchLayout.addComponent(logoutButton);
		searchLayout.addComponent(locationLabel);
		
		searchButton.addListener((ClickListener)this);
		logoutButton.addListener((ClickListener)this);
		return searchLayout;
	}

	
	public void buttonClick(ClickEvent event) {
		
		Button sourceButton= event.getButton();
		if (sourceButton== logoutButton)
			getMainWindow().getApplication().close();
		else if (sourceButton == searchButton)
		{
			if (conn == null) {
				Connection conn = getConn();	
			}
			setMainComponent(mapView);
//			mapPanel.fetchMap();
				
		}
			
	}
	
	private void setMainComponent(Component c){
		horizontalSplit.setSecondComponent(c);
	}
	private Connection getConn() {
		Connection conn = null;
        String url          = "jdbc:mysql://localhost:3306/";
        String db           = "makany_dev";
        String driver       = "com.mysql.jdbc.Driver";
        String user         = "root";
        String pass         = "root";
            
	    try {
	            Class.forName(driver).newInstance();
	    } catch (InstantiationException e) {
	            e.printStackTrace();
	    } catch (IllegalAccessException e) {
	            e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	    }
	    try {       
	                    conn = DriverManager.getConnection(url+db, user, pass);
	    } catch (SQLException e) {
	                    System.err.println("Mysql Connection Error: ");
	            e.printStackTrace();
	    }
	            return conn;
  }
}
