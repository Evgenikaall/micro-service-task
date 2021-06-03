package com.company.controller.rest;

import com.company.model.dto.EncodedMessageScheduleDTO;
import com.company.service.EncodedMessageScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("v1/message")
@RequiredArgsConstructor
public class EncodedSchedulingMessageRestController {

    private final EncodedMessageScheduleService service;

    @GetMapping
    @ResponseStatus(OK)
    public List<EncodedMessageScheduleDTO> findAllMessages(){
        return service.findAllMessages();
    }

}
