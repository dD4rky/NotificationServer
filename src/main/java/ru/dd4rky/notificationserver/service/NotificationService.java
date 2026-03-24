package ru.dd4rky.notificationserver.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.dd4rky.notificationserver.entity.Notification;
import ru.dd4rky.notificationserver.exceptions.EmptyFieldException;
import ru.dd4rky.notificationserver.repository.NotificationRepository;
import ru.dd4rky.notificationserver.service.sender.INotificationSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final INotificationSender notificationSender;

    @Transactional
    public Notification createAndSendNotification(Notification notification) {
        if (notification.getInitiator().isEmpty()) {
            throw new EmptyFieldException("initiator cannot be empty");
        }
        if (notification.getMessage().isEmpty()) {
            throw new EmptyFieldException("message cannot be empty");
        }
        notification.setCreated(LocalDateTime.now());
        Notification stagedEntity = updateNotificationStatus(notification, Notification.NotificationStatus.UNDEFINED);
        Notification createdEntity = updateNotificationStatus(stagedEntity, Notification.NotificationStatus.CREATED);

        sendNotification(createdEntity);

        log.info("Notification created: {}:{}", createdEntity.getUuid(), createdEntity.getStatus());
        return createdEntity;
    }

    private void sendNotification(Notification entity) {
        notificationSender.invokeSendingMessage(
                updateNotificationStatus(entity, Notification.NotificationStatus.IN_PROCESS)
            )
            .exceptionally((exception) -> {
                throw new RuntimeException(exception);
            })
            .thenAccept((message) -> updateNotificationStatus(entity, Notification.NotificationStatus.DELIVERED));
    }
    
    public Notification.NotificationStatus getNotificationStatusByUUID(UUID uuid) {
        Notification notification = notificationRepository.findNotificationByUuid(uuid);
        if (Objects.isNull(notification)) {
            return Notification.NotificationStatus.UNDEFINED;
        }
        return notification.getStatus();
    }

    private Notification updateNotificationStatus(@NotNull Notification entity, Notification.NotificationStatus status) {
        entity.setStatus(status);
        Notification updatedEntity = notificationRepository.save(entity);
        log.info("Notification status updated: {}:{}", updatedEntity.getUuid(), status);

        return updatedEntity;
    }
    @PostConstruct
    private void sendWaitingNotifications() {
        List<Notification> unsentNotifications = notificationRepository.findUnsentNotifications();

        unsentNotifications.forEach(this::sendNotification);
    }
}
