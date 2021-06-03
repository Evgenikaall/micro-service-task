package com.company.service;

import com.company.model.dto.EncodedMessageScheduleDTO;
import com.company.model.entity.EncodedMessageSchedule;
import com.company.repository.EncodedMessageScheduleRepository;
import com.company.util.state.Events;
import com.company.util.state.States;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.company.util.state.Events.CONVERT;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class EncodedMessageScheduleService {

    private final EncodedMessageScheduleRepository repository;
    private final StateMachine<States, Events> stateMachine;

    private final Converter<EncodedMessageSchedule, EncodedMessageScheduleDTO> toDTO;
    private final Converter<EncodedMessageScheduleDTO, EncodedMessageSchedule> toEntity;


    public int saveMessage(EncodedMessageScheduleDTO encodedMessageScheduleDTO){
        stateMachine.sendEvent(CONVERT);
        final EncodedMessageSchedule convert = toEntity.convert(encodedMessageScheduleDTO);

        return repository.save(convert);
    }

    public List<EncodedMessageScheduleDTO> findAllMessages(){
        return repository.findAll().stream().map(toDTO::convert).collect(toList());
    }

}
