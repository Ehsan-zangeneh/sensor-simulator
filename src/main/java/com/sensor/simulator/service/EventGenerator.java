package com.sensor.simulator.service;

import java.security.SecureRandom;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.sensor.simulator.service.model.EnvironmentEvents;
import com.sensor.simulator.service.model.SensorMessage;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Component
@ConfigurationProperties(prefix ="sensor.message")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
public class EventGenerator {
	
	static final SecureRandom secureRandom = new SecureRandom();
	List<String> metrics;

	int environmentalRiskAmount;

	public SensorMessage generate() {
		Condition condition = getCondition();
		SensorMessage sensorMessage; 
		switch (condition) {
		case DANGEROUS ->  sensorMessage = EnvironmentEvents.unsafeOccurrence(List.of(getRandomMetric()));
		case NORMAL -> sensorMessage = EnvironmentEvents.safeOccurrence();
		default -> sensorMessage = SensorMessage.builder().build();
		}
		return sensorMessage;
	}
	
	private String getRandomMetric( ) {
		int percent = secureRandom.nextInt(6);
		return metrics.get(percent % 3);
	}
	/**
	 * About 30 percent of times the condition is risky. 
	 * */
	private Condition getCondition() {
		int percent = secureRandom.nextInt(100);
		return percent > environmentalRiskAmount ? Condition.NORMAL : Condition.DANGEROUS;
	}
	
	private enum Condition {
		DANGEROUS,
		NORMAL;
	} 
	
	
}
