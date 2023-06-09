package com.sensor.simulator.service.model;

import java.security.SecureRandom;
import java.util.List;

import com.sensor.simulator.common.exception.PhysicalProblemException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EnvironmentEvents {
	
	TEMPERATURE(18.0, 28.0, "temperature"),
	OXYGEN(300000.0, 400000.0, "oxygen"),
	LIGHT(10000.0, 25000.0, "light");
	
	static SecureRandom random = new SecureRandom();
	double lowerBound;
	double upperBound;
	String event;
	
	/**
	 * This method creates a normal message. 
	 * */
	public static SensorMessage safeOccurrence() {
		return SensorMessage.builder()
				.temperature(TEMPERATURE.problemFreeNumberGeneration())
				.light(LIGHT.problemFreeNumberGeneration())
				.oxygen(OXYGEN.problemFreeNumberGeneration())
				.build();
	}
	
	/**
	 * This method creates a problematic message using the <i>brokenMetric</i>.
	 * @param brokenMetric contains the value for which the message is supposed to report
	 *        a problem.
	 * @exception PhysicalProblemException is thrown if the brokenMetric contains false data. 
	 * */
	public static SensorMessage unsafeOccurrence(List<String> brokenMetric) {
		if(isBrokenMetricInValid(brokenMetric)) {
			throw new PhysicalProblemException("A physical problem has likely occured in the monitoring environment");
		}
		return SensorMessage.builder()
				.temperature(safeOrNot(TEMPERATURE, brokenMetric))
				.light(safeOrNot(LIGHT, brokenMetric))
				.oxygen(safeOrNot(OXYGEN, brokenMetric))
				.build();
	} 
	
	private static double safeOrNot(EnvironmentEvents environmentEvents, List<String> brokenMetric) {
		return brokenMetric.contains(environmentEvents.event) ?
				environmentEvents.problematicNumberGeneration() : environmentEvents.problemFreeNumberGeneration();
	}
	
	private double problemFreeNumberGeneration() {
		return generateRandom(lowerBound, upperBound);
	}
	
	private double problematicNumberGeneration() {
		boolean problemInLowerRange = random.nextBoolean(); // If true means lower than standard otherwise higher.
		if(problemInLowerRange) {
			return generateRandom(lowerBound - getTenPercentOfNumber(lowerBound), lowerBound);
		}
		return generateRandom(upperBound, upperBound + getTenPercentOfNumber(upperBound));
	}
	
	private double generateRandom(double lowerBound, double upperBound) {
		return lowerBound + (upperBound - lowerBound) * random.nextDouble();
	}
	
	private double getTenPercentOfNumber(double number) {
		return (number / 100) * 10;
	}
	
	private static boolean isBrokenMetricInValid(List<String> brokenMetric) {
		return brokenMetric == null || 
				brokenMetric.isEmpty() ||	
				!containsAnyOfSensorMessageMetrics(brokenMetric)||
				brokenMetric.size() > 1 ; // might be removed later
	}
	
	private static boolean containsAnyOfSensorMessageMetrics(List<String> brokenMetric) {
		return List.of(EnvironmentEvents.values()).stream()
				.map(EnvironmentEvents::getEvent)
		        .anyMatch(brokenMetric::contains);		
	}
}
