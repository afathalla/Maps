package com.boslla.maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;	

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.event.ShortcutAction.*;
import com.boslla.maps.containers.*;
import com.boslla.maps.util.UnitSuggester;
import com.google.gwt.core.client.GWT;

import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;

import org.vaadin.hezamu.googlemapwidget.GoogleMap;
import org.vaadin.hezamu.googlemapwidget.overlay.BasicMarker;
import org.vaadin.hezamu.imagemapwidget.ImageMap;
//import org.vaadin.sami.autocomplete.AutoCompleteTextField;



import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Window.Notification;

public class MapsApplication extends Application implements Button.ClickListener,HttpServletRequestListener{
	  private SplitPanel horizontalSplit = new SplitPanel(
	            SplitPanel.ORIENTATION_HORIZONTAL);
	 
	  private SplitPanel verticalSplit = new SplitPanel(SplitPanel.ORIENTATION_VERTICAL);
	  private SplitPanel verticalSplit2 = new SplitPanel(SplitPanel.ORIENTATION_VERTICAL);
	  private VerticalLayout BottomBar = new VerticalLayout();
	  private VerticalLayout upperBar = new VerticalLayout();
	  private VerticalLayout googleMapLayout= new VerticalLayout();
	  private Button directionSearchButton = new Button("Get Direction");
	  private Button locationSearchButton = new Button("Spot Location");
	  private Button signOutButton = new Button("Sign Out");
	  private Button resetButton = new Button("Clear Search");
	  private Button clearMyPlacesButton = new Button("Clear My Places");
	  private Button myMapsButton = new Button("Back to My Maps");
	  private Button getDirectionFromHereButton = new Button("Get Direction From here");
	  private Button findAnotherLocationButton = new Button("Spot Another Location");
	  private Button addToMyPlacesButton = new Button("Add Location to My Places");
	  private Button showStepsButton = new Button("Show Steps");
	  private Button hideStepsButton = new Button("Hide Steps");
	  private Button setDefaultLocationButton = new Button("Set Default Location");
	  private Button saveDefaultLocationButton = new Button("Save");
	  private Button removeDefaultLocationButton = new Button("Remove");
	  private Button cancelDefaultLocationButton = new Button("Cancel");
	  private Button hidePanelButton = new Button("<<");
	  private Button showPanelButton = new Button(">>");
      private ComboBox setDefaultLocationComboBox = new ComboBox();
	  private TextField startText= new TextField();
	  private TextField locationSearchText= new TextField();

	  private TextField endText= new TextField();
	  private Label locationLabel= new Label("<h3></h3>",Label.CONTENT_XHTML);
	  private Label stepsTitleLabel = new Label ("<h2>Steps to Destination:</h2>",Label.CONTENT_XHTML);
	  private Label walkingDistance = new Label ("Total Distance:",Label.CONTENT_XHTML);
	  private Label walkingTime = new Label ("Total Walking Time:",Label.CONTENT_XHTML);
	  private Label setDefaultLocationLabel = new Label("<b>Set default location</b>",Label.CONTENT_XHTML);
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
	  private boolean defaultLocationFound = false;
	  private String defaultLocationValue = null;
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
      private HorizontalLayout defaultLocationHorizontalLayout = new HorizontalLayout();
      private Embedded startLocationIcon = new Embedded(null, new ThemeResource("numbers/location_1.png"));
      private Embedded endLocationIcon = new Embedded(null, new ThemeResource("numbers/location_2.png"));
      private int horizontalSplitPosition = 25;
      private HttpServletResponse response;
      private HttpServletRequest request;
      private GoogleMap googleMap;


	@Override
	public void init() {
		//setTheme("runo");
		setTheme("chameleon");
		//setTheme("reindeermods-2");
		//setTheme("default");
		
		buildLayout("mainView");
	}
	
	public void onRequestStart(HttpServletRequest request,
            HttpServletResponse response) {
		
		// Get Default Location Cookie
		getDefaultLocationCookie();
		
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
		startText.setWidth(175, TextField.UNITS_PIXELS);
		endText.setInputPrompt("Enter Destination");
		endText.setWidth(175, TextField.UNITS_PIXELS);
		locationSearchText.setInputPrompt("Location Search");
		locationSearchText.setWidth(175, TextField.UNITS_PIXELS);
		
		//Initialize Buttons
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
		signOutButton.setStyleName(Button.STYLE_LINK);
		signOutButton.addListener((ClickListener)this);
		directionSearchButton.addListener((ClickListener)this);
//		directionSearchButton.setIcon(new ThemeResource("icons/search.gif"));
//		locationSearchButton.setIcon(new ThemeResource("icons/search.gif"));
		locationSearchButton.addListener((ClickListener)this);
		findAnotherLocationButton.addListener((ClickListener)this);

		addToMyPlacesButton.setStyleName(Button.STYLE_LINK);
		addToMyPlacesButton.addListener((ClickListener)this);
		clearMyPlacesButton.setStyleName(Button.STYLE_LINK);
		clearMyPlacesButton.addListener((ClickListener)this);
		saveDefaultLocationButton.addListener((ClickListener)this);
		removeDefaultLocationButton.addListener((ClickListener)this);
		cancelDefaultLocationButton.addListener((ClickListener)this);
		
		//Show & Hide Panel Buttons
	    hidePanelButton.setDescription("Hide Panel");
	    showPanelButton.setDescription("Show Panel");
	    hidePanelButton.addListener((ClickListener)this);
	    showPanelButton.addListener((ClickListener)this);
	    hidePanelButton.setStyleName(Button.STYLE_LINK);
	    showPanelButton.setStyleName(Button.STYLE_LINK);
	    
		//Initialize tabSheet
		l1.setMargin(true);	
		l2.setMargin(true);
		l3.setMargin(true);
		l4.setMargin(true);
		l5.setMargin(true);
		
	    //l1.setWidth(20, TextField.UNITS_PERCENTAGE);
	    //l2.setWidth(20, TextField.UNITS_PERCENTAGE);
	    //l3.setWidth(20, TextField.UNITS_PERCENTAGE);
	    //l4.setWidth(20, TextField.UNITS_PERCENTAGE);
	    //l5.setWidth(20, TextField.UNITS_PERCENTAGE);
	    l1.setSizeFull();
	    l2.setSizeFull();
	    l3.setSizeFull();
	    l4.setSizeFull();
	    l5.setSizeFull();
	    
	    tabSheet.setHeight(4, TextField.UNITS_PERCENTAGE);
	    tabSheet.setWidth("100%");
	    tabSheet.setImmediate(true);
	    
        // First, a vertical SplitPanel
		VerticalLayout layout= new VerticalLayout();
		layout.setSizeFull();
		
		// Second, aHorizontal Split
		horizontalSplit.setSplitPosition(horizontalSplitPosition, SplitPanel.UNITS_PERCENTAGE);
		horizontalSplit.setLocked(true);
		
		//Another horizontal split
				
		verticalSplit.addComponent(buildButtonBar());
		verticalSplit.setSplitPosition(100, SplitPanel.UNITS_PERCENTAGE);
		verticalSplit.setLocked(true);
		
		BottomBar.setWidth(100, TextField.UNITS_PERCENTAGE);
		BottomBar.setHeight(6, TextField.UNITS_PERCENTAGE);
		
		horizontalSplit.setFirstComponent(buildVerticalView());
		horizontalSplit.setSecondComponent(buildMainView());

		layout.addComponent(verticalSplit);
		layout.setExpandRatio(verticalSplit, 3);
		layout.addComponent(horizontalSplit);
		layout.setExpandRatio(horizontalSplit,20);
		layout.addComponent(BottomBar);
		layout.setExpandRatio(BottomBar, 1);
		
		mainWindow.setContent(layout);
		setMainWindow(mainWindow);
	}
	
