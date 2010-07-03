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
	}
	public void login() {
		Window loginWindow = new Window("Login to Maps Administration");
		loginWindow.setModal(true);
		loginWindow.setWidth("50%");
		loginWindow.setHeight("50%");
		loginWindow.center();
		LoginForm loginForm = new LoginForm();
		loginForm.setCaption("Login");
        loginForm.addListener(new LoginForm.LoginListener() {
            public void onLogin(LoginEvent event) {
                getWindow().showNotification(
                        "New Login",
                        "Username: " + event.getLoginParameter("username")
                                + ", password: "
                                + event.getLoginParameter("password"));
            }
        });

		VerticalLayout loginLayout = new VerticalLayout();
		loginLayout.addComponent(loginForm);
		loginWindow.setContent(loginLayout);
		this.addWindow(loginWindow);
	}

}
