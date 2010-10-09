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
		 String [] tasks = new String[]{"Add new Place",
		 "Change properties of Existing Place",
		 "Add new Map",
		 "Change properties of Existing Map"};
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
		status.setValue("Changed " + event.getProperty());
		
		if (event.getProperty().toString().equals("Add new Map")) {
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
		
		else if (event.getProperty().toString().equals("Add new Place")) {
			if (adminForm!= null) {
				adminLayout.removeComponent(adminForm);
			}
			adminForm = new Form();
			adminForm.setCaption(event.getProperty().toString());
			adminForm.setDescription("Enter the details of the new Place you want to add");
			adminForm.setFormFieldFactory(new AdminFormFieldFactory("Place"));
			adminForm.setImmediate(true);
			adminForm.setValidationVisibleOnCommit(true);
			final Place newPlace = new Place();
			BeanItem item = new BeanItem(newPlace);
			adminForm.setItemDataSource(item);
			
			HorizontalLayout buttonLayout = new HorizontalLayout();
			buttonLayout.setHeight("25px");
			adminForm.getFooter().addComponent(buttonLayout);
			saveButton = new Button("Save",new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
	                try {
	                    adminForm.commit();
	                    PlaceContainer.savePlace(newPlace);
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
		else if (event.getProperty().toString().equals("Change properties of Existing Map")) {
			adminLayout.removeAllComponents();
			
			MapContainer existingMaps = MapContainer.getAllMaps();
			Select mapSelect = new Select("Choose Map to Edit", existingMaps);
			mapSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
			mapSelect.setItemCaptionPropertyId("mapName");
			adminLayout.addComponent(mapSelect);
		}
	}
	@Override
	public void buttonClick(ClickEvent event) {
		Button sourceButton= event.getButton();
		
		if (sourceButton == signOutButton) {
			this.getApplication().close();
		}	
		
		if (sourceButton == loginButton) {
		    validateLogin();    
		}
	}
	private void validateLogin() {
		if (loginForm.isValid()) {
			if (userText.getValue().equals("a") && passText.getValue().equals("a")) {
				buildLayout();
			    this.removeWindow(loginWindow);
			}
			else {
				errorLabel.setCaption("Invalid Credentials, please retry");
			}
		}
		else {
			errorLabel.setCaption("Some field is missing, please retry");
		}
	}
	public void login() {
		loginWindow = new Window("Maps Administration");
		loginWindow.setModal(true);
		loginWindow.setWidth("25%");
		loginWindow.setHeight("50%");
		loginWindow.center();
		loginForm = new Form();
		loginForm.setCaption("Login");
		userText = new TextField("Username");
		passText = new TextField ("Password");
		passText.setSecret(true);
		loginButton= new Button("Login");
		errorLabel = new Label();
		loginButton.addListener((ClickListener)this);
		loginForm.getLayout().addComponent(userText);
		loginForm.getLayout().addComponent(passText);
		loginForm.setFooter(new VerticalLayout());
		loginForm.getFooter().addComponent(loginButton);
		loginForm.getFooter().addComponent(errorLabel);
		
		loginWindow.addComponent(loginForm);
		this.addWindow(loginWindow);
	}
}
