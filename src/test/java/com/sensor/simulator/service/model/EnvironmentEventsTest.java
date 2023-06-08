package com.sensor.simulator.service.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sensor.simulator.common.exception.PhysicalProblemException;

public class EnvironmentEventsTest {
	
	@RepeatedTest(10)
	public void safeOccurrenceTest() {
		var message = EnvironmentEvents.safeOccurrence();
		assertAll("message",
				() -> assertTrue(
						message.getTemperature() > 18.0 &&
						message.getTemperature() < 28.0
						),
				() -> assertTrue(
						message.getLight() > 1000.0 && 
						message.getLight() < 25000.0
						),
				() -> assertTrue(
						message.getOxygen() > 300000.0 && 
						message.getOxygen() < 400000.0
						)
				);
		
	}
	
	@ParameterizedTest
	@MethodSource("provideBrokenMetric")
	public void unsafeOccurrenceTest(List<String> metrics, boolean throwsException) {
		if (!throwsException) {
			var message = EnvironmentEvents.unsafeOccurrence(metrics);
			assertTrue(checkProblemticMessage(message));
		} else { 
			 assertThrows(PhysicalProblemException.class, () -> EnvironmentEvents.unsafeOccurrence(metrics)); 
	    }
			 
		
	}
	
	private static Stream<Arguments> provideBrokenMetric() {
		return Stream.of(
				Arguments.of(List.of(SensorMessage.Fields.light), false),
				Arguments.of(List.of(SensorMessage.Fields.oxygen), false),
				Arguments.of(List.of(SensorMessage.Fields.temperature), false),
				Arguments.of(List.of(SensorMessage.Fields.temperature, "anyOtherMetric"), true),
				Arguments.of(List.of(), true),
				Arguments.of(null, true),
				Arguments.of(List.of(SensorMessage.Fields.temperature, SensorMessage.Fields.light), true)
				);
	}
	
	/**
	 * Checks to see the produced messages have at least one problem.
	 * */
	private boolean checkProblemticMessage(SensorMessage message) {
		return 	message.getTemperature() < 18.0 ||
				message.getTemperature() > 28.0 ||
				message.getLight() < 1000.0 || 
				message.getLight() > 25000.0 ||
				message.getOxygen() < 300000.0 || 
				message.getOxygen() > 400000.0;
	
	}

}
