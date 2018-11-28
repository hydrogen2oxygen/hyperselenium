package net.hydrogen2oxygen.hyperselenium;

import net.hydrogen2oxygen.hyperselenium.domain.WebSite;
import net.hydrogen2oxygen.hyperselenium.services.HyperseleniumService;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HyperseleniumApplicationTests {

	@Autowired
	private HyperseleniumService service;

	@Test
	public void loadAndExecuteScript() throws Exception {

		List<String> lines = FileUtils.readLines(new File("src/test/resources/google_search_test.txt"),"UTF-8");

		WebSite webSite = null;

		for (String line : lines) {

			System.out.println(line);
			String [] parts = line.split(" ");

			if ("open".equals(parts[0])) {
				webSite = new WebSite();
				webSite.setUrl(parts[1]);

				try {
					service.openWebsite(webSite);
				} catch (Exception e) {
					e.printStackTrace();
				}

				continue;
			}

			if (webSite == null) continue;

			service.executeCommandLine(webSite.getWebDriver(),line);
		}
	}

}