	private VerticalLayout buildMainView() {
		
		VerticalLayout mainViewViewLayout= new VerticalLayout();
		mainViewViewLayout.setSizeFull();
		
		upperBar.setWidth(100, TextField.UNITS_PERCENTAGE);
		upperBar.setHeight(100,TextField.UNITS_PERCENTAGE);;
		upperBar.addComponent(hidePanelButton);
		upperBar.setComponentAlignment(hidePanelButton, Alignment.MIDDLE_LEFT);

//		googleMap = new GoogleMap(this, new Point2D.Double(31.22889,30.05806), 11);
//		googleMap.setSizeFull();
//	    googleMap.setWidth("640px");
//		googleMap.setHeight("480px");
		
		googleMap = new GoogleMap(this);
		googleMap.setSizeFull();
		PlaceContainer placeContainer = PlaceContainer.getPlace(defaultLocationValue);
		setGoogleMap(placeContainer);
		
		googleMapLayout.addComponent(googleMap);
		googleMapLayout.setVisible(true);
		googleMapLayout.setSizeFull();
		
		verticalSplit2.setSplitPosition(4, SplitPanel.UNITS_PERCENTAGE);
		verticalSplit2.setLocked(true);
		verticalSplit2.setFirstComponent(upperBar);
		verticalSplit2.setSecondComponent(googleMapLayout);
		
		mainViewViewLayout.addComponent(verticalSplit2);
		
		return mainViewViewLayout;
	}

	private VerticalLayout buildButtonBar()
	{
		VerticalLayout upperViewLayout= new VerticalLayout();
		
		upperViewLayout.setHeight(100, SplitPanel.UNITS_PERCENTAGE);
		upperViewLayout.setWidth(100, SplitPanel.UNITS_PERCENTAGE);
		
		HorizontalLayout upperButtonBar = new HorizontalLayout();
//		upperButtonBar.addComponent(signOutButton, "right: 1%; top: 1%;");
		upperButtonBar.setWidth(100, SplitPanel.UNITS_PERCENTAGE);
		upperButtonBar.setHeight(100, SplitPanel.UNITS_PERCENTAGE);
		upperButtonBar.addComponent(signOutButton);
		upperButtonBar.setComponentAlignment(signOutButton, Alignment.TOP_RIGHT);
		
		SplitPanel upperVerticalSplit = new SplitPanel(SplitPanel.ORIENTATION_VERTICAL);
		upperVerticalSplit.setSplitPosition(25, SplitPanel.UNITS_PERCENTAGE);
		upperVerticalSplit.setLocked(true);

		HorizontalLayout searchBarLayout= new HorizontalLayout();

		searchBarLayout.setWidth(100, TextField.UNITS_PERCENTAGE);
		searchBarLayout.setHeight(100, SplitPanel.UNITS_PERCENTAGE);
		searchBarLayout.setMargin(false, true, false, false);
		
		Embedded brandIcon = new Embedded(null, new ThemeResource("images/boslla.png"));
		brandIcon.setWidth(100, TextField.UNITS_PERCENTAGE);
		brandIcon.setHeight(90, TextField.UNITS_PERCENTAGE);
		
		final TextField upperSearchText= new TextField();		
		upperSearchText.setWidth(90, TextField.UNITS_PERCENTAGE);
		upperSearchText.setHeight(40, TextField.UNITS_PERCENTAGE);
		
		final Button upperSearchButton = new Button("Spot Location");
		upperSearchButton.setClickShortcut(KeyCode.ENTER);
		upperSearchButton.addListener((ClickListener)this);
		
		final VerticalLayout viewLocationsLayout = new VerticalLayout();
		viewLocationsLayout.setSizeFull();
		viewLocationsLayout.setVisible(true);
		
		upperSearchButton.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	
            	displayedUnits.clear();
        		if (upperSearchText.getValue() != null)
        		{
			  			final UnitContainer unitContainer = UnitContainer.getSimilarUnits(upperSearchText.getValue().toString());
			  			viewLocationsLayout.removeAllComponents();
			  			viewLocationsLayout.addComponent(getUnitsLayout(unitContainer));
			  			
        		}	
        		tabSheet.setSelectedTab(l1);
            	verticalViewLayout.removeAllComponents();
        		verticalViewLayout.addComponent(viewLocationsLayout);
            }
		});
		
		searchBarLayout.addComponent(brandIcon);
		searchBarLayout.setComponentAlignment(brandIcon, Alignment.MIDDLE_LEFT);
		
		searchBarLayout.addComponent(upperSearchText);
		searchBarLayout.setComponentAlignment(upperSearchText, Alignment.MIDDLE_RIGHT);
		
		searchBarLayout.addComponent(upperSearchButton);
		searchBarLayout.setComponentAlignment(upperSearchButton, Alignment.MIDDLE_LEFT);
				
		searchBarLayout.setExpandRatio(brandIcon,1);
		searchBarLayout.setExpandRatio(upperSearchText,2);
		searchBarLayout.setExpandRatio(upperSearchButton,1);
		
		upperVerticalSplit.addComponent(upperButtonBar);
		upperVerticalSplit.addComponent(searchBarLayout);
		
		upperViewLayout.addComponent(upperVerticalSplit);

		return upperViewLayout;
	}
	/**************************************************************************/
	private VerticalLayout buildVerticalView()
		{
			verticalViewLayout.removeAllComponents();
			verticalViewLayout.setSpacing(true);

			tabSheet.addTab(l3,"Home", null);
			tabSheet.addTab(l4,"My Places",null);
//			tabSheet.addTab(l5,"My Places",null);
		    tabSheet.addTab(l1,"Spot Location",null);
		    tabSheet.addTab(l2,"Get Direction",null);

		    tabSheet.setSelectedTab(l3);
		    tabSheet.setSizeFull();

		    VerticalLayout tabViewLayout =  new VerticalLayout();
		    tabViewLayout.setSizeFull();
			
		   // tabSheet.addComponent(verticalViewLayout);
		    tabViewLayout.addComponent(tabSheet);
		     
		    buildHomeTab();

		  	tabSheet.addListener(new SelectedTabChangeListener() 
			{
			    public void selectedTabChange(SelectedTabChangeEvent event) {
			        TabSheet tabSheet = event.getTabSheet();
			        Tab tab = tabSheet.getTab(tabSheet.getSelectedTab());
			        System.out.println(tab.getCaption().toString());
			        
			        if (tab.getCaption().equals("Spot Location"))
			        {
			        	verticalViewLayout.removeAllComponents();
			        	buildFindLocation(null);
			        }
			        else if (tab.getCaption().equals("Get Direction"))
			        {
			        	verticalViewLayout.removeAllComponents();
			        	buildFindDirection(null,null);
			        }
			        else if (tab.getCaption().equals("Home"))
			        {
			        	verticalViewLayout.removeAllComponents();
			        	buildHomeTab();
			        }
			        else if (tab.getCaption().equals("My Places"))
			        {
			        	verticalViewLayout.removeAllComponents();;
			        	buildMyMaps();
			        }
			        else if (tab.getCaption().equals("My Places"))
			        {
			        	verticalViewLayout.removeAllComponents();
//			        	buildMyPlaces();
			        }
			    }
			});

			return tabViewLayout;	 
		}
