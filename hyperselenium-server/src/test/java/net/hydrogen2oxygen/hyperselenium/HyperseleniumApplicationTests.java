package net.hydrogen2oxygen.hyperselenium;

import net.hydrogen2oxygen.hyperselenium.domain.Scenario;
import net.hydrogen2oxygen.hyperselenium.domain.Script;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

// FIXME rewrite the test in order to test our own ui
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class HyperseleniumApplicationTests {

	//@Autowired
	private HyperseleniumService service;

	//@Test
	public void loadAndExecuteScript() throws Exception {

		List<String> lines = FileUtils.readLines(new File("src/test/resources/google_search_test.md"),"UTF-8");
		Scenario scenario = new Scenario();
		Script script = new Script("hyperselenium test", lines);
		scenario.setScript(script);

		service.executeScenario(scenario);
	}

}
