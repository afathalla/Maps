package com.example.maps;

import com.vaadin.Application;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.example.maps.containers.*;
import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

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
	  private MapView mapView= new MapView();
	  private Connection conn=null;
	  private PlaceContainer placeDataSource = null;
	  private PlaceList placeList = new PlaceList(this); //Table that will hold places matched by user query
	  private Boolean startPresented = false ; // Indicates that list of start locations is displayed
	  private Boolean endPresented = false; // Indicates that list of end locations is displayed
	  private Boolean startSelected = false; //Indicates correctly identified start location
	  private Boolean endSelected = false; //Indicates correctly identified end location
	  private  ArrayList<Place> displayedPlaces = null;
	  private  ArrayList<Place> selectedPlaces = new ArrayList<Place>();
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

		VerticalLayout searchLayout= new VerticalLayout();
		startText.setInputPrompt("Enter Starting Point");
		endText.setInputPrompt("Enter Destination");
		startText.setWidth(175, TextField.UNITS_PIXELS);
		endText.setWidth(175, TextField.UNITS_PIXELS);
		
		placeList.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
		placeList.setSelectable(true);
		placeList.setImmediate(true);
		placeList.addListener(new Property.ValueChangeListener() {
		  public void valueChange(ValueChangeEvent event) {
		    Place selectedPlace= (Place)placeList.getValue();
		    if (startPresented) {
			  startText.setValue(selectedPlace.getPlaceName());
			  startPresented = false;
			  startSelected = true;
			  displayedPlaces.clear();
			  selectedPlaces.add(selectedPlace);
			  mapView.setDisplayedPlaces(selectedPlaces);
		    }
		    else if (endPresented)
		    {
		      endText.setValue(selectedPlace.getPlaceName());
		      endPresented = false;
		      endSelected = true;
		      displayedPlaces.clear();
		      selectedPlaces.add(selectedPlace);
			  mapView.setDisplayedPlaces(selectedPlaces);
		    }
			placeList.setVisible(false);
			locationLabel.setVisible(false);
		  }
		});
		
		searchLayout.setSpacing(true);
		searchLayout.addComponent(searchLabel);
		searchLayout.addComponent(startText);
		searchLayout.addComponent(endText);
		searchLayout.addComponent(searchButton);
		searchLayout.addComponent(logoutButton);
		searchLayout.addComponent(locationLabel);
		searchLayout.addComponent(placeList);
		
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
		  displayedPlaces = new ArrayList<Place>();
		  String startTextValue=startText.getValue().toString().trim();
          if (!startTextValue.isEmpty() && !startSelected) {
        	System.out.println("In Start Selection");
		    placeDataSource=PlaceContainer.getSimilarPlaces(startText.getValue().toString());
		    if (placeDataSource.size() == 0) { // Search returns no items
		    	locationLabel.setVisible(true); 
		    	locationLabel.setValue("No items match your chosen Starting Point");
		    } else if (placeDataSource.size()== 1) { // Only one result returned
		        Place foundPlace = placeDataSource.getIdByIndex(0);
		        displayedPlaces.add(foundPlace);
		        selectedPlaces.add(foundPlace);
		    	startText.setValue(foundPlace.getPlaceName()); //Set the value 
		      } else { // More than one item match search 
		    	  for (int i=0;i<placeDataSource.size();i++) {
		    	    displayedPlaces.add(placeDataSource.getIdByIndex(i));
		    	  }
		          locationLabel.setValue("Start location could be one yof these");
		          placeList.setVisible(true);
		          placeList.refreshDataSource(this);
		          startPresented = true;
		        }	
           }
          String endTextValue=endText.getValue().toString().trim();
          if (!endTextValue.isEmpty() && !endSelected) {			
        	System.out.println("In End Selection");
  		    placeDataSource=PlaceContainer.getSimilarPlaces(endText.getValue().toString());
  		    if (placeDataSource.size() == 0) { // Search returns no items
  		    	locationLabel.setVisible(true); 
  		    	locationLabel.setValue("No items match your chosen Ending Point");
  		    } else if (placeDataSource.size()== 1) { // Only one result returned
  		        Place foundPlace = placeDataSource.getIdByIndex(0);
  		        displayedPlaces.add(foundPlace);
  		        selectedPlaces.add(foundPlace);
  		    	endText.setValue(foundPlace.getPlaceName()); //Set the value 
  		      } else { // More than one item match search 
		    	  for (int i=0;i<placeDataSource.size();i++) {
			    	    displayedPlaces.add(placeDataSource.getIdByIndex(i));
			    	  }
  		    	  System.out.println("More than one place found");
  		          locationLabel.setValue("End location could be one of these");
  		          locationLabel.setVisible(true);
  		          locationLabel.setImmediate(true);
  		          placeList.setVisible(true);
  		          placeList.refreshDataSource(this);
  		          endPresented = true;
  		        }
            }
		  if (displayedPlaces.size() > 0) {//If there are items found 
	        mapView.setDisplayedPlaces(displayedPlaces);
		  }	
		  if (selectedPlaces.size()== 2) {
		    Place startPlace = selectedPlaces.get(0);
		    Place endPlace = selectedPlaces.get(1);
		    System.out.println("Calculating path between: " + startPlace.getPlaceName() +
		    			       "and " + endPlace.getPlaceName());
		    mapView.calculatePath(startPlace,endPlace);
		  }
		  setMainComponent(mapView);
		
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

	public PlaceContainer getPlaceDataSource() {
      return placeDataSource;
	}
}
