package ru.dd4rky.notificationserver.service.sender;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.dd4rky.notificationserver.entity.Notification;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class TelegramNotificationSender implements INotificationSender {
    private TelegramClient telegramClient;

    @Value("${app.telegram.token}")
    private String token;
    @Value("${app.telegram.account_id}")
    private String accountId;

    @Override
    @Async
    public CompletableFuture<Message> invokeSendingMessage(Notification notification) {
        log.info("Sending message for notification: {}", notification.getUuid());
        try {
            return telegramClient.executeAsync(SendMessage.builder()
                .chatId(accountId)
                .text(notification.prepareMessageToSending())
                .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void init() {
        this.telegramClient = new OkHttpTelegramClient(token);
        log.info("Telegram client initialized");
    }
}