/****************************************************************************
	This method builds the Get Direction tab
*****************************************************************************/
	
	private void buildFindDirection(String startingUnitName, String locationName)
	{
		l2.addComponent(verticalViewLayout);
		verticalViewLayout.removeAllComponents();
				
		Label label = new Label("<h3>Please select starting point and destination point:</h3>",Label.CONTENT_XHTML);
		Label locationLabel =  new Label("<h3>Selected Location: </h3>"+ locationName,Label.CONTENT_XHTML);
		
		final UnitContainer unitContainer = UnitContainer.getAllUnits(); 
		
		final ComboBox startingLocationComboBox = new ComboBox();
		startingLocationComboBox.setWidth(80, TextField.UNITS_PERCENTAGE);
		startingLocationComboBox.setNewItemsAllowed(false);      
		startingLocationComboBox.setImmediate(true);
		startingLocationComboBox.setNullSelectionAllowed(false);
		startingLocationComboBox.setInputPrompt("Select Starting Point");
        String item;
        
        for (int i = 0; i < unitContainer.size(); i++) {
        	item =  unitContainer.getIdByIndex(i).getUnitName();
        	startingLocationComboBox.addItem(item);
        }
        
        // Set Starting Location Name if received
        if (startingUnitName != null)
        {
        	startingLocationComboBox.setValue(startingUnitName);
        	// show first Location on map
        	UnitContainer startingUnitContainer = UnitContainer.getSimilarUnits(startingUnitName);
        	displayUnitOnMap(startingUnitContainer);
        }
        
		final ComboBox endingLocationComboBox = new ComboBox();
		endingLocationComboBox.setWidth(80, TextField.UNITS_PERCENTAGE);
		endingLocationComboBox.setNewItemsAllowed(false);      
		endingLocationComboBox.setImmediate(true);
		endingLocationComboBox.setNullSelectionAllowed(false);
		endingLocationComboBox.setInputPrompt("Select Destination");
        
        for (int i = 0; i < unitContainer.size(); i++) {
        	item =  unitContainer.getIdByIndex(i).getUnitName();
        	endingLocationComboBox.addItem(item);
        }
        
		HorizontalLayout startLocationLayout= new HorizontalLayout();
		HorizontalLayout endLocationLayout= new HorizontalLayout();
		
	    Embedded startLocationIcon = new Embedded(null, new ThemeResource("numbers/location_1.png"));
	    Embedded endLocationIcon = new Embedded(null, new ThemeResource("numbers/location_2.png"));
		
	    startLocationIcon.setWidth(80, TextField.UNITS_PERCENTAGE);
	    startLocationIcon.setHeight(80, TextField.UNITS_PERCENTAGE);
	    
	    endLocationIcon.setWidth(80, TextField.UNITS_PERCENTAGE);
	    endLocationIcon.setHeight(80, TextField.UNITS_PERCENTAGE);
	    
		startLocationLayout.setSizeFull();
		startLocationLayout.addComponent(startLocationIcon);
		startLocationLayout.setComponentAlignment(startLocationIcon, Alignment.TOP_LEFT);
		startLocationLayout.setExpandRatio(startLocationIcon, 1);
		startLocationLayout.addComponent(startingLocationComboBox);
		startLocationLayout.setComponentAlignment(startingLocationComboBox, Alignment.MIDDLE_LEFT);
		startLocationLayout.setExpandRatio(startingLocationComboBox, 8);

		endLocationLayout.setSizeFull();
		endLocationLayout.addComponent(endLocationIcon);
		endLocationLayout.setComponentAlignment(endLocationIcon, Alignment.TOP_LEFT);
		endLocationLayout.setExpandRatio(endLocationIcon, 1);
		endLocationLayout.addComponent(endingLocationComboBox);
		endLocationLayout.setComponentAlignment(endingLocationComboBox, Alignment.MIDDLE_LEFT);
		endLocationLayout.setExpandRatio(endingLocationComboBox, 8);
		
        final VerticalLayout directionLayout = new VerticalLayout();
        
		Button findDirectionButton = new Button("Get Direction", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	
            		directionLayout.removeAllComponents();
            		
            		mapView.clearDisplayedUnits();
            		mapView.clearPath();
            		
            		if (startingLocationComboBox.getValue() == null || endingLocationComboBox.getValue() == null)
            		{
            			Label label = new Label("<h3>Please select a valid starting point and destination:</h3>",Label.CONTENT_XHTML);
            			directionLayout.addComponent(label);
            		}
            			else if (startingLocationComboBox.getValue() != null && endingLocationComboBox.getValue() != null)
            		{

  			  			final Unit startUnit = UnitContainer.getUnit(startingLocationComboBox.getValue().toString());
  			  			final Unit endUnit = UnitContainer.getUnit(endingLocationComboBox.getValue().toString());
  			  			
  			  			endUnit.setUnitIconUrl("numbers/location_"+ 2 + ".png");
  			  			endUnit.setUnitIcon(new Embedded(null,new ThemeResource("numbers/location_"+1+".png")));
  			  				  			
  			  			if (startUnit.getUnitName().equals(endUnit.getUnitName()))
  			  			{
  	            			Label label = new Label("<h3>Starting and ending Units are the same.</h3>",Label.CONTENT_XHTML);
  			  				directionLayout.addComponent(label);
  			  			}
  			  			else
  			  			{
  			  				directionLayout.addComponent(getDirection(startUnit,endUnit));
  			  			}
  			  		}	

				setMainComponent(mapView);
            }
        });

		findDirectionButton.addListener((ClickListener)this);
		findDirectionButton.setStyleName(Button.STYLE_LINK);
		
		verticalViewLayout.addComponent(locationLabel);
//		verticalViewLayout.addComponent(label);
		verticalViewLayout.addComponent(startLocationLayout);
		verticalViewLayout.addComponent(endLocationLayout);
		verticalViewLayout.addComponent(findDirectionButton);
		verticalViewLayout.addComponent(directionLayout);
		verticalViewLayout.addComponent(resetButton);
	}
	
private void displayUnitOnMap(UnitContainer startingUnitContainer) {
	
	displayedUnits.clear();
	displayedUnits.add(startingUnitContainer.getIdByIndex(0));
	mapView.setDisplayedUnits(displayedUnits);
	setMainComponent(mapView);
}

