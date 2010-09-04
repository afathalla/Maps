package com.boslla.maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.event.ShortcutAction.*;
import com.boslla.maps.containers.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Window.Notification;


public class MapsApplication extends Application implements Button.ClickListener,HttpServletRequestListener{
	  private SplitPanel horizontalSplit = new SplitPanel(
	            SplitPanel.ORIENTATION_HORIZONTAL);
	 
	  private SplitPanel verticalSplit = new SplitPanel(SplitPanel.ORIENTATION_VERTICAL);
//	  private SplitPanel verticalSplit2 = new SplitPanel(SplitPanel.ORIENTATION_VERTICAL);
	  private Button directionSearchButton = new Button("Get Direction");
	  private Button locationSearchButton = new Button("Spot Location");
	  private Button upperSearchButton = new Button("Spot Location");
	  private Button signOutButton = new Button("Sign Out");
	  private Button resetButton = new Button("Clear Search");
	  private Button clearMyPlacesButton = new Button("Clear My Places");
	  private Button myMapsButton = new Button("Back to My Maps");
	  private Button getDirectionFromHereButton = new Button("Get Direction From here");
	  private Button findAnotherLocationButton = new Button("Spot Another Location");
	  private Button findLocationOnMapButton = new Button("Find Location on Map");
	  private Button addToMyPlacesButton = new Button("Add Location to My Places");
	  private Button showStepsButton = new Button("Show Steps");
	  private Button hideStepsButton = new Button("Hide Steps");
	  //private Button hidePanelButton = new Button("<<");
	  //private Button showPanelButton = new Button(">>");
	  private TextField startText= new TextField();
	  private TextField locationSearchText= new TextField();
	  private TextField upperSearchText= new TextField();
	  private TextField endText= new TextField();
	  //private Label startLocationLabel= new Label("<h3></h3>",Label.CONTENT_XHTML);
	  //private Label endLocationLabel= new Label("<h3></h3>",Label.CONTENT_XHTML);
	  private Label locationLabel= new Label("<h3></h3>",Label.CONTENT_XHTML);
	  private Label homeLabel= new Label ("<h2>Welcome to BOSLLA.COM !!</h2></ br> You can find locations and directions to your favorite places here...",Label.CONTENT_XHTML);
	  private Label stepsTitleLabel = new Label ("<h2>Steps to Destination:</h2>",Label.CONTENT_XHTML);
	  private Label walkingDistance = new Label ("Total Distance:",Label.CONTENT_XHTML);
	  private Label walkingTime = new Label ("Total Walking Time:",Label.CONTENT_XHTML);
	  private ProgressIndicator progressIndicator= new ProgressIndicator(new Float(0.0));
	  private static MapView mapView= new MapView();
	  private UnitContainer unitDataSource = null;
	  private PlaceContainer placeDataSource = null;
	  private MapContainer mapDataSource = null;
	  private UnitContainer startUnitDataSource = null;
	  private UnitContainer endUnitDataSource = null;
	  private MapList mapList = new MapList(this); //Table that will hold units matched by user query
	  private PlaceList placeList = new PlaceList(this); //Table that will hold units matched by user query
	  private UnitList unitList = new UnitList(this); //Table that will hold units matched by user query
	  private UnitList directionLocationList = new UnitList(this);
	  private UnitList optionList = new UnitList(this); //Table that will hold units matched by user query
	  private UnitList resultList = new UnitList(this); //Table that will hold results matched by user query
	  private StepList stepList = new StepList(this);
	  private Boolean startSelected = false; //Indicates correctly identified start location
	  private Boolean endSelected = false; //Indicates correctly identified end location
	  private  ArrayList<Unit> displayedUnits = new ArrayList<Unit>();
	  private Unit startSelectedUnit;
	  private Unit endSelectedUnit;
	  private Window mainWindow= new Window("Maps Application");
	  private TabSheet tabSheet = new TabSheet();
      private VerticalLayout l1 = new VerticalLayout();
      private VerticalLayout l2 = new VerticalLayout();
      private VerticalLayout l3 = new VerticalLayout();
      private VerticalLayout l4 = new VerticalLayout();
      private VerticalLayout l5 = new VerticalLayout();
      private static VerticalLayout verticalViewLayout= new VerticalLayout();
      
      private Embedded startLocationIcon = new Embedded("", new ThemeResource("numbers/location_1.png"));
      private Embedded endLocationIcon = new Embedded("", new ThemeResource("numbers/location_2.png"));

      private HttpServletResponse response;
      private HttpServletRequest request;


	@Override
	public void init() {
		//setTheme("runo");
		//setTheme("chameleon");
		setTheme("reindeermods");
		buildLayout("mainView");
	}
	
