package ru.dd4rky.notificationserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dd4rky.notificationserver.entity.Notification;
import ru.dd4rky.notificationserver.exceptions.EmptyFieldException;
import ru.dd4rky.notificationserver.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional
    public Notification createAndSendNotification(Notification notification) {
        if (notification.getInitiator().isEmpty()) {
            throw new EmptyFieldException("initiator cannot be empty");
        }
        if (notification.getMessage().isEmpty()) {
            throw new EmptyFieldException("message cannot be empty");
        }
        notification.setCreated(LocalDateTime.now());
        notification.setStatus(Notification.NotificationStatus.UNDEFINED);
        Notification stagedEntity = notificationRepository.save(notification);

        stagedEntity.setStatus(Notification.NotificationStatus.CREATED);

        return notificationRepository.save(stagedEntity);
    }

    public Notification.NotificationStatus getNotificationStatusByUUID(UUID uuid) {
        Notification notification = notificationRepository.findNotificationByUuid(uuid);
        if (Objects.isNull(notification)) {
            return Notification.NotificationStatus.UNDEFINED;
        }
        return notification.getStatus();
    }
}