private VerticalLayout getDirection(Unit startUnit, Unit endUnit)
{
	VerticalLayout directionLayout = new VerticalLayout();
	directionLayout.setSizeFull();
	directionLayout.setMargin(true);
	
	mapView.clearView();
	
	Label directionLabel = new Label("<h3> A direction between "+startUnit.getUnitName()+" and "+endUnit.getUnitName()+" was found on the map.</h3>",Label.CONTENT_XHTML);

	//Show Steps Button
	final Button showDirectionStepsButton = new Button("Show Steps");
	showDirectionStepsButton.addListener((ClickListener)this);
	showDirectionStepsButton.setStyleName(Button.STYLE_LINK);
	
	//Hide Steps Button	
	final Button hideDirectionStepsButton = new Button("Hide Steps");
	hideDirectionStepsButton.addListener((ClickListener)this);
	hideDirectionStepsButton.setStyleName(Button.STYLE_LINK);
	
	System.out.println("Calculating path between: " + startUnit.getUnitName() +
		       " and " + endUnit.getUnitName());

	mapView.drawPathBetweenTwoUnits(startUnit, endUnit);
	
	final Label walkingDistanceLabel = new Label();
	final Label walkingTimeLabel = new Label();
	final Label stepsListLabel = new Label("",Label.CONTENT_XHTML);

	showDirectionStepsButton.addListener(new Button.ClickListener() {
        public void buttonClick(ClickEvent event) {

        	walkingDistanceLabel.setValue("Total Distance: "+ mapView.getWalkingDistance()+" meters");
        	walkingTimeLabel.setValue("Total Walking Time: "+ mapView.getWalkingTime()+" minutes");
        	String stepsList = mapView.getStepsList();
        	stepsListLabel.setValue(stepsList);
        	System.out.println("Inside showStepsButton");
			stepsListLabel.setVisible(true);
			walkingTimeLabel.setVisible(true);
			walkingDistanceLabel.setVisible(true);
			showDirectionStepsButton.setVisible(false);
	    	hideDirectionStepsButton.setVisible(true);
        }
    });

	hideDirectionStepsButton.addListener(new Button.ClickListener() {
        public void buttonClick(ClickEvent event) {

			System.out.println("Inside hideStepsButton");
			stepsListLabel.setVisible(false);
			walkingTimeLabel.setVisible(false);
			walkingDistanceLabel.setVisible(false);
	    	hideDirectionStepsButton.setVisible(false);
	    	showDirectionStepsButton.setVisible(true);
        }
    });

	stepsListLabel.setVisible(false);
	walkingTimeLabel.setVisible(false);
	walkingDistanceLabel.setVisible(false);
	hideDirectionStepsButton.setVisible(false);
	showStepsButton.setVisible(true);
	
	directionLayout.addComponent(directionLabel);
	directionLayout.addComponent(showDirectionStepsButton);
	directionLayout.addComponent(hideDirectionStepsButton);
	directionLayout.addComponent(new Label("<br>",Label.CONTENT_XHTML));
	directionLayout.addComponent(walkingDistanceLabel);	
	directionLayout.addComponent(walkingTimeLabel);
	directionLayout.addComponent(new Label("<br>",Label.CONTENT_XHTML));
	directionLayout.addComponent(stepsListLabel);
	
	return directionLayout;
}

/*	private VerticalLayout buildFindDirection()
	{
		l2.addComponent(verticalViewLayout);
		
		// Hide Labels and Lists
		locationLabel.setVisible(false);
		directionLocationList.setVisible(false);
		resultList.setVisible(false);
		showStepsButton.setVisible(false);
		hideStepsButton.setVisible(false);
		resetButton.setVisible(false);
		stepList.setVisible(false);
		walkingDistance.setVisible(false);
		walkingTime.setVisible(false);
		optionList.setVisible(false);
		
		//Reset Parameters
		directionLocationList.removeAllItems();
		resultList.removeAllItems();
		stepList.removeAllItems();
		mapView.clearPath();

		//Initialize start and end Text Boxes
		startText.setValue("");
		startSelected=false;
		
		
		if (startSelectedUnit !=null)
		{
			startText.setValue(startSelectedUnit.getUnitName()+", "+startSelectedUnit.getMapDescription());
			startSelected=true;
			startUnitDataSource=UnitContainer.getSimilarUnits(startSelectedUnit.getUnitName());
			displayedUnits.clear();
			displayedUnits.add(startUnitDataSource.getIdByIndex(0));
			mapView.setDisplayedUnits(displayedUnits);
			setMainComponent(mapView);
		}
		else if (defaultLocationFound)
		{
			startText.setValue(defaultLocationValue);
			startSelected = true;
			
			String[] s = startText.getValue().toString().split(",");
			System.out.println("Get Direction: "+s[0]);
			startUnitDataSource=UnitContainer.getSimilarUnits(s[0]);
			startSelectedUnit = startUnitDataSource.getIdByIndex(0);
			displayedUnits.clear();
			displayedUnits.add(startSelectedUnit);
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
				  startText.setValue(startSelectedUnit.getUnitName()+", "+startSelectedUnit.getMapDescription());
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
				  endText.setValue(endSelectedUnit.getUnitName()+", "+endSelectedUnit.getMapDescription());
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
		
		startLocationLayout.setSizeFull();
		startLocationLayout.addComponent(startLocationIcon);
		startLocationLayout.setComponentAlignment(startLocationIcon, Alignment.TOP_LEFT);
		startLocationLayout.setExpandRatio(startLocationIcon, 1);
		startLocationLayout.addComponent(startText);
		startLocationLayout.setComponentAlignment(startText, Alignment.MIDDLE_LEFT);
		startLocationLayout.setExpandRatio(startText, 8);

		endLocationLayout.setSizeFull();
		endLocationLayout.addComponent(endLocationIcon);
		endLocationLayout.setComponentAlignment(endLocationIcon, Alignment.TOP_LEFT);
		endLocationLayout.setExpandRatio(endLocationIcon, 1);
		endLocationLayout.addComponent(endText);
		endLocationLayout.setComponentAlignment(endText, Alignment.MIDDLE_LEFT);
		endLocationLayout.setExpandRatio(endText, 8);
		
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
*/
/*****************************************************************************/
	
