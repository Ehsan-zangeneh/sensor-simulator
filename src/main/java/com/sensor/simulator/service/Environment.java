package com.sensor.simulator.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.sensor.simulator.common.exception.PhysicalProblem;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Environment {
	
	double[] tempratureSafeRange = {18.0, 28.0};
	double[] oxygenSafeRange = {300000.0, 400000.0};
	double[] lightSafeRange = {1000.0, 25000.0};
	SecureRandom r = new SecureRandom();
	
	private double problemFreeNumberGeneration(double[] range) {
        if(range == null && range.length != 2 ) {
        	throw new PhysicalProblem("A physical problem has likely occured in the monitoring environment");
        }
		double lowerBound = range[0];
		double upperBound = range[1];
		return generateRandom(lowerBound, upperBound);
	}
	
	private double problematicNumberGeneration(double[] range) {
        if(range == null && range.length != 2 ) {
        	throw new PhysicalProblem("A physical problem has likely occured in the monitoring environment");
        }
		double lowerBound = range[0];
		double upperBound = range[1];
		boolean problemInLowerRange = r.nextBoolean(); // If true means lower than standard otherwise higher.
		if(problemInLowerRange) {
			return generateRandom(lowerBound - getTenPercentOfNumber(lowerBound), lowerBound);
		}
		return generateRandom(upperBound, upperBound + getTenPercentOfNumber(upperBound));
	}
	
	private double generateRandom(double lowerBound, double upperBound) {
		return lowerBound + (upperBound - lowerBound) * r.nextDouble();
	}
	
	private double getTenPercentOfNumber(double number) {
		return (number / 100) * 10;
	}
}
