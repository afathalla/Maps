package com.boslla.maps;

import com.vaadin.data.Property;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.data.*;
import com.vaadin.data.Property.ValueChangeEvent;


public class AdminWindow extends Window implements Property.ValueChangeListener,Button.ClickListener{
	private Label status = new Label("-");
	private Button signOutButton;
	private Button loginButton;
	private Window loginWindow;
	private Form loginForm;
	private TextField userText;
	private TextField passText;
	private Label errorLabel;
	
	public AdminWindow(String name) {
		super(name);
	}
	public void buildLayout() {		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		SplitPanel horizontalAdminSplit = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);
		horizontalAdminSplit.setFirstComponent(buildAdminPanel());
		horizontalAdminSplit.setSplitPosition(300,SplitPanel.UNITS_PIXELS);
		layout.addComponent(horizontalAdminSplit);
		layout.setExpandRatio(horizontalAdminSplit, 1);
		this.setContent(layout);	
	}
	
	public Component buildAdminPanel() {
		 Panel adminPanel = new Panel("Administration Tasks");
		 FormLayout adminForm = new FormLayout();
		 adminForm.setMargin(true);
		 signOutButton = new Button("Sign Out of Admin");
		 signOutButton.setStyleName("link");
		 signOutButton.addListener((ClickListener)this);
		 Select select = new Select ("Select Task");
		 String [] tasks = new String[]{"Add new place on map",
		 "Change properties of existing place",
		 "Change current map"};
		 for (int i=0; i<tasks.length;i++)
		 select.addItem(tasks[i]);
		 select.setImmediate(true);
		 select.addListener(this);

		 adminForm.addComponent(signOutButton);
		 adminForm.addComponent(select);
		 adminForm.addComponent(status);
		 adminPanel.setContent(adminForm);
		 return adminPanel;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		status.setValue("Changed " + event.getProperty());
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
			if (userText.getValue().equals("admin") && passText.getValue().equals("password")) {
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
		
//		LoginForm loginForm = new LoginForm();
//		loginForm.setCaption("Login");
//        loginForm.addListener(new LoginForm.LoginListener() {
//            public void onLogin(LoginEvent event) {
//                getWindow().showNotification(
//                        "New Login",
//                        "Username: " + event.getLoginParameter("username")
//                                + ", password: "
//                                + event.getLoginParameter("password"));
//            }
//        });
		loginWindow.addComponent(loginForm);
		this.addWindow(loginWindow);
	}

}
