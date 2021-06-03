package com.company.util.converter;

import com.company.model.dto.EncodedMessageScheduleDTO;
import com.company.model.entity.EncodedMessageSchedule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EncodedMessageScheduleDTOtoEntityConverter implements Converter<EncodedMessageScheduleDTO, EncodedMessageSchedule> {
    @Override
    public EncodedMessageSchedule convert(EncodedMessageScheduleDTO encodedMessageScheduleDTO) {
        return EncodedMessageSchedule.builder()
                .encodedMessage(encodedMessageScheduleDTO.getEncodedMessage())
                .build();
    }
}