/*	private void buildFindLocation()
	{
		//verticalViewLayout.removeAllComponents();
		l1.addComponent(verticalViewLayout);
		
		String path = GWT.getModuleBaseURL();
		ImageMap imageMap = new ImageMap(new ExternalResource(path));
		verticalViewLayout.addComponent(imageMap);
		
		googleMapLayout.removeComponent(googleMap);
		googleMapLayout.addComponent(imageMap);
	}
*/	
	private void buildFindLocation(final String locationName)
	{
		tabSheet.setSelectedTab(l1);
		l1.addComponent(verticalViewLayout);
    	googleMapLayout.removeComponent(googleMap);
		verticalViewLayout.removeAllComponents();
		mapView.clearDisplayedUnits();
		mapView.clearPath();

		final UnitContainer unitContainer = UnitContainer.getAllUnits(); 
		
		final ComboBox locationComboBox = new ComboBox();
		locationComboBox.setWidth(80, TextField.UNITS_PERCENTAGE);
		locationComboBox.setNewItemsAllowed(true);      
		locationComboBox.setImmediate(true);
		locationComboBox.setNullSelectionAllowed(false);
		locationComboBox.setInputPrompt("Select a Place");
        String item;
        
        for (int i = 0; i < unitContainer.size(); i++) {
        	item =  unitContainer.getIdByIndex(i).getUnitName();
        	locationComboBox.addItem(item);
        }
        
        System.out.println(locationName);
		if (locationName != null)
		{
			final Place place = PlaceContainer.getPlace(locationName).getIdByIndex(0);
		
			MapContainer mapContainer = MapContainer.getMyMaps(locationName);
			Map map = mapContainer.getIdByIndex(0);
			mapView.setMap(map);
			setMainComponent(mapView);		
		}
		
		Label label = new Label("<h3>Please select a Place:</h3>",Label.CONTENT_XHTML);
		Label locationLabel =  new Label("<h3>Selected Location: </h3>"+ locationName,Label.CONTENT_XHTML);
		
/*	
		final AutoCompleteTextField locationAutoCompleteTextField = new AutoCompleteTextField();
        locationAutoCompleteTextField.setRows(10);
        locationAutoCompleteTextField.setColumns(40);
        locationAutoCompleteTextField.setSuggester(new UnitSuggester());
        locationAutoCompleteTextField.focus();
*/
		
		final HorizontalLayout h2 = new HorizontalLayout();
		h2.setSizeFull();
		h2.setVisible(true);
		
		Button findDirectionButton = new Button("Get direction from here", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	verticalViewLayout.removeAllComponents();
            	tabSheet.setSelectedTab(l2);
            	locationSearchText.setValue("");
            	mapView.clearDisplayedUnits();
            	startSelectedUnit=null;
            	mapView.clearPath();
            	buildFindDirection(locationComboBox.getValue().toString(),locationName);
            }
		});
		findDirectionButton.setStyleName(Button.STYLE_LINK);
		findDirectionButton.addListener((ClickListener)this);
		
		
		final VerticalLayout viewLocationsLayout = new VerticalLayout();
		viewLocationsLayout.setSizeFull();
		viewLocationsLayout.setVisible(true);
		
		Button findLocationButton = new Button("Spot Location", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            		displayedUnits.clear();
            		if (locationComboBox.getValue() != null)
            		{
  			  			final UnitContainer unitContainer = UnitContainer.getSimilarUnits(locationComboBox.getValue().toString());
  			  			viewLocationsLayout.removeAllComponents();
  			  			viewLocationsLayout.addComponent(getUnitsLayout(unitContainer));
  			  			viewLocationsLayout.setVisible(true);
            		}	

			  	mapView.setDisplayedUnits(displayedUnits);
				setMainComponent(mapView);
            }
        });

		findLocationButton.addListener((ClickListener)this);
		findLocationButton.setStyleName(Button.STYLE_LINK);
		findLocationButton.setVisible(true);
		
		h2.addComponent(findLocationButton);
		h2.setExpandRatio(findLocationButton, 1);
		h2.addComponent(findDirectionButton);
		h2.setExpandRatio(findDirectionButton, 6);
		
		verticalViewLayout.addComponent(locationLabel);
//		verticalViewLayout.addComponent(label);
		verticalViewLayout.addComponent(locationComboBox);
		verticalViewLayout.addComponent(h2);
		verticalViewLayout.addComponent(viewLocationsLayout);
	}
	
protected VerticalLayout getUnitsLayout(UnitContainer unitContainer) {
	
	final VerticalLayout viewLocationsLayout = new VerticalLayout();
	viewLocationsLayout.setSizeFull();
	viewLocationsLayout.setVisible(true);
	
	for (int i=0; i<unitContainer.size();i++)
		{
		final HorizontalLayout viewLocationLayout = new HorizontalLayout();
		viewLocationLayout.removeAllComponents();
		viewLocationLayout.setSizeFull();
		viewLocationLayout.setMargin(true, true, true, false);
			
		Unit unit = unitContainer.getIdByIndex(i);
		Embedded unitImage = unit.getImageUrl();
		Embedded unitIcon = unit.getUnitIcon();
		String name = unit.getUnitName();
		String category = unit.getUnitType();
		String location = unit.getMapDescription();
		String Description= unit.getDescription();

		Button unitName = new Button(name, new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {}});
		
				unitName.addListener((ClickListener)this);
				unitName.setStyleName(Button.STYLE_LINK);
				unitName.setSizeFull();
		
				Label unitDescription= new Label("<br>"+category+"<br>"+location+"<br>"+Description,Label.CONTENT_XHTML);
			
				VerticalLayout v = new VerticalLayout();
				v.setSizeFull();
				v.setVisible(true);
				
				unitIcon.setWidth(80, TextField.UNITS_PERCENTAGE);
				unitIcon.setHeight(80, TextField.UNITS_PERCENTAGE);
				unitImage.setWidth(95, TextField.UNITS_PERCENTAGE);
				unitImage.setHeight(95, TextField.UNITS_PERCENTAGE);
				unitName.setWidth(95, TextField.UNITS_PERCENTAGE);
				unitName.setHeight(95, TextField.UNITS_PERCENTAGE);
				unitDescription.setWidth(95, TextField.UNITS_PERCENTAGE);
				unitDescription.setHeight(95, TextField.UNITS_PERCENTAGE);
				
				v.addComponent(unitName);
				v.setComponentAlignment(unitName, Alignment.TOP_LEFT);
				v.addComponent(unitImage);		
				v.setComponentAlignment(unitImage, Alignment.MIDDLE_CENTER);
		
				viewLocationLayout.addComponent(unitIcon);
				viewLocationLayout.setComponentAlignment(unitIcon, Alignment.TOP_LEFT);
				viewLocationLayout.setExpandRatio(unitIcon,1);
				viewLocationLayout.addComponent(v);
				viewLocationLayout.setComponentAlignment(v, Alignment.MIDDLE_CENTER);
				viewLocationLayout.setExpandRatio(v,3);
				viewLocationLayout.addComponent(unitDescription);
				viewLocationLayout.setComponentAlignment(unitDescription, Alignment.MIDDLE_CENTER);
				viewLocationLayout.setExpandRatio(unitDescription,6);
		
				viewLocationsLayout.addComponent(viewLocationLayout);
			  	displayedUnits.add(unit);
	}
	
	return viewLocationsLayout;
	
}

/*	private VerticalLayout buildFindLocation()
	{
		l1.addComponent(verticalViewLayout);
		
		locationLabel.setVisible(false);
		optionList.setVisible(false);
//		resetButton.setVisible(false);
    	getDirectionFromHereButton.setVisible(false);
    	findAnotherLocationButton.setVisible(false);
    	addToMyPlacesButton.setVisible(false);
		locationSearchText.setValue("");
		locationSearchText.setInputPrompt("Enter Location");
		
		mapView.clearPath();
		mapView.clearDisplayedUnits();
		optionList.removeAllItems();
		
		optionList.addListener(new Property.ValueChangeListener() 
		{
		  public void valueChange(ValueChangeEvent event) {
			  
			  Unit selectedUnit = (Unit) optionList.getValue();
			  if (selectedUnit != null)
			  {
			  	locationSearchText.setValue(selectedUnit.getUnitName()+", "+selectedUnit.getMapDescription());
			  	System.out.println(selectedUnit.getUnitName());
			  	System.out.println(selectedUnit.getmapImageUrl());
			  	displayedUnits.clear();
			  	displayedUnits.add(selectedUnit);
			  	mapView.setDisplayedUnits(displayedUnits);
				setMainComponent(mapView);
				// Show Results
				locationLabel.setValue("<h3>Matches Found to your chosen location:</h3>");
				locationLabel.setVisible(true); 

				
	        	startSelectedUnit = selectedUnit;
	        	
	        	getDirectionFromHereButton.setVisible(true);
	        	findAnotherLocationButton.setVisible(true);
	        	addToMyPlacesButton.setVisible(true);
//				resetButton.setVisible(true);
			  }
		  }
		});
		
		verticalViewLayout.setSpacing(true);
		verticalViewLayout.addComponent(locationSearchText);
		verticalViewLayout.addComponent(locationSearchButton);
		verticalViewLayout.addComponent(locationLabel);
		verticalViewLayout.addComponent(optionList);
//		verticalViewLayout.addComponent(resetButton);
		verticalViewLayout.addComponent(getDirectionFromHereButton);
		verticalViewLayout.addComponent(findAnotherLocationButton);
		verticalViewLayout.addComponent(addToMyPlacesButton);
	
		locationSearchButton.setClickShortcut(KeyCode.ENTER);
		
		return verticalViewLayout;
	}
	*/
