package ru.dd4rky.notificationserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="notifications")
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private LocalDateTime created;
    @Enumerated(EnumType.ORDINAL)
    private NotificationStatus status;

    private String initiator;
    private String message;

    public enum NotificationStatus {
        UNDEFINED(0),
        CREATED (1),
        IN_PROCESS(2),
        DELIVERED(3);

        @Getter
        private final int code;
        NotificationStatus(int code) {
            this.code = code;
        }
    }
}
