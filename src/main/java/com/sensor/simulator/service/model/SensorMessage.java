package com.sensor.simulator.service.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Builder
@Value
@FieldNameConstants
public class SensorMessage {
	double temperature;
	double oxygen;
	double light;
}