/**********************************************************************************/

	private void buildMyMaps()
	{	
		verticalViewLayout.removeAllComponents();
		l4.addComponent(verticalViewLayout);
		mapView.clearView();
    	
		Label label = new Label("<h3>Please select a Location:</h3>",Label.CONTENT_XHTML);
		label.setVisible(true);

		final ComboBox locationComboBox = new ComboBox();
		locationComboBox.setWidth(80, TextField.UNITS_PERCENTAGE);
		locationComboBox.setNewItemsAllowed(false);      
		locationComboBox.setImmediate(true);
		locationComboBox.setNullSelectionAllowed(false);
		locationComboBox.setInputPrompt("Select a Place");
		
		final HorizontalLayout h2 = new HorizontalLayout();
		h2.setSizeFull();
		
		final PlaceContainer placeContainer = PlaceContainer.getAllPlaces(); 
		
		Button findLocationOnMapButton = new Button("Find a Place inside this Location", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	
            	if (locationComboBox.getValue() != null)
            	{
            	verticalViewLayout.removeAllComponents();
            	tabSheet.setSelectedTab(l1);
            	locationSearchText.setValue("");
            	mapView.clearDisplayedUnits();
            	startSelectedUnit=null;
            	mapView.clearPath();
            	//buildFindLocation(locationComboBox.getValue().toString());
            	buildFindLocation(locationComboBox.getValue().toString());
            	}
            }
		});
		findLocationOnMapButton.setStyleName(Button.STYLE_LINK);
		findLocationOnMapButton.addListener((ClickListener)this);
		
		setGoogleMap(placeContainer);
        
        String item;
        for (int i = 0; i < placeContainer.size(); i++) {
        	item =  placeContainer.getIdByIndex(i).getPlaceName();
        	locationComboBox.addItem(item);
        }

		final HorizontalLayout viewInfoLayout = new HorizontalLayout();
		viewInfoLayout.setSizeFull();
		viewInfoLayout.setVisible(false);
		
		locationComboBox.addListener(new Property.ValueChangeListener()
		{
			  public void valueChange(ValueChangeEvent event) {
				  
				  viewInfoLayout.removeAllComponents();
				  final Place place = PlaceContainer.getPlace(locationComboBox.getValue().toString()).getIdByIndex(0);
				  //Focus on a certain place
				  setGoogleMaps(placeContainer,place);
			  }
		});
		
		Button viewLocationInfoButton = new Button("View Info", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {

            		viewInfoLayout.removeAllComponents();
            		
            		if (locationComboBox.getValue() != null)
            		{
  			  			final Place place = PlaceContainer.getPlace(locationComboBox.getValue().toString()).getIdByIndex(0);
	  			  		Embedded placeImage = place.getPlaceIcon();
	  			  		String name = place.getPlaceName();
	  			  		String category = place.getPlaceType();
	  			  		String address = place.getPlaceLocation();
	  			  		String Description= place.getPlaceDescription();
	            	
	  			  		Label placeDescription= new Label("<b>"+name+"</b>"+"<br>"+category+"<br>"+address+"<br>"+Description,Label.CONTENT_XHTML);
	    			
	  			  		placeImage.setWidth(100, TextField.UNITS_PERCENTAGE);
	  			  		placeImage.setHeight(100, TextField.UNITS_PERCENTAGE);
	    			
	  			  		placeDescription.setWidth(100, TextField.UNITS_PERCENTAGE);
	  			  		placeDescription.setHeight(100, TextField.UNITS_PERCENTAGE);
	            	
	  			  		viewInfoLayout.addComponent(placeImage);
	  			  		viewInfoLayout.setComponentAlignment(placeImage, Alignment.TOP_LEFT);
	  			  		viewInfoLayout.setExpandRatio(placeImage,1);
	  			  		viewInfoLayout.addComponent(placeDescription);
	  			  		viewInfoLayout.setComponentAlignment(placeDescription, Alignment.MIDDLE_LEFT);
	  			  		viewInfoLayout.setExpandRatio(placeDescription,2);
	  			  		
	  			  		viewInfoLayout.setVisible(true);
            		}
            }
        });

		viewLocationInfoButton.addListener((ClickListener)this);
		viewLocationInfoButton.setStyleName(Button.STYLE_LINK);
		
		h2.addComponent(viewLocationInfoButton);
		h2.setExpandRatio(viewLocationInfoButton, 1);
		h2.addComponent(findLocationOnMapButton);
		h2.setExpandRatio(findLocationOnMapButton, 6);
		
//		verticalViewLayout.addComponent(label);
		verticalViewLayout.addComponent(locationComboBox);
		verticalViewLayout.addComponent(h2);
		verticalViewLayout.addComponent(viewInfoLayout);;
	}
	
	private void buildMySelectedMap(Place selectedPlace)
	{
		verticalViewLayout.removeAllComponents();    	
    	l4.addComponent(verticalViewLayout);
    	
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
//					  verticalViewLayout.addComponent(findLocationOnMapButton);
				  }
			  }
		});
		
		verticalViewLayout.addComponent(myMapsButton);
	}
	
/*	private void buildMyPlaces()
	{	
		verticalViewLayout.removeAllComponents();
		mapView.clearDisplayedUnits();
		l5.addComponent(verticalViewLayout);
		
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
*/	
	private void buildHomeTab()
	{			
		l3.addComponent(verticalViewLayout);
		Label homeLabel= new Label ("<h2>Welcome to BOSLLA.COM !!</h2></ br> You can Spot Locations and directions to your favorite indoor places here...",Label.CONTENT_XHTML);
		verticalViewLayout.addComponent(homeLabel);

		if (getDefaultLocationCookie() != null)
		{
			Label defaultLocationLabel = new Label ("Default Location: "+defaultLocationValue);
			verticalViewLayout.addComponent(defaultLocationLabel);
			setDefaultLocationButton.setCaption("Change Default Location");
			PlaceContainer placeContainer = PlaceContainer.getPlace(defaultLocationValue);
			mapView.clearView();
			setGoogleMap(placeContainer);
		}
		else 
		{
			setDefaultLocationButton.setCaption("Set Default Location");
				mapView.setDisplayedUnits(null);
			mapView.clearView();
			setMainComponent(mapView);
		}
		
		setDefaultLocationButton.setClickShortcut(KeyCode.ENTER);
		setDefaultLocationButton.addListener((ClickListener)this);
		setDefaultLocationButton.setStyleName(Button.STYLE_LINK);
		
		setDefaultLocationButton.setVisible(true);
		verticalViewLayout.addComponent(setDefaultLocationButton);
	}
	
