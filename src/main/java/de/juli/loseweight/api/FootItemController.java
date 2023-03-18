package de.juli.loseweight.api;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.juli.loseweight.model.FoodItem;
import de.juli.loseweight.model.MeasuringUnit;
import de.juli.loseweight.model.Model;
import de.juli.loseweight.repository.FoodItemDataRepository;

@Controller
@RequestMapping("/api")
public class FootItemController extends RequestController implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(FootItemController.class);
	
	@Autowired
	private FoodItemDataRepository repo;
	
	@GetMapping(value = "/footItems", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<FoodItem>> footItems(HttpServletResponse response) {
		List<FoodItem> foodItems = repo.findAll();
		return new ResponseEntity<>(foodItems, HttpStatus.OK);
	}

	@PostMapping(value = "/footItem/{name}/caloricValue/{value}/unit/{unit}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<FoodItem> createfootItem(
			HttpServletResponse response, 
			@PathVariable("name") String name, 
			@PathVariable("value") Integer value, 
			@PathVariable("unit") MeasuringUnit unit) {

		FoodItem foodItem = new FoodItem(name, value, unit);
		repo.save(foodItem);
		
		return new ResponseEntity<>(foodItem, HttpStatus.OK);
	}

	@GetMapping(value = "/footItem/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getfootItem(HttpServletResponse response, @PathVariable("name") String name) {
		return null;
	}

	@DeleteMapping(value = "/footItem/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> delfootItem(HttpServletResponse response, @PathVariable("name") String name) {
		return null;
	}

}
