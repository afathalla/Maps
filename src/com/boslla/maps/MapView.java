package com.boslla.maps;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.boslla.maps.containers.Unit;
import com.boslla.maps.containers.UnitContainer;
import com.boslla.maps.containers.StepContainer;
import com.boslla.maps.navigate.MapGrid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.TextArea;
import com.boslla.path.*;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.ui.*;
import com.boslla.maps.containers.*;
import com.boslla.maps.widgetset.client.ui.VMapView;

/**
 * Server side component for the VMapView widget.
 */
@com.vaadin.ui.ClientWidget(VMapView.class)
public class MapView extends AbstractComponent {

//	private String message = "Click here.";
//	private int clicks = 0;
	
	private int scale = 1; // This is the scale of the map (every pixel matches how many meters).
	private String unit = "meters";
	private MapGrid floorMap;
	private AStarPathFinder pathFinder;
	private Path path= null; //Path from the two user-clicked points
	private ArrayList<Unit> displayedUnits;
	
	// Showing Steps parameters
	private int endX;
	private int endY;
	private int startX;
	private int startY;
	//private Table stepsTable;
	private int stepsCounter;
	private StepContainer newDataSource;
	private String[] stepsArray;
	PaintTarget paintTarget;
//	private ArrayList<Step> stepsArrayList = new ArrayList<Step>();
//	private StepList stepsList = new StepList();
	
	private String[] stepsX;
	private String[] stepsY;
	private String 	mapImageUrl;
	private int		mapWidth;
	private int	 	mapHeight;
	private String	mapDescription;
	private int startXPath;
	private int startYPath;
	private int endXPath;
	private int endYPath;
	private com.boslla.maps.containers.Map map = null;
	
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		this.paintTarget=target;
		super.paintContent(paintTarget);
	
		System.out.println("Inside Paint content method");
		
		// Paint any component specific content by setting attributes
		// These attributes can be read in updateFromUIDL in the widget.
		
		/*		if (displayedUnits != null)
				{
					mapImageUrl = displayedUnits.get(0).getmapImageUrl();
					paintTarget.addAttribute("imageurl", mapImageUrl);
					
					placesX = new String[1];
					placesY = new String[1];
					
					placesX[0]=Integer.toString(displayedUnit.getX()); 
					placesY[0]=Integer.toString(displayedUnit.getY()); 
					 
					target.addAttribute("placesX",placesX);
					target.addAttribute("placesY",placesY);
				}
		*/		

		if (displayedUnits != null)
		{
			String[] placesX = new String[displayedUnits.size()];
			String[] placesY = new String[displayedUnits.size()];
			String[] placesImages = new String[displayedUnits.size()];

			System.out.println(displayedUnits.size());
		
			if (displayedUnits.size() == 1) // one item only
			{
				System.out.println("One Item");
			  	Unit currentUnit = (Unit) displayedUnits.get(0);
			  
			  	mapImageUrl = currentUnit.getmapImageUrl();
			  	map = (com.boslla.maps.containers.Map) MapContainer.getMap(mapImageUrl);
			  	mapWidth= map.getMapWidth();
			  	mapHeight= map.getMapHeight();
			  	mapDescription= map.getMapDescription();
			  
			  	placesX[0]=Integer.toString(currentUnit.getX()); 
			  	placesY[0]=Integer.toString(currentUnit.getY()); 
			  	placesImages[0]= currentUnit.getUnitIconUrl();
			  	System.out.println(currentUnit.getUnitName());
			  	System.out.println(currentUnit.getUnitIconUrl());
			}
			else if (displayedUnits.size() == 2) // two items
			{
				System.out.println("Two Items");
				Unit unit1 = (Unit) displayedUnits.get(0);
				Unit unit2 = (Unit) displayedUnits.get(1);
				
				// If the two units are on the same map
				if (unit1.getmapImageUrl().equals(unit2.getmapImageUrl()))
				{
					
					System.out.println("On the same map");
					mapImageUrl = unit1.getmapImageUrl();
					map = (com.boslla.maps.containers.Map) MapContainer.getMap(mapImageUrl);
					mapWidth= map.getMapWidth();
					mapHeight= map.getMapHeight();
					mapDescription= map.getMapDescription();
					
					//dynamically discover size of image or retrieve from db
					floorMap = new MapGrid(mapWidth,mapHeight);
					pathFinder = new AStarPathFinder(floorMap,500,true);
					calculatePath(unit1,unit2);
					calculateSteps();
			    	System.out.println("calculated Path and Steps");
					
					for (int i=0; i<displayedUnits.size();i++)
					{
						Unit currentUnit = (Unit) displayedUnits.get(i);
						placesX[i]=Integer.toString(currentUnit.getX()); 
						placesY[i]=Integer.toString(currentUnit.getY()); 
						placesImages[i]= currentUnit.getUnitIconUrl();
						System.out.println(currentUnit.getUnitName());
						System.out.println(currentUnit.getUnitIconUrl());
					}
				}	
				// If the two units are NOT on the same map
				else
				{
					
				}
			}
			  
			paintTarget.addAttribute("mapImageUrl", mapImageUrl);
			paintTarget.addAttribute("mapWidth", mapWidth);
			paintTarget.addAttribute("mapHeight", mapHeight);
			paintTarget.addAttribute("mapDescription", mapDescription);
			target.addAttribute("placesX",placesX);
			target.addAttribute("placesY",placesY);
			target.addAttribute("placesImages",placesImages);
		}
		else if (map != null)//show maps only
		{
			System.out.println("Show map only");
			mapImageUrl = map.getImageUrl();
			mapWidth = map.getMapWidth();
			mapHeight = map.getMapHeight();
			mapDescription = map.getMapDescription();
			
			System.out.println(mapImageUrl+mapWidth+mapHeight+mapDescription);
			
			paintTarget.addAttribute("mapImageUrl", mapImageUrl);
			paintTarget.addAttribute("mapWidth", mapWidth);
			paintTarget.addAttribute("mapHeight", mapHeight);
			paintTarget.addAttribute("mapDescription", mapDescription);
		}		
				
