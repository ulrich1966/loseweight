package de.juli.loseweight.resolver;

import de.juli.loseweight.model.FoodItem;
import de.juli.loseweight.model.MeasuringUnit;

public class ParameterResolver {
	public static FoodItem[] FOODI_ITEMS = {
			new FoodItem().setName("Banane").setKcal(90).setUnit(MeasuringUnit.GRAMM),
			new FoodItem().setName("Apfel").setKcal(52).setUnit(MeasuringUnit.GRAMM),
			new FoodItem().setName("Speisequark").setKcal(70).setUnit(MeasuringUnit.GRAMM),
			new FoodItem().setName("Milch fettarm").setKcal(48).setUnit(MeasuringUnit.MILLI_LITER),
			new FoodItem().setName("Tee").setKcal(0).setUnit(MeasuringUnit.MILLI_LITER),
			new FoodItem().setName("Kaffee").setKcal(0).setUnit(MeasuringUnit.MILLI_LITER)
	};	
}