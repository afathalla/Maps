package com.boslla.maps;

import javax.xml.bind.ValidationException;
import com.boslla.maps.containers.Feedback;
import com.boslla.maps.containers.FeedbackContainer;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

public class FeedbackView extends MapsApplication implements Button.ClickListener{
	
	public FeedbackView()
	{
		
	}
	
	public void addFeedbackWindow()	{
		
		final Window subWindow = new Window("What do you think about Boslla Indoor Maps ?");
		subWindow.setModal(true);
	    subWindow.setWidth("35%");
	    subWindow.setHeight("70%");
	    subWindow.center();
	    subWindow.setScrollable(false);
	    subWindow.setResizable(false);

	    final Form form = new Form();
	    
	    final FormLayout formLayout = new FormLayout();
	    formLayout.setSpacing(true);
	    
	    final OptionGroup typeOptiongroup = new OptionGroup("Type");
	    typeOptiongroup.setNullSelectionAllowed(false);

	    String [] feedbackTypes = FeedbackContainer.getFeedbackTypes();
	    
	    for (int i=0; i<feedbackTypes.length;i++)
	    {
	    	typeOptiongroup.addItem(feedbackTypes[i]);
	    }
	    
	    final TextField name = new TextField("Name");
	    name.setColumns(25);
	    name.setInputPrompt("Add your name");
	    final TextField email = new TextField("Email");
	    email.setColumns(25);
	    email.setInputPrompt("Add your email");
	    
	    Label label = new Label("N.B If you do not provide your name or email, the source of your feedback will be treated as anonymous.");
	    
	    final TextField title = new TextField("Title");
	    title.setColumns(25);
	    title.setInputPrompt("Sum up your feedback");
	    final TextField description = new TextField("Description");
	    description.setInputPrompt("Describe your feedback");
	    description.setColumns(25);
	    description.setRows(7);
	    	    
	    Button submitFeedbackButton = new Button("Submit Feedback");
	    submitFeedbackButton.addListener((ClickListener)this);
	    
	    Button resetFeedbackButton = new Button("reset");
	    resetFeedbackButton.addListener((ClickListener)this);
	    resetFeedbackButton.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				System.out.println("Discard");
			}
			
		});
	    
	    submitFeedbackButton.addListener(new Button.ClickListener() {
		public void buttonClick(ClickEvent event) {
			
			Notification n;
			
			try
			{
				form.commit();
			}
			catch( EmptyValueException e)
			{
				n = new Notification("",e.getMessage()+" Please make sure to have all the required fields filled in.",Notification.TYPE_ERROR_MESSAGE);
				n.setDelayMsec(1000); // sec->msec
				MapsApplication.layout.getWindow().showNotification(n);
				
				return;
			}
				Feedback feedback = new Feedback();
    			
    			feedback.setName(name.getValue().toString());
    			feedback.setEmail(email.getValue().toString());
    			feedback.setTitle(title.getValue().toString());
    			feedback.setType(typeOptiongroup.getValue().toString());
    			feedback.setDescription(description.getValue().toString());
    			    			
    			if (FeedbackContainer.insertFeedback(feedback))
    			{
    				 n = new Notification("Feedback Saved","",Notification.TYPE_HUMANIZED_MESSAGE);
    				 n.setDelayMsec(1000); // sec->msec
    				 
    				 MapsApplication.layout.getWindow().removeWindow(subWindow);
    			}
    			else 
    			{
    				n = new Notification("Unable to save feedback, please try again later.","",Notification.TYPE_ERROR_MESSAGE);
    				n.setDelayMsec(1000); // sec->msec   
    			}

        	
    			MapsApplication.layout.getWindow().showNotification(n);
		}
        });

	    form.addField("name",name);
	    form.getField("name").setRequired(false);
	    //form.getField("name").setRequiredError("Name is missing");

	    form.addField("email",email);
	    form.getField("email").setRequired(false);
	    //form.getField("email").setRequiredError("Email is missing");
	    
	    form.getLayout().addComponent(label);
	    
	    form.addField("type", typeOptiongroup);
	    form.getField("type").setRequired(true);
	    form.getField("type").setRequiredError("Type is missing.");
	    
	    form.addField("title",title);
	    form.getField("title").setRequired(true);
	    form.getField("title").setRequiredError("Title is missing.");
	    
	    form.addField("description",description);
	    
	    form.getFooter().addComponent(submitFeedbackButton);
	    form.getFooter().addComponent(resetFeedbackButton);
	    
	    form.setValidationVisible(false);
	    form.setValidationVisibleOnCommit(false);
	    
	    formLayout.addComponent(form);
		subWindow.setContent(formLayout);
		MapsApplication.layout.getWindow().addWindow(subWindow);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

}