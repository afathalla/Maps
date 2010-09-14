package com.boslla.maps.containers;

import java.sql.Time;
import java.util.Date;

public class Review{
	
	private Date date;
	private Time time;
	private String name;
	private String email;
	private String phone;
	private int unitId;
	private String description;
	private int rate;
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public void setTime(Time time)
	{
		this.time = time;
	}
	
	public Time getTime()
	{
		return time;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public void setUnitId(int unitId)
	{
		this.unitId = unitId;
	}
	
	public int getUintId()
	{
		return unitId;
	}
	
	public void setRate(int rate)
	{
		this.rate = rate;
	}
	
	public int getRate()
	{
		return rate;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getDescription()
	{
		return description;
	}

}
