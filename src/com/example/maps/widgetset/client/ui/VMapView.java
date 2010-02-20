package com.example.maps.widgetset.client.ui;

import java.util.ArrayList;

import com.vaadin.contrib.gwtgraphics.client.DrawingArea;
import com.vaadin.contrib.gwtgraphics.client.Line;
import com.vaadin.contrib.gwtgraphics.client.animation.Animate;
import com.vaadin.contrib.gwtgraphics.client.shape.Circle;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
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

	private AbsolutePanel panel;
	
	private DrawingArea canvas;
	
	private Image mapImage;

	private int width = 500;

	private int height = 500;
	
	//x and y coordinates for MouseClick
	private int x=0;
	
	private int y=0;
	
	private String imageUrl = null;
	
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

		canvas = new DrawingArea(width, height);
		//FIXME removed click handler for the time being
		//canvas.addClickHandler(this);
		canvas.addMouseMoveHandler(this);
		canvas.addMouseDownHandler(this);
		canvas.addMouseUpHandler(this);
		mapImage= new Image();
		
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

		// Clear Everything first
		panel.clear();
		canvas.clear();
		circleArray.clear();
		
		// Add Canvas and Image to Panel
		panel.add(mapImage,0,0);
		panel.add(canvas,0,0);
		
		// Get Values from server
		// TODO get values (like width and height) from Server
		imageUrl= uidl.getStringAttribute("imageurl");
		
		// Set values after getting them from server
//		panel.setSize((50 + 1 + width) + "px", (25 + height) + "px");
		panel.setSize(width + "px",height + "px");
		
		canvas.setWidth(width);		
		canvas.setHeight(height);
		canvas.getElement().getStyle().setPropertyPx("width", width);
		canvas.getElement().getStyle().setPropertyPx("height", height);
		
		mapImage.setUrl(imageUrl);
		
//		// Process attributes/variables from the server
//		// The attribute names are the same as we used in 
//		// paintContent on the server-side
//		int clicks = uidl.getIntAttribute("clicks");
//		String message = uidl.getStringAttribute("message");
//		
//		getElement().setInnerHTML("After <b>"+clicks+"</b> mouse clicks:\n" + message);
		
	}

    /**
     * Called when a native click event is fired.
     * 
     * @param event
     *            the {@link ClickEvent} that was fired
     */
     public void onClick(ClickEvent event) {
	
    	//Create a new initial point the user wants to get from
    	 if (circleArray.size() < 2)
    	 {
        	Circle circle= new Circle (x,y,7);
        	circle.setFillColor("red");
        	canvas.add(circle);
        	circleArray.add(circle);
    	 }
    	 
    	 if (circleArray.size()==2 && !pathExists)
    	 {
    		 Circle c1= circleArray.get(0);
    		 Circle c2= circleArray.get(1);
    		 
//    		 Line line= new Line(c1.getX(),c1.getY(),c2.getX(),c2.getY());
    		 Line line= new Line(c1.getX(),c1.getY(),c1.getX(),c1.getY());
    		 line.setStrokeWidth(3);
    		 line.setStrokeOpacity(0.5);
    		 line.setStrokeColor("blue");
    		 canvas.add(line);
    		 pathExists=true;
    		 new Animate(line,"x2",c1.getX(),c2.getX(),600).start();
    		 new Animate(line,"y2",c1.getY(),c2.getY(),600).start();
    		 
    	 }
    	 
       // Send a variable change to the server side component so it knows the widget has been clicked
		String button = "left click";
		// The last parameter (immediate) tells that the update should be sent to the server
		// right away
		client.updateVariable(paintableId, CLICK_EVENT_IDENTIFIER, button, true);
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
