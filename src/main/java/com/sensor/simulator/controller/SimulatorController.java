package com.sensor.simulator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sensor.simulator.service.EventGenerator;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/simulator")
public class SimulatorController {
	
	private final EventGenerator eventGenerator;
	
	@GetMapping("/start")
	public String turnOn( ) {
		return "Hello World!";
	}
	

}
