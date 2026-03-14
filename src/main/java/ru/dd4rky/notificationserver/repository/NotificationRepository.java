package ru.dd4rky.notificationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dd4rky.notificationserver.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
