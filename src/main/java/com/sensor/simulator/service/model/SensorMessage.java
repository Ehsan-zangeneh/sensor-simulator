package com.sensor.simulator.service.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SensorMessage {
	double temprature;
	double oxygen;
	double light;
}
