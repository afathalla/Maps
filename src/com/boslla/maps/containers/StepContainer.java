package com.boslla.maps.containers;

import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class StepContainer extends BeanItemContainer<Step> implements Serializable
{
	public StepContainer() throws InstantiationException, IllegalAccessException 
	{
		super(Step.class);	
	}
	
	public static StepContainer getStepContainer(int stepsCounter, String[] directionArray)
	{
		StepContainer stepContainer= null;
		try
		{
			stepContainer = new StepContainer();
			
			System.out.println("Steps Counter: "+stepsCounter);
			for (int i=1; i < stepsCounter; i++)
			{
				Step step = new Step();
				step.setStep(i);
				step.setDirection(directionArray[i].toString());
				stepContainer.addBean(step);
			}
	    } catch (InstantiationException e) {
		    e.printStackTrace(); 
	       } catch (IllegalAccessException e) {
		       e.printStackTrace();    
	         }
	       
	    return stepContainer;
	}
}
