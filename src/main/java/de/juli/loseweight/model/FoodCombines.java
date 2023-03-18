package de.juli.loseweight.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

//@Entity
public class FoodCombines extends Model<FoodCombines> {
	private static final long serialVersionUID = 1L;
	
	private String name;
	@OneToMany
	private List<FoodItem> foodItems;

	public FoodCombines() {
		super();
	}

	public FoodCombines(String name, List<FoodItem> foodItems) {
		this();
		this.name = name;
		this.foodItems = foodItems;
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
	 * @return the foodItems
	 */
	public List<FoodItem> getFoodItems() {
		if (null == foodItems) {
			this.foodItems = new ArrayList<>();
		}
		return this.foodItems;
	}
	
	/**
	 * @param foodItems the foodItems to set
	 */
	public void setFoodItems(List<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}

	public void addFoodItem(FoodItem foodItem) {
		getFoodItems().add(foodItem);
	}

	public void removeFoodItem(FoodItem foodItem) {
		if (getFoodItems().contains(foodItem)) {
			getFoodItems().remove(foodItem);
		}
	}
}
