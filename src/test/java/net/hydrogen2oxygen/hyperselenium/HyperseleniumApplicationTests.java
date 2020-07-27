package net.hydrogen2oxygen.hyperselenium;

import net.hydrogen2oxygen.hyperselenium.domain.CommandResult;
import net.hydrogen2oxygen.hyperselenium.selenium.HyperWebDriver;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HyperseleniumApplicationTests {

	@Value("${selenium.driver.directory}")
	private String seleniumDriverDirectory;

	@Autowired
	private HyperseleniumService service;

	@Test
	public void loadAndExecuteScript() throws Exception {

		List<String> lines = FileUtils.readLines(new File("src/test/resources/google_search_test.md"),"UTF-8");

		service.executeScript(lines);
	}

}
