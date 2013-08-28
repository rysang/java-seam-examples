package org.test.spring.remoting.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = { "/index", "/" }, method = { RequestMethod.GET })
	public String index(Map<String, Object> map) {

		map.put("message", "Hello World! ");
		return "index";
	}

}
