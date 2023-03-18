package de.juli.loseweight.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import de.juli.loseweight.exeption.ModelCreateException;
import de.juli.loseweight.model.FoodItem;
import de.juli.loseweight.service.JsonMarshallService;


@Component
public class DataSetUpHelper {
	public static final String FOOD_ITEMS = "/json-data/food-items.json";
	
	@Autowired
	private JsonMarshallService<FoodItem> marshaller;
	@Autowired
	private ResourceLoader loader;

	public Path writeStringDataToJsonFile(String dataString, String targetPath) throws ModelCreateException, IOException {
		Path path = Paths.get(loader.getResource(targetPath).getURI());
		File target = marshaller.marshall(new JSONArray(dataString), path);
		return Paths.get(target.toURI()); 
	}

}
