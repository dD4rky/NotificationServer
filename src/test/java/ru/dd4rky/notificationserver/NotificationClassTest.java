package ru.dd4rky.notificationserver;

import org.junit.jupiter.api.Test;
import ru.dd4rky.notificationserver.entity.Notification;


public class NotificationClassTest {
    @Test
    public void messageBuilderTest() {
        Notification notification = new Notification();

        notification.setInitiator("testInitiator");
        notification.setMessage("test message");

        String generatedMessage = notification.prepareMessageToSending();
        String assertionMessage = "From: testInitiator\n\r--------------------\n\rtest message";

        assert generatedMessage.equals(assertionMessage);
    }
}
