package by.pavvel.notification.service;

import by.pavvel.notification.model.Notification;
import by.pavvel.notification.model.NotificationRequest;
import by.pavvel.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(NotificationRequest notificationRequest) {
        Notification notification = new Notification(
                notificationRequest.getCustomerId(),
                notificationRequest.getCustomerEmail(),
                notificationRequest.getMessage(),
                LocalDateTime.now()
        );
        notificationRepository.save(notification);
    }
}
