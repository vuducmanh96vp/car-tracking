# Car Tracking

This demo showcases a simple Car Tracking system using Spring Boot, Kafka, and Google Maps. It allows you to track the real-time location of cars on a Google Map.

## Prerequisites

Before you begin, make sure you have the following installed:

- Java Development Kit (JDK) 8 or later
- Apache Maven
- Apache Kafka
- Google Maps API Key

## Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/vuducmanh96vp/car-tracking.git
    ```

2. Navigate to the project directory:

    ```bash
    cd car-tracking
    ```

3. Open the `src/main/resources/application.properties` file and configure your Kafka broker:

    ```properties
    spring.kafka.bootstrap-servers=<your-kafka-broker>
    ```

4. Start Kafka (if not already running) and create a topic named `car-tracking-topic`:

    ```bash
    kafka-server-start.sh config/server.properties
    kafka-topics.sh --create --topic car-tracking-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
    ```

## Run the Application

1. Build the project:

    ```bash
    mvn clean install
    ```

2. Run the Spring Boot application:

    ```bash
    java -jar target/car-tracking-0.0.1-SNAPSHOT.jar
    ```

3. Access the application at [http://localhost:8080](http://localhost:8080) in your web browser.

## Simulate Car Location Updates

To simulate car location updates, you can use a Kafka producer. Run the following command to send a sample location update:

```bash
kafka-console-producer.sh --broker-list localhost:9092 --topic car-tracking-topic
>{"carId":"car123","latitude":37.7749,"longitude":-122.4194}
```

## Demo
[Car Tracking system using Spring Boot, Kafka, and Google Maps](https://youtu.be/cI1H8Kig-jk)
![Init screen](/img/CarTracking1.png)
![Car Tracking location 1](/img/CarTracking2.png)
![Car Tracking  location 2](/img/CarTracking3.png)
