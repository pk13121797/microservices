package by.pavvel.notification.controller;

import by.pavvel.notification.model.NotificationRequest;
import by.pavvel.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public void sendNotification(@RequestBody NotificationRequest notificationRequest){
        log.info("new notification request for customer {}",notificationRequest.getCustomerId());
        notificationService.sendNotification(notificationRequest);
    }
}
