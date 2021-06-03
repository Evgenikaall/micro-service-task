package com.company.listener;

import com.company.model.dto.EncodedMessageScheduleDTO;
import com.company.model.dto.MessageScheduleDTO;
import com.company.service.EncodedMessageScheduleService;
import com.company.util.state.Events;
import com.company.util.state.States;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import static com.company.util.state.Events.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncodedMessageListener {

    private final EncodedMessageScheduleService service;
    private final StateMachine<States, Events> stateMachine;

    @SneakyThrows
    @RabbitListener(queues = "encodedScheduleMessagePostQueue")
    public void listen(EncodedMessageScheduleDTO message) {
        stateMachine.sendEvent(LISTEN);
        stateMachine.sendEvent(CONVERT);

        service.saveMessage(message);

        stateMachine.sendEvent(SAVE);
        Thread.sleep(100 * 2);
        stateMachine.sendEvent(LISTEN);
    }
}
