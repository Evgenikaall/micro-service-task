package com.company;

import com.company.util.state.Events;
import com.company.util.state.States;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.statemachine.StateMachine;


@Slf4j
@EnableScheduling
@SpringBootApplication
public class ScheduleService implements CommandLineRunner {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(ScheduleService.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(stateMachine.getState().getId().name() + " state ");
        stateMachine.sendEvent(Events.RUN);
    }
}
