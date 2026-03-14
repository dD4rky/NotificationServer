package ru.dd4rky.notificationserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Notification {
    @Id
    private Long id;

}
