package de.juli.loseweight.model;

import javax.persistence.Entity;

@Entity
public class FoodItem extends Model<FoodItem> {
	private static final long serialVersionUID = 1L;

	/**
	 * Bezeichnung
	 */
	private String name;
	/**
	 * Kalorien pro 100g
	 */
	private Integer kcal;
	private MeasuringUnit unit;

	public FoodItem() {
		super();
	}

	public FoodItem(String name, Integer kcal, MeasuringUnit unit) {
		this();
		this.name = name;
		this.kcal = kcal;
		this.unit = unit;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 * @return 
	 */
	public FoodItem setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the kcal
	 */
	public Integer getKcal() {
		return kcal;
	}

	/**
	 * @param kcal the kcal to set
	 * @return 
	 */
	public FoodItem setKcal(Integer kcal) {
		this.kcal = kcal;
		return this;
	}

	/**
	 * @return the unit
	 */
	public MeasuringUnit getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 * @return 
	 */
	public FoodItem setUnit(MeasuringUnit unit) {
		this.unit = unit;
		return this;
	}
}
