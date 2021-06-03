package com.company.util.converter;

import com.company.model.dto.EncodedMessageScheduleDTO;
import com.company.model.entity.EncodedMessageSchedule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EncodedMessageScheduleToDTOConverter implements Converter<EncodedMessageSchedule, EncodedMessageScheduleDTO> {
    @Override
    public EncodedMessageScheduleDTO convert(EncodedMessageSchedule encodedMessageSchedule) {
        return EncodedMessageScheduleDTO.builder()
                .encodedMessage(encodedMessageSchedule.getEncodedMessage())
                .build();
    }
}