/*	private void showDirectionResults()
	{
    	System.out.println("Inside Show Direction Results");
    	System.out.println(mapView.getWalkingDistance());
    	System.out.println(mapView.getWalkingTime());
	}
*/
/*	private void getDirection()
	{
		String [] s = startText.getValue().toString().trim().split(",");
		String startTextValue = s[0];
        String [] e = endText.getValue().toString().trim().split(",");
        String endTextValue = e[0];
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
		else if (startUnitDataSource.size()== 1 && 
	    		startUnitDataSource.getIdByIndex(0).getUnitName().toLowerCase().trim().equals(startText.getValue().toString().toLowerCase().trim())) 
	    { // Only one result returned
	        Unit foundUnit = startUnitDataSource.getIdByIndex(0);
	        startSelectedUnits.add(foundUnit);
	    	startText.setValue(foundUnit.getUnitName()); //Set the value 
	    	startSelected = true;
	      }
		    
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
			  locationLabel.setValue("<h3>The End location is the same as the start location!</h3>");
			  locationLabel.setVisible(true);
			  endSelected = false;
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
	    	locationLabel.setValue("<h3>A direction between "+startSelectedUnit.getUnitName()+" and "+endSelectedUnit.getUnitName()+" was found on the map.</h3>");
	    	locationLabel.setVisible(true);
			resetButton.setVisible(true);
			//Show Steps Button
			showStepsButton.setVisible(true);
		  }
	  }
	}
*/
	private void showNotification(String caption, String description, int type)
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
	        if (tab.getCaption().equals("Spot Location"))
	        {
	        	verticalViewLayout.removeAllComponents();
	        	mapView.clearDisplayedUnits();
	        	startSelectedUnit=null;
	        	buildFindLocation(null);
	        }
	        else if (tab.getCaption().equals("Get Direction"))
	        {
	        	verticalViewLayout.removeAllComponents();
	        	mapView.clearDisplayedUnits();
	        	mapView.clearPath();
	        	startSelectedUnit=null;
	        	buildFindDirection(null,null);
	        }
	        else if (tab.getCaption().equals("My Places"))
	        {
	        	verticalViewLayout.removeAllComponents();
	        	buildMyMaps();
	        }
	        else if (tab.getCaption().equals("My Places"))
	        {
	        	verticalViewLayout.removeAllComponents();
//	        	buildMyPlaces();
	        }
		}
		else if (sourceButton == hidePanelButton)
		{
			horizontalSplit.setSplitPosition(0, SplitPanel.UNITS_PERCENTAGE);
		    upperBar.removeComponent(hidePanelButton);
		    upperBar.addComponent(showPanelButton);
		}
		else if (sourceButton == showPanelButton)
		{
			upperBar.removeComponent(showPanelButton);
			upperBar.addComponent(hidePanelButton);
			horizontalSplit.setSplitPosition(horizontalSplitPosition, SplitPanel.UNITS_PERCENTAGE);
		}
		
		else if (sourceButton == setDefaultLocationButton)
		{   	
			buildSetDefaultLocation();			
		}
		else if (sourceButton == saveDefaultLocationButton)
		{ 
			saveSetDefaultLocation();
		}
		else if (sourceButton == removeDefaultLocationButton)
		{ 
			removeSetDefaultLocation();
		}
		else if (sourceButton == cancelDefaultLocationButton)
		{  
			cancelSetDefaultLocation();
		}
		else if (sourceButton == getDirectionFromHereButton)
		{   	
        	verticalViewLayout.removeAllComponents();
        	tabSheet.setSelectedTab(l2);
        	mapView.setDisplayedUnits(null);
        	mapView.clearPath();
        	buildFindDirection(null,null);
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
		    	tabSheet.setSelectedTab(l5);
				Label label = new Label("<h3>There are no saved Locations in My Places</h3>",Label.CONTENT_XHTML);	
				verticalViewLayout.addComponent(label);
				label.setVisible(true);
	        	showNotification("My Places have been cleared","",Notification.TYPE_HUMANIZED_MESSAGE);
			}
			else showNotification("Unable to clear My Places","",Notification.TYPE_ERROR_MESSAGE);
		}
		else if (sourceButton == findAnotherLocationButton)
		{
        	verticalViewLayout.removeAllComponents();
        	tabSheet.setSelectedTab(l1);
        	locationSearchText.setValue("");
        	mapView.clearDisplayedUnits();
        	startSelectedUnit=null;
        	mapView.clearPath();
        	buildFindLocation(null);
		}
		else if (sourceButton == myMapsButton)
		{
        	verticalViewLayout.removeAllComponents();
        	tabSheet.setSelectedTab(l4);
        	buildMyMaps();
		}
