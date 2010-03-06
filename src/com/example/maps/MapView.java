package com.example.maps;

import java.util.Map;
import com.example.maps.navigate.MapGrid;
import com.maps.util.*;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;

/**
 * Server side component for the VMapView widget.
 */
@com.vaadin.ui.ClientWidget(com.example.maps.widgetset.client.ui.VMapView.class)
public class MapView extends AbstractComponent {

//	private String message = "Click here.";
//	private int clicks = 0;
	//FIXME this has to be dynamic
	private String imageUrl = "http://totheweb.com/eichler/images/subpage_photos/floor-plan-1224.gif";
	//TODO dynamically discover size of image or retrieve from db
	private MapGrid floorMap = new MapGrid(881,1164);
	private AStarPathFinder pathFinder = new AStarPathFinder(floorMap,500,true);
	private Path path= null; //Path from the two user-clicked points
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);

		// Paint any component specific content by setting attributes
		// These attributes can be read in updateFromUIDL in the widget.
//		target.addAttribute("clicks", clicks);
//		target.addAttribute("message", message);
		target.addAttribute("imageurl", imageUrl);
	if (path!=null){
		String[] stepsX = new String[path.getLength()];
		String[] stepsY = new String[path.getLength()];

		for (int i=0; i<path.getLength();i++)
		{
			stepsX[i]= Integer.toString(path.getX(i));
			stepsY[i]= Integer.toString(path.getY(i));
		}
		int startXPath=path.getX(0);
		int startYPath=path.getY(0);
		int endXPath=path.getX(path.getLength()-1);
		int endYPath=path.getY(path.getLength()-1);
		target.addAttribute("startXPath",startXPath);
		target.addAttribute("startYPath",startYPath);
		target.addAttribute("endXPath",endXPath);
		target.addAttribute("endYPath",endYPath);
		target.addAttribute("stepsX",stepsX);
		target.addAttribute("stepsY",stepsY);
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

		if (variables.containsKey("startX") && variables.containsKey("endX") 
		    && variables.containsKey("startY") && variables.containsKey("endY"))
		{
			path=pathFinder.findPath(null, Integer.parseInt(variables.get("startX").toString()),
					Integer.parseInt(variables.get("startY").toString()), 
					Integer.parseInt(variables.get("endX").toString()),
					Integer.parseInt(variables.get("endY").toString()));
			
			requestRepaint();
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

}
