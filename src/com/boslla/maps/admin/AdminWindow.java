package com.boslla.maps.admin;

import com.vaadin.data.Property;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.data.*;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.boslla.maps.containers.*;


public class AdminWindow extends Window implements Property.ValueChangeListener,Button.ClickListener{
	private Label status = new Label("-");
	private Button signOutButton;
	private Button loginButton;
	private Button saveButton;
	private Button resetButton;
	private Window loginWindow;
	private Form loginForm;
	private TextField userText;
	private TextField passText;
	private Label errorLabel;
	private Form adminForm;
	private FormLayout adminLayout;
	
	public AdminWindow(String name) {
		super(name);
	}
	public void buildLayout() {		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		SplitPanel horizontalAdminSplit = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);
		horizontalAdminSplit.setFirstComponent(buildAdminPanel());
		horizontalAdminSplit.setSplitPosition(600,SplitPanel.UNITS_PIXELS);
		layout.addComponent(horizontalAdminSplit);
		layout.setExpandRatio(horizontalAdminSplit, 1);
		this.setContent(layout);	
	}
	
	public Component buildAdminPanel() {
		 Panel adminPanel = new Panel("Administration Tasks");
		 adminLayout = new FormLayout();
		 adminLayout.setMargin(true);
		 signOutButton = new Button("Sign Out of Admin");
		 signOutButton.setStyleName("link");
		 signOutButton.addListener((ClickListener)this);
		 Select select = new Select ("Select Task");
		 String [] tasks = new String[]{"Add Place",
		 "Edit Place",
		 "Add Map",
		 "Edit Map",
		 "Add Unit",
		 "Edit Unit"};
		 for (int i=0; i<tasks.length;i++)
		 select.addItem(tasks[i]);
		 select.setImmediate(true);
		 select.addListener(this);

		 adminLayout.addComponent(signOutButton);
		 adminLayout.addComponent(select);
		 adminLayout.addComponent(status);
		 adminPanel.setContent(adminLayout);
		 return adminPanel;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
	//	status.setValue("Changed " + event.getProperty());		
		 if (event.getProperty().toString().equals("Add Place")) {
			buildNewForm("Place",event);
		} else if (event.getProperty().toString().equals("Add Map")) {
			buildNewForm("Map",event);
		} else if (event.getProperty().toString().equals("Add Unit")) {
			buildNewForm("Unit",event);
		}
		else if (event.getProperty().toString().equals("Edit Map")) {
			buildEditMapForm();
		}
	}
	private void buildEditMapForm() {
		adminLayout.removeAllComponents();
		
		MapContainer existingMaps = MapContainer.getAllMaps();
		Select mapSelect = new Select("Choose Map to Edit", existingMaps);
		mapSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		mapSelect.setItemCaptionPropertyId("mapName");
		adminLayout.addComponent(mapSelect);
	}
	private void buildNewForm(final String className,ValueChangeEvent event) {
		if (adminForm!= null) {
			adminLayout.removeComponent(adminForm);
		}
		adminForm = new Form();
		adminForm.setCaption(event.getProperty().toString());
		adminForm.setFormFieldFactory(new AdminFormFieldFactory(className));
		adminForm.setImmediate(true);
		adminForm.setValidationVisibleOnCommit(true);
		final Place newPlace = new Place();
		final Map newMap = new Map();
		final Unit newUnit = new Unit();
		if (className == "Place") {
			BeanItem item = new BeanItem(newPlace);
			adminForm.setItemDataSource(item);
		} else if (className == "Map") {
			BeanItem item = new BeanItem(newMap);
			adminForm.setItemDataSource(item);
		} else if (className == "Unit") {
			BeanItem item = new BeanItem(newUnit);
			adminForm.setItemDataSource(item);
		}
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setHeight("25px");
		adminForm.getFooter().addComponent(buttonLayout);
		saveButton = new Button("Save",new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
		        try {
		            adminForm.commit();
		            System.out.println("Class Name is " + className);
		            if (className == "Place") {
		            	PlaceContainer.savePlace(newPlace);
		            }
		            else if (className == "Map") {
		            	MapContainer.saveMap(newMap);
		            }
		            else if (className == "Unit") {
		            	//UnitContainer.saveUnit(newUnit);
		            }
		            
		        } catch (Exception e) {
		            // Ignored, we'll let the Form handle the errors
		        }
		    }
		});

		resetButton = new Button("Reset",new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
		            adminForm.discard();    
			}
		});
		buttonLayout.addComponent(saveButton);
		buttonLayout.addComponent(resetButton);
		adminLayout.addComponent(adminForm);
	}
	private void buildNewMapForm(ValueChangeEvent event) {
		if (adminForm!= null) {
			adminLayout.removeComponent(adminForm);
		}
		adminForm = new Form();
		adminForm.setCaption(event.getProperty().toString());
		adminForm.setDescription("Enter the details of the new Map you want to add");
		adminForm.setFormFieldFactory(new AdminFormFieldFactory("Map"));
		adminForm.setImmediate(true);
		adminForm.setValidationVisibleOnCommit(true);
		final Map newMap = new Map();
		BeanItem item = new BeanItem(newMap);
		adminForm.setItemDataSource(item);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setHeight("25px");
		adminForm.getFooter().addComponent(buttonLayout);
		saveButton = new Button("Save",new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
		        try {
		            adminForm.commit();
		            MapContainer.saveMap(newMap);
		        } catch (Exception e) {
		            // Ignored, we'll let the Form handle the errors
		        }
		    }
		});

		resetButton = new Button("Reset",new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
		            adminForm.discard();    
			}
		});
		buttonLayout.addComponent(saveButton);
		buttonLayout.addComponent(resetButton);
		adminLayout.addComponent(adminForm);
	}
	@Override
	public void buttonClick(ClickEvent event) {
		Button sourceButton= event.getButton();
		
		if (sourceButton == signOutButton) {
			this.getApplication().close();
		}	
	}


}
