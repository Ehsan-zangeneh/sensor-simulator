# sensor-simulator
This Project is going to be just a simple sample work ;)
The project is written in Java 17.0.4.1

This is a sensor simulator project which also uses the sensor-message-publisher(inside the lib repository)
1) First deploy it in maven repository: mvn clean install
2) This project needs a kafka topic with (at least) two partitions:
	kafka-topics.sh/bat --create --topic sensor-message-topic --bootstrap-server localhost:9092 --partitions 3
	(topic name and bootstrap server are configurable in application.yaml file.)
3) Run this project (spring boot based)
4) The APIs are at disposal via "http://localhost:8082/sensor-simulator/swagger-ui.html"

When you run "start" it runs a thread which produces a message every 1 second.
Messages are divided into two categories: NORMAL and PROBLEMATIC. Each goes to its dedicated kafka partition.