/*		else if (sourceButton == upperSearchButton)
		{
        	verticalViewLayout.removeAllComponents();
        	tabSheet.setSelectedTab(l1);
        	
        	buildFindLocation(null);
        	
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
*/		else if (sourceButton == locationSearchButton)
		{
			// Reset all parameters for repeated search
			String locationTextValue = null;
	        String [] e = locationSearchText.getValue().toString().trim().split(",");
	        locationTextValue = e[0];
			
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
//			getDirection();
		}
		
		else if (sourceButton == showStepsButton)
		{
			System.out.println("Inside showStepsButton");
//			showDirectionResults();
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
	
/*******************************************************************/
	
private void buildSetDefaultLocation() 
{
		System.out.println("Inside Build Default Location");
        setDefaultLocationComboBox.setNewItemsAllowed(false);      
        setDefaultLocationComboBox.setImmediate(true);
        setDefaultLocationComboBox.setNullSelectionAllowed(false);
 
        placeDataSource = PlaceContainer.getAllPlaces();       
        String item;
        for (int i = 0; i < placeDataSource.size(); i++) {
        	item =  placeDataSource.getIdByIndex(i).getPlaceName();
        	setDefaultLocationComboBox.addItem(item);
        }

		System.out.println("Build Default Location:" + defaultLocationValue);
		
		if (defaultLocationFound == false)
		    setDefaultLocationComboBox.setInputPrompt("Enter Default Location");
		else
			setDefaultLocationComboBox.setInputPrompt(defaultLocationValue);
		
		setDefaultLocationButton.setVisible(false);

		defaultLocationHorizontalLayout.setSpacing(true);
		
		verticalViewLayout.addComponent(setDefaultLocationLabel);
		defaultLocationHorizontalLayout.addComponent(setDefaultLocationComboBox);
		defaultLocationHorizontalLayout.setComponentAlignment(setDefaultLocationComboBox, Alignment.BOTTOM_LEFT);
		defaultLocationHorizontalLayout.addComponent(saveDefaultLocationButton);
		defaultLocationHorizontalLayout.setComponentAlignment(saveDefaultLocationButton, Alignment.BOTTOM_LEFT);
		defaultLocationHorizontalLayout.addComponent(removeDefaultLocationButton);
		defaultLocationHorizontalLayout.setComponentAlignment(removeDefaultLocationButton, Alignment.BOTTOM_LEFT);			
		defaultLocationHorizontalLayout.addComponent(cancelDefaultLocationButton);
		defaultLocationHorizontalLayout.setComponentAlignment(cancelDefaultLocationButton, Alignment.BOTTOM_LEFT);
		
		verticalViewLayout.addComponent(defaultLocationHorizontalLayout);
	}
	
	private void cancelSetDefaultLocation() {
	
    	verticalViewLayout.removeAllComponents();
    	buildHomeTab();
	}
	
	private void removeSetDefaultLocation() {
		
		if (removeDefaultLocationCookie())
			{
				showNotification("Default Location is removed successfully","",Notification.TYPE_HUMANIZED_MESSAGE);
				defaultLocationFound = false;
				defaultLocationValue = null;
				cancelSetDefaultLocation();
			}
		else 
			showNotification("Unable to remove default location. Please check if you have cookies enabled on your browser.","",Notification.TYPE_ERROR_MESSAGE);
		
	}
	
	private void saveSetDefaultLocation() {
		
		if (setDefaultLocationComboBox.getValue() != null)
		{
			defaultLocationValue = setDefaultLocationComboBox.getValue().toString();
			
			if (setDefaultLocationCookie(defaultLocationValue))
			{
				setDefaultLocationCookie(defaultLocationValue);
				showNotification("Selected default location: ",defaultLocationValue, Notification.TYPE_HUMANIZED_MESSAGE);
				defaultLocationFound = true;
				cancelSetDefaultLocation();
			}
			else				
				showNotification("Unable to set Default Location. Please make sure that cookies are enabled on your browser.","", Notification.TYPE_ERROR_MESSAGE);
		}
		else
			showNotification("Please select a location","", Notification.TYPE_ERROR_MESSAGE);

	}

/*******************************************************************/
	
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
	
	private String getDefaultLocationCookie() {
		
		try 
		{
			Cookie[] cookies = request.getCookies();
			System.out.println("Cookie Length: "+cookies.length);
			for (int i=0; i<cookies.length; i++) {
				if (cookies[i].getName().equals("defaultlocation"))
				{
					defaultLocationValue = cookies[i].getValue();
					defaultLocationFound = true;
					System.out.println("Cookie Found: "+ defaultLocationValue);
					return defaultLocationValue;
				}
			}
		    return null;
		}
		catch (Exception e)
		{
			//Unable to get Cookie
			return null;
		}   
	}
	
	private boolean setDefaultLocationCookie(String defaultLocation) {
		try
		{			
			Cookie cookie = new Cookie("defaultlocation",defaultLocation);
			// Use a fixed path
			//cookie.setPath("/My-Places");
			cookie.setMaxAge(1000000000); // Unlimited Time
			response.addCookie(cookie);
			System.out.println("Set cookie: Default Location");
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
	
	private boolean removeDefaultLocationCookie() {
		
		try
		{
			Cookie cookie = new Cookie("defaultlocation","");
			// Use a fixed path
			//cookie.setPath("/My-Places");
			cookie.setMaxAge(0); // Unlimited Time
			response.addCookie(cookie);
			System.out.println("Removed cookie: defaultlocation");
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	private void setGoogleMap(PlaceContainer placeContainer)
	{
	    //GoogleMap googleMap = new GoogleMap(this, new Point2D.Double(22.3, 60.4522), 8);
		//googleMap.addMarker(new BasicMarker(1L, new Point2D.Double(31.345657,30.073244), "Test marker"));
		
		googleMap.setCenter(new Point2D.Double(31.22889,30.05806));
		googleMap.setZoom(11);
		googleMap.setScrollWheelZoomEnabled(true);
		googleMap.removeAllMarkers();
		
		for (int i=0;i<placeContainer.size();i++)
		{
			Long l = new Long(i);
			System.out.println( l + placeContainer.getIdByIndex(i).getPlaceLongitude()+ placeContainer.getIdByIndex(i).getPlaceLatitude());
			BasicMarker basicMarker = new BasicMarker( l, new Point2D.Double( placeContainer.getIdByIndex(i).getPlaceLongitude(), placeContainer.getIdByIndex(i).getPlaceLatitude()), placeContainer.getIdByIndex(i).getPlaceName());
			basicMarker.setDraggable(false);		  	
		  	basicMarker.setInfoWindowContent(null, getGoogleMarkersInfo(placeContainer.getIdByIndex(i)));
		  	googleMap.addMarker(basicMarker);	
		}
		
		googleMapLayout.removeAllComponents();
		googleMapLayout.addComponent(googleMap);
		setMainComponent(googleMapLayout);
	}
	
	protected void setGoogleMaps(PlaceContainer placeContainer, Place place) {
		
		googleMap.setCenter(new Point2D.Double(place.getPlaceLongitude(),place.getPlaceLatitude()));
		googleMap.setZoom(15);
		googleMap.removeAllMarkers();
		googleMap.setScrollWheelZoomEnabled(true);
		
		// Create a marker at the IT Mill offices
		//googleMap.addMarker(new BasicMarker(1L, new Point2D.Double(31.345657,30.073244), "Test marker"));
		
		for (int i=0;i<placeContainer.size();i++)
		{
			Long l = new Long(i);
			BasicMarker basicMarker = new BasicMarker( l, new Point2D.Double( placeContainer.getIdByIndex(i).getPlaceLongitude(), placeContainer.getIdByIndex(i).getPlaceLatitude()), placeContainer.getIdByIndex(i).getPlaceName());
			basicMarker.setDraggable(false);
		  	basicMarker.setInfoWindowContent(null, getGoogleMarkersInfo(placeContainer.getIdByIndex(i)));
			googleMap.addMarker(basicMarker);
		}
		
		googleMapLayout.removeAllComponents();
		googleMapLayout.addComponent(googleMap);
		setMainComponent(googleMapLayout);
	}

	public HorizontalLayout getGoogleMarkersInfo(Place place)
	{
		final Embedded placeImage = place.getPlaceIcon();
		final String name = place.getPlaceName();
		final String category = place.getPlaceType();
		final String address = place.getPlaceLocation();
		final String Description= place.getPlaceDescription();
	  	final Label placeDescription= new Label("<b>"+name+"</b>"+"<br>"+category+"<br>"+address+"<br>"+Description+"<br>",Label.CONTENT_XHTML);
	  	
/*	  	final HorizontalLayout viewInfoLayout = new HorizontalLayout();
  		
	  	viewInfoLayout.setMargin(true,true,true,true);
  		viewInfoLayout.addComponent(placeImage);
	  	viewInfoLayout.setComponentAlignment(placeImage, Alignment.TOP_LEFT);
	  	viewInfoLayout.setExpandRatio(placeImage,1);
	  	viewInfoLayout.addComponent(placeDescription);
	  	viewInfoLayout.setComponentAlignment(placeDescription, Alignment.MIDDLE_LEFT);
	  	viewInfoLayout.setExpandRatio(placeDescription,2);
*/	  	
		Button goInsideButton = new Button("Go Inside", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	buildFindLocation(name);
            }
		});
		goInsideButton.setWidth(10, TextField.UNITS_PERCENTAGE);
		goInsideButton.setStyleName(Button.STYLE_LINK);
		goInsideButton.addListener((ClickListener)this);
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.addComponent(placeDescription);
		verticalLayout.addComponent(goInsideButton);

	  	final HorizontalLayout horizontalLayout = new HorizontalLayout();
	  	horizontalLayout.addComponent(placeImage);
	  	horizontalLayout.addComponent(verticalLayout);;

	  	return horizontalLayout;
	}
	
	private void setMainComponent(Component c){
		verticalSplit2.setSecondComponent(c);
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