package ru.dd4rky.notificationserver.entity.DTO;

import lombok.Builder;
import lombok.Data;
import ru.dd4rky.notificationserver.entity.Notification;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
@Builder
@JsonSerialize
public class NotificationStatusDTO {
    @Builder.Default
    private int responseStatus = 200;
    private Notification.NotificationStatus notificationStatus;
}
