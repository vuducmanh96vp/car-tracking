package com.nastech.cartracking.consumer;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nastech.cartracking.controller.CarLocationUpdate;

/**
 * <H3>
 * CarLocationConsumer
 * </H3>
 *
 * @author manhvud
 * @since 2023/11/16
 */
@Component
public class CarLocationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CarLocationConsumer.class);

    private final ObjectMapper          objectMapper;
    @Autowired
    private       SimpMessagingTemplate messagingTemplate;

    private final Map<String, CarLocationUpdate> allLocations         = new HashMap<>();
    private final Map<String, Instant>           lastUpdateTimestamps = new HashMap<>();

    public CarLocationConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "car-tracking-topic", groupId = "car-tracking-group")
    public void consume(String message) {
        logger.info("Received car location: {}", message);

        try {
            CarLocationUpdate locationUpdate = objectMapper.readValue(message, CarLocationUpdate.class);

            lastUpdateTimestamps.put(locationUpdate.getCarId(), Instant.now());
            // Save the location to the list
            allLocations.put(locationUpdate.getCarId(), locationUpdate);
            // Send the location update to WebSocket clients
            messagingTemplate.convertAndSend("/topic/car-location", locationUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, CarLocationUpdate> getAllLocations() {
        return new HashMap<>(allLocations);
    }

    // Add a method to get online status
    public Map<String, Boolean> getOnlineStatus() {
        Map<String, Boolean> onlineStatus     = new HashMap<>();
        Instant              now              = Instant.now();
        Duration             offlineThreshold = Duration.ofMinutes(1);

        for (Map.Entry<String, Instant> entry : lastUpdateTimestamps.entrySet()) {
            boolean isOnline = Duration.between(entry.getValue(), now).compareTo(offlineThreshold) < 0;
            onlineStatus.put(entry.getKey(), isOnline);
            if (!isOnline) {
                allLocations.remove(entry.getKey());
            }
        }

        return onlineStatus;
    }
}
