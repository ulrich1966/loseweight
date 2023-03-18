package de.juli.loseweight.model;

import java.util.List;

import javax.persistence.Entity;

//@Entity
public class ConsumptionItem extends Model<ConsumptionItem> {
	
	private List<FoodItem> items;
	private float quantity;
	private MeasuringUnit unit;

}
