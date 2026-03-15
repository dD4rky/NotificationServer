CREATE TABLE notifications
(
    uuid      UUID NOT NULL,
    created   TIMESTAMP WITHOUT TIME ZONE,
    status    SMALLINT,
    initiator VARCHAR(255),
    message   VARCHAR(255),
    CONSTRAINT pk_notifications PRIMARY KEY (uuid)
);