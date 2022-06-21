package com.example.testingTheWeb;

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

	){
		return a+b;
	}

	@GetMapping("/less")
	public Object less(
			@RequestParam(value = "a", defaultValue = "0") Float a,
			@RequestParam(value = "b", defaultValue = "0") Float b
	){
		return a-b;
	}
	@GetMapping("/multi")
	public Object multi(
			@RequestParam(value = "a", defaultValue = "0") Float a,
			@RequestParam(value = "b", defaultValue = "0") Float b
	){
		return a*b;
	}
	@GetMapping("/div")
	public Object div(
			@RequestParam(value = "a", defaultValue = "0") Float a,
			@RequestParam(value = "b", defaultValue = "0") Float b
	){
		return a/b;
	}
}