package com.boslla.maps.containers;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

public class Step {
	
	public void setStep(int step)
	{
		this.step=step;
	}
	
	public void setDirection(String direction)
	{
		this.direction=direction;
	}
	
	public int getStep()
	{
		return step;
	}
	
	public String getDirection()
	{
		return direction;
	}
	
	public Key getKey() {
		return key;
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	private int step;
	@Persistent
	private String direction;
	
}