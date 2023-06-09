package com.sensor.simulator.service;

import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Component;
import com.sensor.simulator.message.publisher.SensorMessagePublisher;
import com.sensor.simulator.message.publisher.model.SensorPulishableMessage;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SimulatorService {
	
	EventGenerator eventGenerator;
	SensorMessagePublisher sensorMessagePublisher;
	
	static AtomicBoolean SENSORS_SWITCHED_ON = new AtomicBoolean(false);
	
	public void switchSensorsOn() {
		SENSORS_SWITCHED_ON.set(true);
		var sensorMessageGeneratorThread = new Thread(() -> runGenerator());
		sensorMessageGeneratorThread.start();		
	}
	
	public void switchSensorsOff() {
		SENSORS_SWITCHED_ON.set(false);		
	}
	
	@SneakyThrows
	private void runGenerator() {
		while(SENSORS_SWITCHED_ON.get()) {
			var sm = eventGenerator.generate();
			System.err.println("produced");
			sensorMessagePublisher.publish(SensorPulishableMessage.builder()
					.light(sm.getLight())
					.temperature(sm.getTemperature())
					.oxygen(sm.getOxygen())
					.build()
					);
			Thread.sleep(1000);
		}		
	}

}
