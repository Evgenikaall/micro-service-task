package com.company.controller;

import com.company.model.dto.MessageScheduleDTO;
import com.company.util.state.Events;
import com.company.util.state.States;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulingController {

    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;
    private final StateMachine<States, Events> stateMachine;

    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    public void generateMessageForScheduleMessagePostQueue() {
        final Date currentDate = new Date(System.currentTimeMillis());

        stateMachine.sendEvent(Events.GENERATE);

        MessageScheduleDTO messageScheduleDTO = MessageScheduleDTO.builder()
                .date(currentDate)
                .message("This is message from MessageSchedule Service")
                .build();

        try {
            final Message message = new Message(objectMapper.writeValueAsBytes(messageScheduleDTO));
            stateMachine.sendEvent(Events.SEND);
            amqpTemplate.send("scheduleMessagePostQueue", message);
            log.info("Message sent. Date {}", currentDate);
        } catch (Exception e) {
            log.error("Json parsing exception");
        }finally {
            stateMachine.sendEvent(Events.GENERATE);
        }
    }
}
