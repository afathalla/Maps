package com.boslla.maps.admin;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.boslla.maps.containers.PlaceContainer;
import com.google.appengine.api.datastore.Key;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

public class AdminFormFieldFactory implements com.vaadin.ui.FormFieldFactory {

	private String className = null;
	public AdminFormFieldFactory(String className) {
		super();
		this.className = className;
	}
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		
		if (className == "Map") {
		String pid = (String) propertyId;
		if (pid.equals("key")) {
			return null;
		}	
		else if (pid.equals("mapName")) {
			TextField mapNameText = new TextField(pid);
			mapNameText.setCaption("Map Name");
			mapNameText.setMaxLength(20);
			StringLengthValidator mapNameValidator = new StringLengthValidator("Map Name" +
										"must be between 1 and 20 charachters");
			mapNameValidator.setMinLength(1);
			mapNameValidator.setMaxLength(20);
			mapNameText.addValidator(mapNameValidator);
			return mapNameText;
		}
		if (pid.equals("mapDescription")) {
			TextField mapDescriptionText = new TextField(pid);
			mapDescriptionText.setCaption("Map Description");
			mapDescriptionText.setMaxLength(200);
			StringLengthValidator mapDescritpionValidator = new StringLengthValidator("Map Name" +
										" must be between 10 and 200 charachters");
			mapDescritpionValidator.setMinLength(10);
			mapDescritpionValidator.setMaxLength(200);
			mapDescriptionText.addValidator(mapDescritpionValidator);
			return mapDescriptionText;
		}
		
		if (pid.equals("mapWidth") || pid.equals("mapHeight")) {
			TextField mapDimsText = new TextField(pid);
			if (pid.equals("mapWidth")) {
				mapDimsText.setCaption("Map Width");
			}
			else {
				mapDimsText.setCaption("Map Height");
			}
			mapDimsText.setMaxLength(5);
			RegexpValidator mapDimsValidator = new RegexpValidator("[1-9][0-9][0-9][0-9][0-9]","Map Width and Height" +
										" must be an integer between 11111 and 99999");
	
			mapDimsText.addValidator(mapDimsValidator);
			return mapDimsText;
		}
		
		if (pid.equals("mapScale")) {
			TextField mapScaleText = new TextField(pid);
		    mapScaleText.setCaption("Map Scale");
			
			mapScaleText.setMaxLength(3);
			RegexpValidator mapScaleValidator = new RegexpValidator("[1-9][.][0-9]","Map Scale" +
										" must be a floating point integer between 1.0 and 9.9");
	
			mapScaleText.addValidator(mapScaleValidator);
			return mapScaleText;
		}
		if (pid.equals("imageUrl")) {
			TextField imageUrlText = new TextField(pid);
		    imageUrlText.setCaption("Image Url");
			
			imageUrlText.setMaxLength(100);
			return imageUrlText;
		}
		if (pid.equals("place")) {
			PlaceContainer existingPlaces = PlaceContainer.getAllPlaces();
			Select mapPlaceSelect = new Select("Map Place", existingPlaces);
			mapPlaceSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
			mapPlaceSelect.setItemCaptionPropertyId("placeName");
			return mapPlaceSelect;
		}
		
		
	}
	
		 if (className == "Place") {
//			@PrimaryKey
//			@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//			private Key key;
//			@Persistent
//			private String placeName;
//			@Persistent
//			private String placeDescription;
//			@Persistent
//			private String placeLocation;
//			@Persistent
//			private String placeType;
			String pid = (String) propertyId;
			if (pid.equals("key")) {
				return null;
			}
			if (pid.equals("placeName")) {
				TextField placeNameText = new TextField(pid);
				placeNameText.setDescription("Place Name");
				placeNameText.setMaxLength(20);
				StringLengthValidator placeNameValidator = new StringLengthValidator("Place Name" +
											"must be between 1 and 20 charachters");
				placeNameValidator.setMinLength(1);
				placeNameValidator.setMaxLength(20);
				placeNameText.addValidator(placeNameValidator);
				return placeNameText;
			}
			if (pid.equals("placeDescription")) {
				TextField placeDescriptionText = new TextField(pid);
				placeDescriptionText.setCaption("Place Description");
				placeDescriptionText.setMaxLength(200);
				StringLengthValidator placeDescritpionValidator = new StringLengthValidator("Map Name" +
											" must be between 10 and 200 charachters");
				placeDescritpionValidator.setMinLength(10);
				placeDescritpionValidator.setMaxLength(200);
				placeDescriptionText.addValidator(placeDescritpionValidator);
				return placeDescriptionText;
			}
			if (pid.equals("placeLocation")) {
				TextField locationDescriptionText = new TextField(pid);
				locationDescriptionText.setCaption("Location Description");
				locationDescriptionText.setMaxLength(200);
				StringLengthValidator locationDescritpionValidator = new StringLengthValidator("Location Name" +
											" must be between 10 and 200 charachters");
				locationDescritpionValidator.setMinLength(10);
				locationDescritpionValidator.setMaxLength(200);
				locationDescriptionText.addValidator(locationDescritpionValidator);
				return locationDescriptionText;
			}
			if (pid.equals("placeLongitude")) {
				TextField longitudeText = new TextField(pid);
				longitudeText.setCaption("Place Longitude");
				longitudeText.setMaxLength(10);
				DoubleValidator longitudeValidator = new DoubleValidator("Place Longitude" +
											" must be a Double Value");
			
				longitudeText.addValidator(longitudeValidator);
				return longitudeText;
			}
			if (pid.equals("placeLatitude")) {
				TextField latitudeText = new TextField(pid);
				latitudeText.setCaption("Place Latitude");
				latitudeText.setMaxLength(10);
				DoubleValidator latitudeValidator = new DoubleValidator("Place Longitude" +
											" must be a Double Value");
			
				latitudeText.addValidator(latitudeValidator);
				return latitudeText;
			}
			if (pid.equals("placeType")) {
				return null;
			}
		}
		return null;
 }
}