		if (path!=null){
			paintTarget.addAttribute("startXPath",startXPath);
			paintTarget.addAttribute("startYPath",startYPath);
			paintTarget.addAttribute("endXPath",endXPath);
			paintTarget.addAttribute("endYPath",endYPath);
			paintTarget.addAttribute("stepsX",stepsX);
			paintTarget.addAttribute("stepsY",stepsY);
		}
	}
	
public void calculateSteps()
{
		if (path!=null){
			stepsX = new String[path.getLength()];
			stepsY = new String[path.getLength()];
			
			//Initialize Parameters		
			stepsArray = new String[path.getLength()];
			newDataSource = null;
			stepsCounter=1;
			
			startX = path.getX(0);
			startY = path.getY(0);
			endX = path.getX((path.getLength()-1));
			endY = path.getY((path.getLength()-1));
			
			System.out.println("Starting X:"+startX );
			System.out.println("Starting Y:"+startY);
			System.out.println("Ending X:"+endX);
			System.out.println("Ending Y:"+endY);
			
			for (int i=0; i<path.getLength();i++)
			{
				stepsX[i]= Integer.toString(path.getX(i));
				stepsY[i]= Integer.toString(path.getY(i));;		
			}
			
			showSteps();
			
			startXPath=path.getX(0);
			startYPath=path.getY(0);
			endXPath=path.getX(path.getLength()-1);
			endYPath=path.getY(path.getLength()-1);
			
			//Show Results
			newDataSource = StepContainer.getStepContainer(stepsCounter, stepsArray);
		}
		// We could also set variables in which values can be returned
		// but declaring variables here is not required
	}

	/**
	 * Receive and handle events and other variable changes from the client.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		super.changeVariables(source, variables);

		// Variables set by the widget are returned in the "variables" map.
		if (variables.containsKey("clickedX") && variables.containsKey("clickedY")
			&& variables.containsKey("unitIconUrl")) {
			Integer clickedX = (Integer)variables.get("clickedX");
			Integer clickedY = (Integer)variables.get("clickedY");
			String unitIconUrl = ((String)variables.get("unitIconUrl"));

			System.out.println ("clickedX: " + clickedX + "clickedY: " + clickedY 
					            + "unitIconUrl" + variables.get("unitIconUrl"));
			//imageUrl = imageUrl.substring(imageNumber.indexOf("_") + 1,imageNumber.indexOf(".png"));
			
			//Unit selectedUnit = displayedUnits.get(Integer.parseInt(imageNumber) -1);
			
			int i = 0;
			while (!unitIconUrl.contains(displayedUnits.get(i).getUnitIconUrl()))
				i++;
			
			Unit selectedUnit = displayedUnits.get(i);
			
			Window subWindow = new Window (selectedUnit.getUnitName());
			subWindow.setModal(true);
			
	        subWindow.setWidth("50%");
	        subWindow.setHeight("50%");
			
//			subWindow.setPositionX(clickedX.intValue());
//			subWindow.setPositionY(clickedY.intValue());
			
			subWindow.center();
			
			HorizontalLayout layout = new HorizontalLayout();
			layout.setMargin(true);
			layout.setSpacing(true);

			Embedded placeImage = selectedUnit.getImageUrl();
			Label placeDescription = new Label(selectedUnit.getDescription());
			layout.addComponent(placeImage);
			layout.addComponent(placeDescription);

			subWindow.setContent(layout);						
			getWindow().addWindow(subWindow);	
		}
		
//		if (variables.containsKey("click")) {
//
//			// When the user has clicked the component we increase the 
//			// click count, update the message and request a repaint so 
//			// the changes are sent back to the client.
//			clicks++;
//			message += "<br/>" + variables.get("click");
//
//			requestRepaint();
//		}
	}

	public ArrayList<Unit> getDisplayedUnits() {
		return displayedUnits;
	}

	public void setDisplayedUnits(ArrayList<Unit> displayedUnits) {
		System.out.println("Setting Displayed Units");
		this.displayedUnits = null;
		this.displayedUnits = displayedUnits;
		requestRepaint();
	}
	
	public void setDisplayedUnit(Unit displayedUnit) {
		System.out.println("Setting Displayed Unit");
		displayedUnits = new ArrayList<Unit> (1);
		this.displayedUnits.add(displayedUnit);
		requestRepaint();
	}
	
	public void addDisplayedUnits(ArrayList<Unit> displayedUnits) {
		System.out.println("Setting Displayed Units");
		this.displayedUnits.addAll(displayedUnits);
		requestRepaint();
	}
	
	public void calculatePath(Unit startUnit, Unit endUnit) {
		path=pathFinder.findPath(null, startUnit.getX(),startUnit.getY(),
				                 endUnit.getX(),endUnit.getY());
	}
	
	public void setMap(com.boslla.maps.containers.Map selectedMap)
	{
		System.out.println("Setting Map");
		this.map = selectedMap;
		requestRepaint();
	}
	
	public void clearPath() {
		path=null;
	}
	
	public void showSteps(){
		
		int i =1;
		int distance=0;
		float realDistance = 0;
		float mapScale = map.getMapScale();
		int X;
		int Y;
		int previousX;
		int previousY;
		String previousMapDirection="";
		String currentMapDirection="";
		
		//Starting point
		previousMapDirection="Give your back to the current Location.";
		stepsArray[stepsCounter] = previousMapDirection;
		stepsCounter++;
		
		while (i<path.getLength())
		{			

			previousMapDirection=currentMapDirection;
			
			previousX= path.getX(i-1);
			previousY= path.getY(i-1);
			X = path.getX(i);
			Y = path.getY(i);
		
			System.out.println("X:"+X+"- Y:"+Y+"- NextX:"+previousX+"- NextY:"+previousY);
						
			if (previousX < X  && previousY > Y )
			{
				currentMapDirection = "Move right Upward Diagonal for about ";
			}
			else if (previousX < X  && previousY < Y )	
			{
				currentMapDirection = "Move right Downward Diagonal for about ";;
			}
			else if (previousX > X  && previousY > Y )	
			{
				currentMapDirection = "Move left Upward Diagonal for about ";
			} 
			else if (previousX > X  && previousY < Y )	
			{
				currentMapDirection = "Move left Downward Diagonal for about ";
			} 
			else if (previousX < X   && Y == previousY )	
			{
				currentMapDirection = "Move right for about ";
			} 
			else if (previousX > X   && Y == previousY )	
			{
				currentMapDirection = "Move left for about ";
			}
			else if (previousY < Y  && X == previousX )	
			{
				currentMapDirection = "Move downward for about ";
			} 
			else if (previousY > Y  && X == previousX )	
			{
				currentMapDirection = "Move upward for about ";
			} 
			
			System.out.println("previousMapDirection="+previousMapDirection);
			System.out.println("currentMapDirection="+currentMapDirection);
			
			if (!previousMapDirection.equals(currentMapDirection)&& distance!=0)
			{	
				realDistance = distance*mapScale;
				stepsArray[stepsCounter] = previousMapDirection+realDistance+" "+unit+".";	
				stepsCounter++;
				distance=-1;
			}
		
			if (i==(path.getLength()-1))
			{
				realDistance = distance*mapScale;
				stepsArray[stepsCounter] = previousMapDirection+realDistance+" "+unit+".";	
				stepsCounter++;
				currentMapDirection = "You have reached your Destination.";
				stepsArray[stepsCounter] = currentMapDirection;
				stepsCounter++;
			}	
			
			distance++;	//Move one step forward
			i++; // Increment Counter
		}
	}
	
	public StepContainer getStepDataSource()
	{
		System.out.println(newDataSource.size());
		return newDataSource;
	}
	
	public String [] getStepsArray()
	{
		return stepsArray;
	}
	
	public int getStepsCounter()
	{
		return stepsCounter;
	}
	
	public void clearView()
	{
		this.map=null;
		this.displayedUnits=null;
	}
	
/*	  public void setMapImageUrl(String mapImageUrl) {
			this.mapImageUrl = mapImageUrl;
		}
		 public String getMapImageUrl() {
			return mapImageUrl;
		}
*/	
//	public String[] getSteps()
//	{
//		for (int i=1; i<= steps.length; i++)
//		{
//			System.out.println(steps[i]);
//		}
//		return steps;
//	}
}