	public void onRequestStart(HttpServletRequest request,
            HttpServletResponse response) {
		this.response = response;
		this.request = request;
		System.out.println("[Start of request");
		System.out.println(" Query string: " +
			request.getQueryString());
		System.out.println(" Path: " +
			request.getPathInfo());
			
	}
	public void onRequestEnd(HttpServletRequest request,
          HttpServletResponse response) {
		System.out.println(" End of request]");
}	
	private void buildLayout(String type){
				
		//Initialize text boxes
		startText.setInputPrompt("Enter Starting Point");
		endText.setInputPrompt("Enter Destination");
		startText.setWidth(175, TextField.UNITS_PIXELS);
		endText.setWidth(175, TextField.UNITS_PIXELS);
		locationSearchText.setInputPrompt("Location Search");
		locationSearchText.setWidth(175, TextField.UNITS_PIXELS);
		upperSearchText.setWidth(100, TextField.UNITS_PERCENTAGE);
		
		//Initialize Buttons
		upperSearchButton.setClickShortcut(KeyCode.ENTER);
		resetButton.setStyleName(Button.STYLE_LINK);
		resetButton.addListener((ClickListener)this);
		getDirectionFromHereButton.setStyleName(Button.STYLE_LINK);
		getDirectionFromHereButton.addListener((ClickListener)this);
		findAnotherLocationButton.setStyleName(Button.STYLE_LINK);
		showStepsButton.setStyleName(Button.STYLE_LINK);
		showStepsButton.addListener((ClickListener)this);
		hideStepsButton.setStyleName(Button.STYLE_LINK);
		hideStepsButton.addListener((ClickListener)this);
		myMapsButton.setStyleName(Button.STYLE_LINK);
		myMapsButton.addListener((ClickListener)this);
		findLocationOnMapButton.setStyleName(Button.STYLE_LINK);
		signOutButton.setStyleName(Button.STYLE_LINK);
		signOutButton.addListener((ClickListener)this);
		findLocationOnMapButton.addListener((ClickListener)this);
		directionSearchButton.addListener((ClickListener)this);
		directionSearchButton.setIcon(new ThemeResource("icons/search.gif"));
		locationSearchButton.setIcon(new ThemeResource("icons/search.gif"));
		locationSearchButton.addListener((ClickListener)this);
		findAnotherLocationButton.addListener((ClickListener)this);
		upperSearchButton.addListener((ClickListener)this);
		
		addToMyPlacesButton.setStyleName(Button.STYLE_LINK);
		addToMyPlacesButton.addListener((ClickListener)this);
		
		clearMyPlacesButton.setStyleName(Button.STYLE_LINK);
		clearMyPlacesButton.addListener((ClickListener)this);
		
		//Show & Hide Panel Buttons
//	    hidePanelButton.setDescription("Hide Panel");
//	    showPanelButton.setDescription("Show Panel");
//	    hidePanelButton.addListener((ClickListener)this);
//	    showPanelButton.addListener((ClickListener)this);
		
		//Initialize tabSheet
		l1.setMargin(true);	
		l2.setMargin(true);
		l3.setMargin(true);
		l4.setMargin(true);
		l5.setMargin(true);
		
	    l1.setWidth(20, TextField.UNITS_PERCENTAGE);
	    l2.setWidth(20, TextField.UNITS_PERCENTAGE);
	    l3.setWidth(20, TextField.UNITS_PERCENTAGE);
	    l4.setWidth(20, TextField.UNITS_PERCENTAGE);
	    l5.setWidth(20, TextField.UNITS_PERCENTAGE);
	    
	    tabSheet.setHeight("34px");
	    tabSheet.setWidth("100%");
	    tabSheet.setImmediate(true);
	    
        // First, a vertical SplitPanel
		VerticalLayout layout= new VerticalLayout();
		layout.setSizeFull();
		
		// Second, aHorizontal Split
		horizontalSplit.setFirstComponent(buildVerticalView());
		horizontalSplit.setSplitPosition(33, SplitPanel.UNITS_PERCENTAGE);
		horizontalSplit.setLocked(false);
//		horizontalSplit.setSecondComponent(hidePanelButton);
		
		//Another horizontal split
		
		AbsoluteLayout buttonBar = new AbsoluteLayout();
		buttonBar = buildButtonBar();
		
		verticalSplit.addComponent(buttonBar);
		verticalSplit.setSplitPosition(100, SplitPanel.UNITS_PERCENTAGE);
		verticalSplit.setLocked(true);
		
		layout.addComponent(verticalSplit);
		layout.setExpandRatio(verticalSplit, 1);
		layout.addComponent(horizontalSplit);
		layout.setExpandRatio(horizontalSplit,10);
	
		mainWindow.setContent(layout);
		setMainWindow(mainWindow);
	}
	
