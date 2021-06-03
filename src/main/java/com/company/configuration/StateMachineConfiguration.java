package com.company.configuration;

import com.company.util.state.Events;
import com.company.util.state.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.statemachine.StateMachineSystemConstants;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;


import static com.company.util.state.Events.CONVERT;
import static com.company.util.state.Events.END_LISTEN;
import static com.company.util.state.Events.GENERATE;
import static com.company.util.state.Events.LISTEN;
import static com.company.util.state.Events.REPEAT;
import static com.company.util.state.Events.RUN;
import static com.company.util.state.States.CONVERTED;
import static com.company.util.state.States.END_GENERATOR;
import static com.company.util.state.States.END_LISTENER;
import static com.company.util.state.States.FORK;
import static com.company.util.state.States.GENERATED;
import static com.company.util.state.States.JOIN;
import static com.company.util.state.States.LISTENED;
import static com.company.util.state.States.READY;
import static com.company.util.state.States.SAVED;
import static com.company.util.state.States.SEND;
import static com.company.util.state.States.TASK;

@Slf4j
@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(READY)
                .fork(FORK)
                .state(TASK)
                .join(JOIN)
                .and()
                .withStates()
                    .parent(TASK)
                    .initial(LISTENED)
                    .state(CONVERTED)
                    .state(SAVED)
                    .end(END_LISTENER)
                .and()
                .withStates()
                    .parent(TASK)
                    .initial(GENERATED)
                    .state(SEND)
                    .end(END_GENERATOR);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(READY).target(FORK).event(RUN)
                .and()
                .withFork()
                    .source(FORK).target(TASK)
                .and()
                .withExternal()
                    .source(TASK).target(LISTENED).event(LISTEN)
                .and()
                .withExternal()
                    .source(LISTENED).target(CONVERTED).event(LISTEN)
                .and()
                .withExternal()
                    .source(CONVERTED).target(SAVED).event(CONVERT)
                .and()
                .withExternal()
                    .source(SAVED).target(END_LISTENER).event(END_LISTEN)
                .and()
                .withExternal()
                    .source(SAVED).target(LISTENED).event(LISTEN)
                .and()
                .withExternal()
                    .source(TASK).target(GENERATED).event(GENERATE)
                .and()
                .withExternal()
                    .source(GENERATED).target(SEND).event(Events.SEND)
                .and()
                .withExternal()
                    .source(SEND).target(END_GENERATOR).event(Events.END_GENERATOR)
                .and()
                .withExternal()
                    .source(SEND).target(GENERATED).event(GENERATE)
                .and()
                .withJoin()
                    .source(TASK).target(JOIN)
                .and()
                .withExternal()
                    .source(JOIN).target(READY).event(REPEAT);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener())
        ;
        }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                final States id = (from == null) ? null : from.getId();
                log.info("{} ==> {}", id,to.getId());
            }
        };
    }


    @Bean(name = StateMachineSystemConstants.TASK_EXECUTOR_BEAN_NAME)
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        return taskExecutor;
    }
}
