package com.boslla.maps;

import javax.xml.bind.ValidationException;
import com.boslla.maps.containers.Feedback;
import com.boslla.maps.containers.FeedbackContainer;
import com.boslla.maps.containers.Review;
import com.boslla.maps.containers.ReviewContainer;
import com.boslla.maps.containers.Unit;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

public class ReviewView extends MapsApplication implements Button.ClickListener{
	
	public ReviewView()
	{
		
	}
	
	public void getReviewsWindow(Unit unit)	{
		
		final Window subWindow = new Window(unit.getUnitName()+", "+unit.getMapDescription());
		subWindow.setSizeFull();
		subWindow.setModal(true);
	    subWindow.setWidth("35%");
	    subWindow.setHeight("70%");
	    subWindow.center();
	    subWindow.setScrollable(true);
	    subWindow.setResizable(true);

	    ReviewContainer reviewContainer = null;
		try {
			
			reviewContainer = new ReviewContainer();
			reviewContainer = ReviewContainer.getReviews(unit.getUnitId());
		
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    VerticalLayout reviewsLayout = new VerticalLayout();
	    reviewsLayout.setSizeFull();
	    reviewsLayout.setMargin(true);
	    
	    for (int i=0; i<reviewContainer.size();i++)
	    {
	    	HorizontalLayout reviewLayout = new HorizontalLayout();
	    	
	    	Review review = reviewContainer.getIdByIndex(i);	
	    	
	    	VerticalLayout contactInformation = new VerticalLayout();
	    	//contactInformation.setMargin(true);
	    	//contactInformation.setSizeFull();
	    	
	    	contactInformation.addComponent(new Label(review.getDate().toString()+" "+review.getTime().toString()));
	    	if (review.getName() != null) contactInformation.addComponent(new Label(review.getName(),Label.CONTENT_XHTML));
	    	if (review.getEmail() != null) contactInformation.addComponent(new Label(review.getEmail(),Label.CONTENT_XHTML));
	    	if (review.getPhone() != null) contactInformation.addComponent(new Label(review.getPhone(),Label.CONTENT_XHTML));
	    	
	    	Label descriptionLabel = new Label(review.getDescription().toString());
	    	
	    	reviewLayout.setSizeFull();
	    	reviewLayout.setMargin(true);
	    	reviewLayout.addComponent(contactInformation);
	    	reviewLayout.setComponentAlignment(contactInformation, Alignment.TOP_LEFT);
	    	reviewLayout.setExpandRatio(contactInformation, 1);
	    	reviewLayout.addComponent(descriptionLabel);
	    	reviewLayout.setComponentAlignment(descriptionLabel, Alignment.TOP_LEFT);
	    	reviewLayout.setExpandRatio(descriptionLabel, 2);
	    	
	    	reviewsLayout.addComponent(reviewLayout);	    	
	    }
	    
		subWindow.setContent(reviewsLayout);
		MapsApplication.layout.getWindow().addWindow(subWindow);

	}

public void addReviewWindow(final Unit unit)	{
		
		final Window subWindow = new Window("Add your review for "+unit.getUnitName()+", "+unit.getMapDescription());
		subWindow.setModal(true);
	    subWindow.setWidth("35%");
	    subWindow.setHeight("70%");
	    subWindow.center();
	    subWindow.setScrollable(false);
	    subWindow.setResizable(false);

	    final Form form = new Form();
	    
	    final FormLayout formLayout = new FormLayout();
	    formLayout.setSpacing(true);
	    final TextField name = new TextField("Name(Optional)");
	    name.setColumns(25);
	    name.setInputPrompt("Add your name");
	    final TextField email = new TextField("Email(Optional)");
	    email.setColumns(25);
	    email.setInputPrompt("Add your email");
	    final TextField phone = new TextField("Phone(Optional)");
	    phone.setColumns(25);
	    phone.setInputPrompt("Add your phone");

	    final TextField description = new TextField("Description");
	    description.setInputPrompt("Add your Review");
	    description.setColumns(25);
	    description.setRows(7);

	    
	    final OptionGroup rateOptionGroup = new OptionGroup("Rate this location");
	    rateOptionGroup.setNullSelectionAllowed(false);
	    rateOptionGroup.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ICON_ONLY);
    	rateOptionGroup.addItem(1);
		rateOptionGroup.setItemIcon(1, new ThemeResource("images/rate_1.png"));
    	rateOptionGroup.addItem(2);
    	rateOptionGroup.setItemIcon(2, new ThemeResource("images/rate_2.png"));
    	rateOptionGroup.addItem(3);
    	rateOptionGroup.setItemIcon(3, new ThemeResource("images/rate_3.png"));
    	rateOptionGroup.addItem(4);
    	rateOptionGroup.setItemIcon(4, new ThemeResource("images/rate_4.png"));
    	rateOptionGroup.addItem(5);
    	rateOptionGroup.setItemIcon(5, new ThemeResource("images/rate_5.png"));
	    
	    	    
	    Button submitReviewButton = new Button("Add Review");
	    submitReviewButton.addListener((ClickListener)this);
	    
	    Button resetReviewButton = new Button("reset");
	    resetReviewButton.addListener((ClickListener)this);
	    resetReviewButton.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				System.out.println("Discard");
			}	
		});
	    
	    submitReviewButton.addListener(new Button.ClickListener() {
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
				Review review = new Review();
    			
				System.out.println("Rate: "+rateOptionGroup.getValue().toString());
				
				review.setName(name.getValue().toString());
				review.setEmail(email.getValue().toString());
				review.setPhone(phone.getValue().toString());
				review.setDescription(description.getValue().toString());
				review.setRate(1);
				review.setUnitId(unit.getUnitId());
    			    			
    			if (ReviewContainer.insertReview(review))
    			{
    				 n = new Notification("Review added successfully","",Notification.TYPE_HUMANIZED_MESSAGE);
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

	    form.addField("email",email);
	    form.getField("email").setRequired(false);
	    
	    form.addField("phone",phone);
	    form.getField("phone").setRequired(false);
	    
	    form.addField("description",description);
	    form.getField("description").setRequired(true);
	    form.getField("description").setRequiredError("Please add your review");
	    
	    form.addField("rate", rateOptionGroup);
	    form.getField("rate").setRequired(true);
	    form.getField("rate").setRequiredError("Type is missing.");
	    
	    form.getFooter().addComponent(submitReviewButton);
	    form.getFooter().addComponent(resetReviewButton);
	    
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