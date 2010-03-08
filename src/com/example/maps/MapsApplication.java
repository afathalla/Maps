package com.example.maps;


import com.vaadin.Application;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MapsApplication extends Application implements Button.ClickListener{
	  private SplitPanel horizontalSplit = new SplitPanel(
	            SplitPanel.ORIENTATION_HORIZONTAL);
	  private Button searchButton = new Button("Search");
	  private Button logoutButton = new Button("Logout");
	  private TextField startText= new TextField();
	  private TextField endText= new TextField();
	  private Label searchLabel= new Label ("<h2>Find Directions</h2>",Label.CONTENT_XHTML);
	  private ProgressIndicator progressIndicator= new ProgressIndicator(new Float(0.0));
	//  private MapPanel mapPanel = new MapPanel();
	  private MapView mapView= new MapView();
	  
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
			setMainComponent(mapView);
//			mapPanel.fetchMap();
				
		}
			
	}
	
	private void setMainComponent(Component c){
		horizontalSplit.setSecondComponent(c);
	}
}
