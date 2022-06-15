package com.example.testingTheWeb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

	@RequestMapping("/")
	public @ResponseBody String greeting() {
		return "Hello, World";
	}

	@GetMapping("/add")
	public Object add(
			@RequestParam(value = "a", defaultValue = "0") Float a,
			@RequestParam(value = "b", defaultValue = "0") Float b

	) {
		Float c = a+b;
		return c;
	}

}