package pl.kipperthrower.astaroth.core.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
	
	private static final Logger LOGGER = Logger.getLogger(HomeController.class);
	
	@RequestMapping(value={"/", "/index.html"})
	public String index() {

		LOGGER.info("/index.htm ");
		return "home/index";
	}
	@RequestMapping("/protected.html")
	public String protectedPage() {
		LOGGER.info("/protected.htm ");
		return "home/protected";
	}
}
