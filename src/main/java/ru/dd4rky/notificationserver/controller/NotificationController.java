package ru.dd4rky.notificationserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dd4rky.notificationserver.entity.Notification;
import ru.dd4rky.notificationserver.service.NotificationService;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor()
@Controller
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody Notification notification) {
        Notification created = notificationService.createAndSendNotification(notification);

        URI uri = ServletUriComponentsBuilder.fromCurrentServletMapping()
            .path("/notification/get_status")
            .queryParam("uuid", created.getUuid())
            .build()
            .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/get_status")
    public ResponseEntity<Notification.NotificationStatus> getNotificationStatus(@RequestParam("uuid") UUID uuid) {
        Notification.NotificationStatus status = notificationService.getNotificationStatusByUUID(uuid);

        return ResponseEntity.ok(status);
    }
}
