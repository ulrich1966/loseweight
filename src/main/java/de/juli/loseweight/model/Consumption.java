package de.juli.loseweight.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

//@Entity
public class Consumption extends Model<Consumption> {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Date date;
	private Date time;
	private List<ConsumptionItem> items;
	
	public Consumption() {
		super();
	}
	
	public Consumption(String name, Date date, Date time, List<ConsumptionItem> items) {
		this();
		this.name = name;
		this.date = date;
		this.time = time;
		this.items = items;
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * @return the items
	 */
	public List<ConsumptionItem> getItems() {
		if(null == this.items) {
			this.items = new ArrayList<>();
		}
		return this.items;
	}
	
	/**
	 * @param items the items to set
	 */
	public void setItems(List<ConsumptionItem> items) {
		this.items = items;
	}

	public void addItem(ConsumptionItem item) {
		getItems().add(item);
	}

	public void removeItem(ConsumptionItem item) {
		if(getItems().contains(item)) {
			getItems().remove(item);			
		}
	}
}
