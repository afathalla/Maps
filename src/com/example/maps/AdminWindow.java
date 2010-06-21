package com.example.maps;

import com.vaadin.data.Property;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.data.*;
import com.vaadin.data.Property.ValueChangeEvent;

public class AdminWindow extends Window implements Property.ValueChangeListener{
	private Label status = new Label("-");
	
	public AdminWindow(String name) {
		super("name");
	}
	public void buildLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		SplitPanel horizontalAdminSplit = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);
		horizontalAdminSplit.setFirstComponent(buildAdminPanel());
		horizontalAdminSplit.setSplitPosition(400,SplitPanel.UNITS_PIXELS);
		layout.addComponent(horizontalAdminSplit);
		layout.setExpandRatio(horizontalAdminSplit, 1);
		this.setContent(layout);	
	}
	
	public Component buildAdminPanel() {
		Panel adminPanel = new Panel("Administration Tasks");
		adminPanel.setWidth(Sizeable.SIZE_UNDEFINED,0);
		FormLayout adminForm = new FormLayout();
		adminForm.setMargin(true);
		Select select = new Select ("Select Task");
		String [] tasks = new String[]{"Add new places on map",
									   "Change properties of existing place",
									   "Change current map"};
		for (int i=0; i<tasks.length;i++)
			select.addItem(tasks[i]);
		select.setImmediate(true);
		select.addListener(this);
		
		adminForm.addComponent(select);
		adminForm.addComponent(status);
		adminPanel.setContent(adminForm);
		return adminPanel;	
	}
	@Override
	public void valueChange(ValueChangeEvent event) {
		status.setValue("Changed " + event.getProperty());
	}

}
