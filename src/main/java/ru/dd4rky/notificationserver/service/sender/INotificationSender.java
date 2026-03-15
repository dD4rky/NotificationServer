package ru.dd4rky.notificationserver.service.sender;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.dd4rky.notificationserver.entity.Notification;

import java.util.concurrent.CompletableFuture;

public interface INotificationSender {
    CompletableFuture<Message> invokeSendingMessage(Notification notification);
}
