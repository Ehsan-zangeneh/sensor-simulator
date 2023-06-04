package com.sensor.simulator.service.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SensorMessage {
	double temprature;
	double oxygen;
	double light;
}
