package com.example.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "notification",
        url = "http://localhost:8086/notifications"
)
public interface NotificationClient {

    @PostMapping
    void sendRestaurantNotification(@RequestBody NotificationRequest notificationRequest);
}
