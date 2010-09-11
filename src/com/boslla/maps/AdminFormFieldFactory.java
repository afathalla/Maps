package com.boslla.maps;

import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

public class AdminFormFieldFactory implements com.vaadin.ui.FormFieldFactory {

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		String pid = (String) propertyId;
		if (pid.equals("key")) {
			return null;
		}	
		else if (pid.equals("mapName")) {
			TextField mapNameText = new TextField(pid);
			mapNameText.setDescription("Map Name");
			mapNameText.setMaxLength(20);
			StringLengthValidator mapNameValidator = new StringLengthValidator("Map Name" +
										"must be between 1 and 20 charachters");
			mapNameValidator.setMinLength(1);
			mapNameValidator.setMaxLength(20);
			mapNameText.addValidator(mapNameValidator);
			return mapNameText;
		}
		else if (pid.equals("mapDescription")) {
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
		
		else if (pid.equals("mapWidth") || pid.equals("mapHeight")) {
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
		
		else if (pid.equals("mapScale")) {
			TextField mapScaleText = new TextField(pid);
		    mapScaleText.setCaption("Map Scale");
			
			mapScaleText.setMaxLength(3);
			RegexpValidator mapScaleValidator = new RegexpValidator("[1-9][.][0-9]","Map Scale" +
										" must be a floating point integer between 1.0 and 9.9");
	
			mapScaleText.addValidator(mapScaleValidator);
			return mapScaleText;
		}
		return null;
	}
}
