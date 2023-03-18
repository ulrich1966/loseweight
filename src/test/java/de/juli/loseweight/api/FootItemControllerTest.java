package de.juli.loseweight.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.juli.loseweight.TestResultLoggerExtension;
import de.juli.loseweight.model.FoodItem;
import de.juli.loseweight.resolver.ParameterResolver;
import de.juli.loseweight.util.DataSetUpHelper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(TestResultLoggerExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class FootItemControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(FootItemControllerTest.class);
	private static final String URL_CREATE = "/api/footItem/%s/caloricValue/%s/unit/%s";
	private static final String URL_ALL = "/api/footItems";

	private MockMvc mockMvc;
	private String currentUrl;
	
	@Autowired
	private DataSetUpHelper dataSetUp;
	
	@BeforeEach
	void setup(WebApplicationContext wac) throws IOException, JAXBException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		assertNotNull(this.mockMvc, "no mockMvc");
		assertNotNull(dataSetUp, "no dataSetUp");
	}

	@Test
	@Order(2)  
	void testFootItems() throws Exception {
		currentUrl = URL_ALL;
		LOG.debug("\n\tcurrentUrl = {}", currentUrl);
		mockMvc.perform(get(currentUrl)).andExpect(status().isOk()).andDo(print()).andDo(new ResultHandler() {
			@Override
			public void handle(MvcResult result) throws Exception {
				Path path = dataSetUp.writeStringDataToJsonFile(result.getResponse().getContentAsString(), DataSetUpHelper.FOOD_ITEMS);
				assertNotNull(path, "no path");
				LOG.debug("witten in file {}", path);
			}
		});
		LOG.info("{}", "DONE");
	}

	@Tag("IntegrationTest")
	@Test
	@Order(1)  
	void testCreatefootItem() throws Exception {
		FoodItem temp = null;
		for (int i = 0; i < ParameterResolver.FOODI_ITEMS.length; i++) {
			temp = ParameterResolver.FOODI_ITEMS[i];
			currentUrl = String.format(URL_CREATE, temp.getName(), temp.getKcal(), temp.getUnit());
			LOG.debug("\n\tcurrentUrl = {}", currentUrl);
			mockMvc.perform(post(currentUrl)).andExpect(status().isOk()).andDo(print()).andDo(new ResultHandler() {
				@Override
				public void handle(MvcResult result) throws Exception {
					LOG.debug("{}", result.getResponse().getContentAsString());
				}
			});
		}
		LOG.info("{}", "DONE");
	}

	@Disabled
	@Test
	void testGetfootItem() {
		assertTrue(true);
		LOG.info("{}", "DONE");
	}

	@Test
	void testDelfootItem() {
		assertTrue(true);
		LOG.info("{}", "DONE");
	}

}
