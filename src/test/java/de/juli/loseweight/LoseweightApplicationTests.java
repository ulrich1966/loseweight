package de.juli.loseweight;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

@SpringBootTest
class LoseweightApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(LoseweightApplicationTests.class);

	@Autowired
	ResourceLoader loader;
	
	@Test
	void contextLoads() throws IOException {
		LOG.debug("{}", "moin");
		Path path = Paths.get(loader.getResource("/").getURI());
		LOG.debug("{}", path);
	}

}
