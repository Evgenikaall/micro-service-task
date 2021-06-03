CREATE
SEQUENCE message_schedule_seq
START
1
INCREMENT 1;

CREATE TABLE encoded_message_schedule
(
    encoded_message_schedule_id      BIGINT       DEFAULT nextval('message_schedule_seq') NOT NULL PRIMARY KEY,
    encoded_message_schedule_message VARCHAR(255) DEFAULT 'EMPTY MESSAGE BODY'
);