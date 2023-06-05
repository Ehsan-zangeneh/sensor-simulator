package com.sensor.simulator.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.sensor.simulator.common.exception.PhysicalProblemException;
import com.sensor.simulator.service.model.SensorMessage;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventGenerator {

	SecureRandom r = new SecureRandom();
	

}
