package de.juli.loseweight.model;

public enum MeasuringUnit {
	GRAMM("g"),
	KILO("kg"),
	LITER("l"),
	MILLI_LITER("ml");

	private String value;

	MeasuringUnit(String value) {
		this.value = value;
	}


	/**
	 * @return the unit
	 */
	public String getValue() {
		return value;
	}
}
