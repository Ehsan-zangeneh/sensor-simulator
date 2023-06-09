package com.sensor.simulator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sensor.simulator.service.SimulatorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/simulator")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimulatorController {
	
	SimulatorService simulatorService;
	
	@GetMapping("/start")
	public void turnOn( ) {
		simulatorService.switchSensorsOn();
	}
	
	@GetMapping("/stop")
	public void turnOff( ) {
		simulatorService.switchSensorsOff();
	}	

}