	private AbsoluteLayout buildButtonBar()
	{
		AbsoluteLayout upperViewLayout= new AbsoluteLayout();

		upperViewLayout.addComponent(signOutButton, "right: 1%; top: 1%;");
		
		HorizontalLayout searchBarLayout= new HorizontalLayout();
		searchBarLayout.setWidth(75, TextField.UNITS_PERCENTAGE);
		//searchBarLayout.setSpacing(true);
		//searchBarLayout.setMargin(true, false, true, false);
		
		Label brandLabel= new Label ("<h1><b>boslla.com</b></h1>",Label.CONTENT_XHTML);
		//brandLabel.setWidth(10, TextField.UNITS_PERCENTAGE);
		searchBarLayout.addComponent(brandLabel);
		searchBarLayout.setComponentAlignment(brandLabel, Alignment.MIDDLE_LEFT);
		searchBarLayout.addComponent(upperSearchText);
		searchBarLayout.setComponentAlignment(upperSearchText, Alignment.MIDDLE_LEFT);
		searchBarLayout.addComponent(upperSearchButton);
		searchBarLayout.setComponentAlignment(upperSearchButton, Alignment.MIDDLE_LEFT);
		
		upperViewLayout.addComponent(searchBarLayout);
		
		return upperViewLayout;
	}

/*****************************************************************************/
	
	private VerticalLayout buildFindDirection()
	{
		// Hide Labels and Lists
		locationLabel.setVisible(false);
		directionLocationList.setVisible(false);
		resultList.setVisible(false);
		showStepsButton.setVisible(false);
		hideStepsButton.setVisible(false);
		stepList.setVisible(false);
		walkingDistance.setVisible(false);
		walkingTime.setVisible(false);
		
		//Reset Parameters
		directionLocationList.removeAllItems();
		resultList.removeAllItems();

		//Initialize start and end Text Boxes
		startText.setValue("");
		startSelected=false;
	
		if (startSelectedUnit !=null)
		{
			startText.setValue(startSelectedUnit.getUnitName());
			startSelected=true;
			startUnitDataSource=UnitContainer.getSimilarUnits(startSelectedUnit.getUnitName());
			displayedUnits.clear();
			displayedUnits.add(startUnitDataSource.getIdByIndex(0));
			mapView.setDisplayedUnits(displayedUnits);
			setMainComponent(mapView);
		}

		endText.setValue("");
		endSelected=false;
		endSelectedUnit= null;
		
		directionLocationList.addListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
			  if (!startSelected && directionLocationList.size()!=0)
			  {
				  startSelectedUnit= (Unit) directionLocationList.getValue();
				  startText.setValue(startSelectedUnit.getUnitName());
				  startSelected = true;
				  locationLabel.setVisible(false);
				  directionLocationList.setVisible(false);
				  directionLocationList.removeAllItems();
				  getDirection();
			  }
			  else
			  if (startSelected && !endSelected && directionLocationList.size()!=0)
			  {
				  endSelectedUnit= (Unit) directionLocationList.getValue();
				  endText.setValue(endSelectedUnit.getUnitName());
				  endSelected = true;
				  locationLabel.setVisible(false);
				  directionLocationList.setVisible(false);
				  directionLocationList.removeAllItems();
				  getDirection();
			  }
		  }
		});
		
		resultList.addListener(new Property.ValueChangeListener() {
			  public void valueChange(ValueChangeEvent event) {
				  Unit selectedUnit= (Unit) resultList.getValue();
				  if (selectedUnit != null)
				  {
					  mapView.setDisplayedUnit(selectedUnit);
					  setMainComponent(mapView);
				  }
			  }
		});
		
		HorizontalLayout startLocationLayout= new HorizontalLayout();
		HorizontalLayout endLocationLayout= new HorizontalLayout();
		
		startLocationLayout.setSpacing(true);
		startLocationLayout.addComponent(startLocationIcon);
		startLocationLayout.setComponentAlignment(startLocationIcon, Alignment.BOTTOM_LEFT);
		startLocationLayout.addComponent(startText);
		startLocationLayout.setComponentAlignment(startText, Alignment.BOTTOM_LEFT);

		endLocationLayout.setSpacing(true);
		endLocationLayout.addComponent(endLocationIcon);
		endLocationLayout.setComponentAlignment(endLocationIcon, Alignment.BOTTOM_LEFT);
		endLocationLayout.addComponent(endText);
		endLocationLayout.setComponentAlignment(endText, Alignment.BOTTOM_LEFT);
		
		verticalViewLayout.setSpacing(true);
		verticalViewLayout.addComponent(startLocationLayout);
		verticalViewLayout.addComponent(endLocationLayout);
		verticalViewLayout.addComponent(directionSearchButton);
		verticalViewLayout.addComponent(locationLabel);
		verticalViewLayout.addComponent(directionLocationList);
		verticalViewLayout.addComponent(resultList);
		verticalViewLayout.addComponent(showStepsButton);
		verticalViewLayout.addComponent(hideStepsButton);
		verticalViewLayout.addComponent(walkingTime);
		verticalViewLayout.addComponent(walkingDistance);
    	verticalViewLayout.addComponent(stepList);
		verticalViewLayout.addComponent(resetButton);
		
		directionSearchButton.setClickShortcut(KeyCode.ENTER);
	
		return verticalViewLayout;
	}
	
