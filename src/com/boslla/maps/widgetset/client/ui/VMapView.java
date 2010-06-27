package com.boslla.maps.widgetset.client.ui;

import java.util.ArrayList;

import com.vaadin.contrib.gwtgraphics.client.DrawingArea;
import com.vaadin.contrib.gwtgraphics.client.Line;
import com.vaadin.contrib.gwtgraphics.client.animation.Animate;
import com.vaadin.contrib.gwtgraphics.client.shape.Circle;
import com.vaadin.contrib.gwtgraphics.client.shape.Path;
import com.vaadin.contrib.gwtgraphics.client.shape.path.LineTo;
import com.vaadin.contrib.gwtgraphics.client.shape.path.PathStep;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VMapView extends Composite implements Paintable, ClickHandler,
MouseDownHandler, MouseUpHandler, MouseMoveHandler {

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-mapview";

	public static final String CLICK_EVENT_IDENTIFIER = "click";

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection client;

	private TextBox textBox;
	
	private AbsolutePanel panel;
	
	private DrawingArea canvas;

	private int width = 1000;

	private int height = 1000;
	
	//x and y coordinates for MouseClick
	private int x=0;
	
	private int y=0;
	
	//Path Variables
	
	int startXPath =0;
	int startYPath =0; 
	int endXPath =0; 
	int endYPath=0;
	
	String[] stepsX=null;
	String[] stepsY=null;
	
	String[] placesX=null;
	String[] placesY=null;
	
	String[] obstaclesX;
	String[] obstaclesY;
	
	private int windowWidth=1243;
	private int windowHeight=931;
	
	private int mapWidth;
	private int mapHeight;
	private String mapDescription;
	private String mapImageUrl;
	private String[] placesImages = null;
//	private Image placeImage;
	private Label mapDescriptionLabel;
	private Image mapImage;
	private Image placeImage = null;
	
	private ArrayList<Circle> circleArray;
	
	private Boolean pathExists;
	
	private Boolean mouseDown;
	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VMapView() {
		
		panel = new AbsolutePanel();
		initWidget(panel);

		canvas = new DrawingArea(windowWidth, windowHeight);
		
		//FIXME removed mouse click,down and up for the time being
//		canvas.addClickHandler(this);
		canvas.addMouseMoveHandler(this);
//		canvas.addMouseDownHandler(this);
	//	canvas.addMouseUpHandler(this);
		mapDescriptionLabel = new Label();
		mapImage= new Image();
		textBox= new TextBox();
		circleArray = new ArrayList<Circle>();
		pathExists=false;
		mouseDown=false;
		
		setStyleName(CLASSNAME);
		DOM.setStyleAttribute(canvas.getElement(), "border", "1px solid black");
		
		// TODO This example code is extending the GWT Widget class so it must set a root element.
		// Change to a proper element or remove this line if extending another widget.
		//setElement(Document.get().createDivElement());
		
		// This method call of the Paintable interface sets the component
		// style name in DOM tree
		//setStyleName(CLASSNAME);
		
		// Tell GWT we are interested in receiving click events
//		sinkEvents(Event.ONCLICK);
		// Add a handler for the click events (this is similar to FocusWidget.addClickHandler())
	}

    /**
     * Called whenever an update is received from the server 
     */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		// This call should be made first. 
		// It handles sizes, captions, tooltips, etc. automatically.
		if (client.updateComponent(this, uidl, true)) {
		    // If client.updateComponent returns true there has been no changes and we
		    // do not need to update anything.
			return;
		}

		// Save reference to server connection object to be able to send
		// user interaction later
		this.client = client;

		// Save the client side identifier (paintable id) for the widget
		paintableId = uidl.getId();
		
		// Get Values from server
		// TODO get values (like width and height) from Server
		mapImageUrl= uidl.getStringAttribute("mapImageUrl");
		mapWidth = uidl.getIntAttribute("mapWidth");
		mapHeight = uidl.getIntAttribute("mapHeight");
		mapDescription = uidl.getStringAttribute("mapDescription");
		mapDescriptionLabel = new Label (mapDescription);

		// Clear Everything first
		panel.clear();
		canvas.clear();
//		circleArray.clear();
		
		// Add Canvas and Image to Panel
		panel.add(mapImage,0,0);
		panel.add(canvas,0,0);
		panel.add(textBox,350,10);
		panel.add(mapDescriptionLabel,10,10);
		
		// Set values after getting them from server
	//	panel.setSize((50 + 1 + width) + "px", (25 + height) + "px");
		panel.setSize(mapWidth + "px",mapHeight + "px");
		
		canvas.setWidth(mapWidth);		
		canvas.setHeight(mapHeight);
		canvas.getElement().getStyle().setPropertyPx("width", mapWidth);
		canvas.getElement().getStyle().setPropertyPx("height", mapHeight);
		
		mapImage.setUrl(GWT.getModuleBaseURL()+mapImageUrl);
		
		if (uidl.getStringArrayAttribute("placesX") != null) {
		  placesImages = uidl.getStringArrayAttribute("placesImages");
		  placesX = uidl.getStringArrayAttribute("placesX");
		  placesY = uidl.getStringArrayAttribute("placesY");
		  
		  for (int i = 0; i< placesX.length; i++){
			int placeX = Integer.parseInt(placesX[i]);
			int placeY = Integer.parseInt(placesY[i]);
	        Circle circle= new Circle (placeX,placeY,13);
	        circle.setFillColor("red");
	        canvas.add(circle);
	        placeImage = new Image();
	        placeImage.setUrl(GWT.getModuleBaseURL()+placesImages[i]);
	        placeImage.addClickHandler(this);

	        panel.add(placeImage, placeX - 15, placeY - 15);
		  }
		}
		
		if (uidl.getIntAttribute("startXPath")!=0)
		{
			pathExists=true;
			startXPath=uidl.getIntAttribute("startXPath");
			startYPath=uidl.getIntAttribute("startYPath");
			endXPath=uidl.getIntAttribute("endXPath");
			endYPath=uidl.getIntAttribute("endYPath");
			stepsX= uidl.getStringArrayAttribute("stepsX");
			stepsY= uidl.getStringArrayAttribute("stepsY");
			drawPath();
		}
	}

    private void drawPath() {
	  Line line= new Line(startXPath,startYPath,startXPath,startYPath);
	  line.setStrokeWidth(3);
	  line.setStrokeOpacity(0.5);
	  line.setStrokeColor("blue");
	 canvas.add(line);
//		 //Animated Path Line
	 new Animate(line,"x2",startXPath,endXPath,600).start();
	 new Animate(line,"y2",startYPath,endYPath,600).start();
	 pathExists=true;	 
	 if (stepsX!=null&& stepsY!=null)
	 {
		
		 for (int i=0; i<stepsX.length;i++)
		 {
			Line stepLine= new Line(Integer.parseInt(stepsX[i]),Integer.parseInt(stepsY[i]),
					Integer.parseInt(stepsX[i+1]),Integer.parseInt(stepsY[i+1]));
			
    		 stepLine.setStrokeWidth(4);
    		 stepLine.setStrokeOpacity(0.5);
    		 stepLine.setStrokeColor("green");
    		 
			canvas.add(stepLine);
		 }
	 }
	}
    
	/**
     * Called when a native click event is fired.
     * 
     * @param event
     *            the {@link ClickEvent} that was fired
     */
     public void onClick(ClickEvent event) {
   // 	if (placeImage != null && event.getSource().getClass() == placeImage.getClass()) {
    		Image image = (Image) event.getSource();
    	 	client.updateVariable(paintableId, "clickedX", x, false);
    		client.updateVariable(paintableId, "clickedY", y, false);
    		client.updateVariable(paintableId, "unitIconUrl", image.getUrl(), true);
   // 	}
//       // Send a variable change to the server side component so it knows the widget has been clicked
//		String button = "left click";
//		// The last parameter (immediate) tells that the update should be sent to the server
//		// right away
//		client.updateVariable(paintableId, CLICK_EVENT_IDENTIFIER, button, true);
	}

	public void onMouseDown(MouseDownEvent event) {
		
		 x = event.getRelativeX(canvas.getElement());
		 y = event.getRelativeY(canvas.getElement());

		 canvas.getElement().getStyle().setProperty("cursor", "pointer");
		 mouseDown=true;
	}

	public void onMouseUp(MouseUpEvent event) {
		mouseDown=false;
		canvas.getElement().getStyle().setProperty("cursor", "default");
	}

	public void onMouseMove(MouseMoveEvent event) {

		x=event.getRelativeX(canvas.getElement());
		y=event.getRelativeY(canvas.getElement());
		
		textBox.setText("X= "+ Integer.toString(x) + " Y= " + Integer.toString(y));
//TODO uncomment code for scrolling		
	if (mouseDown){
			int newX=event.getRelativeX(canvas.getElement());
			int newY=event.getRelativeY(canvas.getElement());
			
			int difX= newX-x;
			int difY=newY-y;
			
			int newLeft= canvas.getAbsoluteLeft() + difX;
			int newTop= canvas.getAbsoluteTop() + difY;
		
    		mapImage.setVisibleRect(newLeft, newTop, canvas.getWidth(),canvas.getHeight());
		}
		 
	}
}