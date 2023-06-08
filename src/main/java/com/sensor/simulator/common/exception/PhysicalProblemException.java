package com.sensor.simulator.common.exception;

/**
 * This exception indicates that there might be a physical problem with the sensor. 
 * */
public class PhysicalProblemException extends RuntimeException {
	
	public PhysicalProblemException(String message) {
		super(message);
	}
}