/*****************************************************************************/
	
	private VerticalLayout buildFindLocation()
	{
		locationLabel.setVisible(false);
		optionList.setVisible(false);
		resultList.setVisible(false);
		locationSearchText.setValue("");
		
		optionList.addListener(new Property.ValueChangeListener() 
		{
		  public void valueChange(ValueChangeEvent event) {
			  
			  Unit selectedUnit = (Unit) optionList.getValue();
			  if (selectedUnit != null)
			  {
			  	locationSearchText.setValue(selectedUnit.getUnitName());
			  	System.out.println(selectedUnit.getUnitName());
			  	System.out.println(selectedUnit.getmapImageUrl());
			  	displayedUnits.clear();
			  	displayedUnits.add(selectedUnit);
				// Show Results
				locationLabel.setValue("<h3>One Unit was found that matches your chosen location.</h3>");
				locationLabel.setVisible(true); 
				mapView.setDisplayedUnits(displayedUnits);
				setMainComponent(mapView);
				
	        	startSelectedUnit = selectedUnit;
				verticalViewLayout.addComponent(getDirectionFromHereButton);
				
				verticalViewLayout.addComponent(findAnotherLocationButton);
				
				verticalViewLayout.addComponent(addToMyPlacesButton);
			  }
		  }
		});
		
		verticalViewLayout.setSpacing(true);
		verticalViewLayout.addComponent(locationSearchText);
		verticalViewLayout.addComponent(locationSearchButton);
		verticalViewLayout.addComponent(locationLabel);
		verticalViewLayout.addComponent(resultList);
		verticalViewLayout.addComponent(optionList);
//		verticalViewLayout.addComponent(resetButton);
	
		locationSearchButton.setClickShortcut(KeyCode.ENTER);
		
		return verticalViewLayout;
	}
	
	private VerticalLayout buildVerticalView()
	{
		verticalViewLayout.removeAllComponents();
		verticalViewLayout.setSpacing(true);

		tabSheet.addTab(l3,"Home", null);
		tabSheet.addTab(l4,"My Maps",null);
		tabSheet.addTab(l5,"My Places",null);
	    tabSheet.addTab(l1,"Find Location",null);
	    tabSheet.addTab(l2,"Find Direction",null);

		tabSheet.setSelectedTab(null);
	    
	    verticalViewLayout.addComponent(tabSheet);
	    buildHomeTab();

	  	tabSheet.addListener(new SelectedTabChangeListener() 
		{
		    public void selectedTabChange(SelectedTabChangeEvent event) {
		        TabSheet tabSheet = event.getTabSheet();
		        Tab tab = tabSheet.getTab(tabSheet.getSelectedTab());
		        System.out.println(tab.getCaption().toString());
		        
		        if (tab.getCaption().equals("Find Location"))
		        {
		        	verticalViewLayout.removeAllComponents();
		        	verticalViewLayout.addComponent(tabSheet);
		        	buildFindLocation();
		        }
		        else if (tab.getCaption().equals("Find Direction"))
		        {
		        	verticalViewLayout.removeAllComponents();
		        	verticalViewLayout.addComponent(tabSheet);
		        	buildFindDirection();
		        }
		        else if (tab.getCaption().equals("Home"))
		        {
		        	verticalViewLayout.removeAllComponents();
		        	verticalViewLayout.addComponent(tabSheet);
		        	buildHomeTab();
		        }
		        else if (tab.getCaption().equals("My Maps"))
		        {
		        	verticalViewLayout.removeAllComponents();
		        	verticalViewLayout.addComponent(tabSheet);
		        	buildMyMaps();
		        }
		        else if (tab.getCaption().equals("My Places"))
		        {
		        	verticalViewLayout.removeAllComponents();
		        	verticalViewLayout.addComponent(tabSheet);
		        	buildMyPlaces();
		        }
		    }
		});

		return verticalViewLayout;	 
	}
	
	public void buildMyMaps()
	{	
		verticalViewLayout.removeAllComponents();
    	verticalViewLayout.addComponent(tabSheet);
    	tabSheet.setSelectedTab(l4);
    	
		Label label = new Label("<h3>Please select a Map:</h3>",Label.CONTENT_XHTML);
		verticalViewLayout.addComponent(label);
		label.setVisible(true);
		
		placeDataSource = PlaceContainer.getAllPlaces();
		placeList.refreshDataSource(this, placeDataSource);
		verticalViewLayout.addComponent(placeList);
		placeList.setVisible(true);
		
		placeList.addListener(new Property.ValueChangeListener() 
		{
		  public void valueChange(ValueChangeEvent event) {
			  Place selectedPlace= (Place) placeList.getValue();
			  if(selectedPlace != null)
			  {
				  placeList.setVisible(false);
				  buildMyMaps(selectedPlace);
			  }
		  }
		});
	}
	
	public void buildMyMaps(Place selectedPlace)
	{
		verticalViewLayout.removeAllComponents();
    	verticalViewLayout.addComponent(tabSheet);
    	tabSheet.setSelectedTab(l4);
    	
		mapDataSource = MapContainer.getMyMaps(selectedPlace.getPlaceName());
		mapList.refreshDataSource(mapDataSource);
		
		if (mapList.size()==0)
		{
			Label label = new Label("<h3>There are no maps found.</h3>",Label.CONTENT_XHTML);
			label.setVisible(true);
			verticalViewLayout.addComponent(label);
		}
		else
		{
			Label label = new Label("<h3>Please select a map:</h3>",Label.CONTENT_XHTML);
			label.setVisible(true);
			verticalViewLayout.addComponent(label);
			verticalViewLayout.addComponent(mapList);
			mapList.setVisible(true);
		}
		
		mapList.addListener(new Property.ValueChangeListener() 
		{
			public void valueChange(ValueChangeEvent event) 
			  {
				  Map selectedMap = (Map) mapList.getValue();
				  if (selectedMap != null)
				  {
					  mapView.setDisplayedUnits(null);
					  mapView.setMap(selectedMap);
					  setMainComponent(mapView);
					  verticalViewLayout.addComponent(findLocationOnMapButton);
				  }
			  }
		});
		
		verticalViewLayout.addComponent(myMapsButton);
	}
	
	public void buildMyPlaces()
	{	
		verticalViewLayout.removeAllComponents();
    	verticalViewLayout.addComponent(tabSheet);
    	tabSheet.setSelectedTab(l5);
    	
    	getDirectionFromHereButton.setVisible(false);
    	
		//Get Cookies
		String myPlaces = getMyPlacesCookie();
		
		if (myPlaces == null)
		{
			Label label = new Label("<h3>There are no saved Locations in My Places</h3>",Label.CONTENT_XHTML);	
			verticalViewLayout.addComponent(label);
			label.setVisible(true);
		}
		else 
		{
			Label label = new Label("<h3>Please select a place:</h3>",Label.CONTENT_XHTML);
			verticalViewLayout.addComponent(label);
			label.setVisible(true);
			
			String [] myPlacesArray = myPlaces.split(",");
			unitDataSource = UnitContainer.getUnits(myPlacesArray);
			unitList.refreshDataSource(this, unitDataSource);
			verticalViewLayout.addComponent(unitList);
			unitList.setVisible(true);
		
		unitList.addListener(new Property.ValueChangeListener() 
		{
		  public void valueChange(ValueChangeEvent event) {
			  Unit selectedUnit= (Unit) unitList.getValue();
			  if(selectedUnit != null)
			  {
				  displayedUnits.clear();
				  displayedUnits.add(selectedUnit); 
				  mapView.setDisplayedUnits(displayedUnits);
				  setMainComponent(mapView);

		          startSelectedUnit = selectedUnit;
		          getDirectionFromHereButton.setVisible(true);
				  			  
			  }
		  }
		});
		
		verticalViewLayout.addComponent(getDirectionFromHereButton);
		verticalViewLayout.addComponent(clearMyPlacesButton);
		}
	}
	
	private String getMyPlacesCookie() {
		
		String myplaces = null;
		
		try 
		{
			Cookie[] cookies = request.getCookies();
			System.out.println("Cookie Length: "+cookies.length);
			System.out.println("Cookie Name: "+cookies[0].getName());
			for (int i=0; i<cookies.length; i++) {
				if (cookies[i].getName().equals("myplaces"))
                myplaces = cookies[i].getValue();
			}
			System.out.println("Cookie Found: "+ myplaces);
		}
		catch (Exception e)
		{
			//Unable to get Cookie
			return null;
		}
        return myplaces;
        
	}

	public void buildHomeTab()
	{
		verticalViewLayout.addComponent(homeLabel);
	}
	
	public void showDirectionResults()
	{
    	System.out.println("Inside Show Direction Results");
    	System.out.println(mapView.getWalkingDistance());
    	System.out.println(mapView.getWalkingTime());
    	
    	walkingDistance.setValue("Total Distance: "+ mapView.getWalkingDistance()+" meters");
  	  	walkingTime.setValue("Total Walking Time: "+mapView.getWalkingTime()+" minutes");
    	stepList.refreshDataSource(this, StepContainer.getStepContainer(mapView.getStepsCounter(), mapView.getStepsArray()));
	}

	public void getDirection()
	{
		String startTextValue=startText.getValue().toString().trim();
        String endTextValue=endText.getValue().toString().trim();
		System.out.println(startTextValue);
		System.out.println(endTextValue);

		// Source Location Check
		if (!startTextValue.isEmpty() && !startSelected) 
		{
			System.out.println("In Start Selection");
			startUnitDataSource=UnitContainer.getSimilarUnits(startTextValue);
    	
			if (startUnitDataSource.size() == 0) { // Search returns no items
				locationLabel.setVisible(true); 
				locationLabel.setValue("<h3>No items match your chosen starting location.</h3>");
				startSelected = false;
			} 
/*		else if (startUnitDataSource.size()== 1 && 
	    		startUnitDataSource.getIdByIndex(0).getUnitName().toLowerCase().trim().equals(startText.getValue().toString().toLowerCase().trim())) 
	    { // Only one result returned
	        Unit foundUnit = startUnitDataSource.getIdByIndex(0);
	        startSelectedUnits.add(foundUnit);
	    	startText.setValue(foundUnit.getUnitName()); //Set the value 
	    	startSelected = true;
	      }
*/		    
	    else { // More than one item match search 
	    	  for (int i=0;i<startUnitDataSource.size();i++)
	    	    displayedUnits.add(startUnitDataSource.getIdByIndex(i));
	    	    
	          locationLabel.setValue("<h3> Start Location could be:</h3>");
	          locationLabel.setVisible(true);
	          directionLocationList.refreshOptionsDataSource(this,startUnitDataSource);
	          directionLocationList.setVisible(true);
	        }	
        }
		else if (startTextValue.isEmpty())
		{
				startText.focus();
		    	locationLabel.setValue("<h3>Please enter a Start Point.</h3>");
		    	locationLabel.setVisible(true);
		}
      
      // Destination Check
		if (!endTextValue.isEmpty() && !endSelected && startSelected)
		{
    	System.out.println("In End Selection");
		    endUnitDataSource=UnitContainer.getSimilarUnits(endText.getValue().toString());
		    
		    if (endUnitDataSource.size() == 0) { // Search returns no items
		    	locationLabel.setVisible(true); 
		    	locationLabel.setValue("<h3>No items match your chosen Ending Point</h3>");
		    	endSelected = false;
		    }
		    else { // More than one item match search 
	    	  for (int i=0;i<endUnitDataSource.size();i++) {
		    	    displayedUnits.add(endUnitDataSource.getIdByIndex(i));
		    	  }
	          locationLabel.setValue("<h3> End Location could be:</h3>");
		          locationLabel.setVisible(true);
		          directionLocationList.refreshDataSource(this, endUnitDataSource);
		          directionLocationList.refreshOptionsDataSource(this, endUnitDataSource);
		          directionLocationList.setVisible(true);
		          //endPresented = true;
		        }
        }
		else if (endTextValue.isEmpty() && startSelected)
		{
				endText.focus();
		    	locationLabel.setValue("<h3>Please enter an End Point.</h3>");
		    	locationLabel.setVisible(true);
		}
      
	  // Calculating Path
	  if (endSelected == true && startSelected == true ) {
		 
		  if ( startSelectedUnit.getUnitName().equals(endSelectedUnit.getUnitName()))
		  {
			  locationLabel.setValue("<h3>The Start and the End Locations are the same !</h3>");
			  locationLabel.setVisible(true);
		  }
		  else // Calculate Path
		  {
	    	System.out.println("Calculating path between: " + startSelectedUnit.getUnitName() +
	    			       " and " + endSelectedUnit.getUnitName());
	    	//mapView.calculatePath(startSelectedUnit, endSelectedUnit);
	    	//mapView.calculateSteps();

			ArrayList<Unit> unitsArrayList = new ArrayList<Unit>();
			unitsArrayList.add(startSelectedUnit);
			unitsArrayList.add(endSelectedUnit);
			
			unitDataSource = null;
			unitDataSource= UnitContainer.addUnits(startSelectedUnit, endSelectedUnit);
			resultList.refreshDataSource(this,unitDataSource);
			resultList.setVisible(true);
			resultList.setSelectable(false);
	    	
	    	mapView.setDisplayedUnits(unitsArrayList);
			setMainComponent(mapView);
	    	locationLabel.setValue("<h3>A direction between "+startSelectedUnit.getUnitName()+" and "+endSelectedUnit.getUnitName()+" was found on the map.</h3>");
	    	locationLabel.setVisible(true);
			//Show Steps Button
			showStepsButton.setVisible(true);
		  }
	  }
	}
	
	
	public void showNotification(String caption, String description, int type)
	{
		Notification n = new Notification(caption,description, type);
        n.setDelayMsec(1000); // sec->msec
        verticalViewLayout.getWindow().showNotification(n);
	}
	
	public void buttonClick(ClickEvent event) {
		
		Button sourceButton= event.getButton();
		
		//Reset Parameters
//		displayedUnits.clear();
//		optionList.setVisible(false);
//		mapView.clearView();
//		mapView.clearPath();
				
		if (sourceButton == signOutButton)
			getMainWindow().getApplication().close();
		else if (sourceButton == resetButton)
		{
			Tab tab = tabSheet.getTab(tabSheet.getSelectedTab());
	        if (tab.getCaption().equals("Find Location"))
	        {
	        	verticalViewLayout.removeAllComponents();
	        	mapView.setDisplayedUnits(null);
	        	verticalViewLayout.addComponent(tabSheet);
	        	buildFindLocation();
	        }
	        else if (tab.getCaption().equals("Find Direction"))
	        {
	        	verticalViewLayout.removeAllComponents();
	        	mapView.setDisplayedUnits(null);
	        	mapView.clearPath();
	        	verticalViewLayout.addComponent(tabSheet);
	        	startSelectedUnit=null;
	        	buildFindDirection();
	        }
	        else if (tab.getCaption().equals("My Maps"))
	        {
	        	verticalViewLayout.removeAllComponents();
	        	verticalViewLayout.addComponent(tabSheet);
	        	buildMyMaps();
	        }
	        else if (tab.getCaption().equals("My Places"))
	        {
	        	verticalViewLayout.removeAllComponents();
	        	verticalViewLayout.addComponent(tabSheet);
	        	buildMyPlaces();
	        }
		}
/*		else if (sourceButton == hidePanelButton)
		{
			horizontalSplit.setVisible(false);
		    horizontalSplit.removeComponent(hidePanelButton);
		    horizontalSplit.addComponent(showPanelButton);
		}
		else if (sourceButton == showPanelButton)
		{
		    horizontalSplit.removeComponent(showPanelButton);
		    horizontalSplit.addComponent(hidePanelButton);
		    horizontalSplit.setVisible(true);
		}
*/		else if (sourceButton == getDirectionFromHereButton)
		{   	
        	verticalViewLayout.removeAllComponents();
        	verticalViewLayout.addComponent(tabSheet);
        	tabSheet.setSelectedTab(l2);
        	mapView.setDisplayedUnits(null);
        	mapView.clearPath();
        	buildFindDirection();
		}
		else if (sourceButton == addToMyPlacesButton)
		{   
			if (setMyPlacesCookie())			
				showNotification("Location is added successfully to My Places","",Notification.TYPE_HUMANIZED_MESSAGE);
			else showNotification("Unable to add Location to My Places. Please check if you have cookies enabled on your browser.","",Notification.TYPE_ERROR_MESSAGE);
		}
		else if (sourceButton == clearMyPlacesButton)
		{   
			if (removeMyPlacesCookie())		
			{
				verticalViewLayout.removeAllComponents();
		    	verticalViewLayout.addComponent(tabSheet);
		    	tabSheet.setSelectedTab(l5);
				Label label = new Label("<h3>There are no saved Locations in My Places</h3>",Label.CONTENT_XHTML);	
				verticalViewLayout.addComponent(label);
				label.setVisible(true);
	        	showNotification("My Places have been cleared","",Notification.TYPE_HUMANIZED_MESSAGE);
			}
			else showNotification("Unable to clear My Places","",Notification.TYPE_ERROR_MESSAGE);
		}
		else if (sourceButton == findAnotherLocationButton || sourceButton == findLocationOnMapButton)
		{
        	verticalViewLayout.removeAllComponents();
        	verticalViewLayout.addComponent(tabSheet);
        	tabSheet.setSelectedTab(l1);
        	locationSearchText.setValue("");
        	mapView.setDisplayedUnits(null);
        	mapView.clearPath();
        	buildFindLocation();
		}
		else if (sourceButton == myMapsButton)
		{
        	verticalViewLayout.removeAllComponents();
        	verticalViewLayout.addComponent(tabSheet);
        	tabSheet.setSelectedTab(l4);
        	buildMyMaps();
		}
		else if (sourceButton == upperSearchButton)
		{
        	verticalViewLayout.removeAllComponents();
        	verticalViewLayout.addComponent(tabSheet);
        	tabSheet.setSelectedTab(l1);
        	
        	buildFindLocation();
        	
        	verticalViewLayout.removeComponent(locationSearchText);
        	verticalViewLayout.removeComponent(locationSearchButton);
  
        	String locationTextValue = null;
			locationTextValue=upperSearchText.getValue().toString().trim();
			
			if (!locationTextValue.isEmpty()) {
				System.out.println("In Location Selection");
				unitDataSource=UnitContainer.getSimilarUnits(locationTextValue);
        	
				if (unitDataSource.size() == 0) { // Search returns no items
					locationLabel.setValue("<h3>No items match your chosen Location</h3>");
					locationLabel.setVisible(true);
				} 
				else { // More than one item match search 
					System.out.println("More than one item found");
					for (int i=0;i<unitDataSource.size();i++)
						displayedUnits.add(unitDataSource.getIdByIndex(i));

					locationLabel.setValue("<h3>Location could be:</h3>");
					locationLabel.setVisible(true);
					optionList.refreshDataSource(this,unitDataSource);
					optionList.setVisible(true);
		        	}
				}
				else if (locationTextValue.isEmpty())
				{
					locationLabel.setValue("<h3>Please enter a location.</h3>");
					locationLabel.setVisible(true);
				}
			}
		else if (sourceButton == locationSearchButton)
		{
			// Reset all parameters for repeated search
			String locationTextValue = null;
			locationTextValue=locationSearchText.getValue().toString().trim();
			
			if (!locationTextValue.isEmpty()) {
				System.out.println("In Location Selection");
				unitDataSource=UnitContainer.getSimilarUnits(locationTextValue);
        	
				if (unitDataSource.size() == 0) { // Search returns no items
					locationLabel.setValue("<h3>No items match your chosen Location</h3>");
					locationLabel.setVisible(true);
				} 
				else { // More than one item match search 
					System.out.println("More than one item found");
					for (int i=0;i<unitDataSource.size();i++)
						displayedUnits.add(unitDataSource.getIdByIndex(i));
					
					locationLabel.setValue("<h3>Location could be:</h3>");
					locationLabel.setVisible(true);
					optionList.refreshDataSource(this,unitDataSource);
					optionList.setVisible(true);
		        	}
				}
				else if (locationTextValue.isEmpty())
				{
					locationLabel.setValue("<h3>Please enter a location.</h3>");
					locationLabel.setVisible(true);
				}
			}
		
		else if (sourceButton == directionSearchButton)
		{
			getDirection();
		}
		
		else if (sourceButton == showStepsButton)
		{
			System.out.println("Inside showStepsButton");
			showDirectionResults();
			stepList.setVisible(true);
			walkingTime.setVisible(true);
			walkingDistance.setVisible(true);
	    	showStepsButton.setVisible(false);
	    	hideStepsButton.setVisible(true);
		}
		else if (sourceButton == hideStepsButton)
		{
			System.out.println("Inside hideStepsButton");
			stepList.setVisible(false);
			walkingTime.setVisible(false);
			walkingDistance.setVisible(false);
	    	hideStepsButton.setVisible(false);
	    	showStepsButton.setVisible(true);
		}
	}
	
	private boolean removeMyPlacesCookie() {
	
		try
		{
			Cookie cookie = new Cookie("myplaces","");
			// Use a fixed path
			//cookie.setPath("/My-Places");
			cookie.setMaxAge(0); // Unlimited Time
			response.addCookie(cookie);
			System.out.println("Removed cookie.");
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	private boolean setMyPlacesCookie() {
		try
		{
			String myPlaces = getMyPlacesCookie();
			
			if (myPlaces == null)
			{
				//There is no previous cookie
				// Set Cookie
				myPlaces = displayedUnits.get(0).getUnitName();
			}
			else
			if (!myPlaces.contains(displayedUnits.get(0).getUnitName()))
			{
				// Update Cookie
				myPlaces = myPlaces + "," + displayedUnits.get(0).getUnitName();
			}
			
			Cookie cookie = new Cookie("myplaces",myPlaces);
			// Use a fixed path
			//cookie.setPath("/My-Places");
			cookie.setMaxAge(1000000000); // Unlimited Time
			response.addCookie(cookie);
			System.out.println("Set cookie.");
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
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

	public UnitContainer getUnitDataSource() {
      return unitDataSource;
	}
}