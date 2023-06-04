package com.sensor.simulator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulator")
public class SimulatorController {
	
	@GetMapping("/start")
	public String turnOn( ) {
		return "Hello World!";
	}
	

}
