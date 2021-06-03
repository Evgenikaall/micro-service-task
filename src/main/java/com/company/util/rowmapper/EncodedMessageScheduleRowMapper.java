package com.company.util.rowmapper;

import com.company.model.entity.EncodedMessageSchedule;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EncodedMessageScheduleRowMapper implements RowMapper<EncodedMessageSchedule> {
    @Override
    public EncodedMessageSchedule mapRow(ResultSet resultSet, int i) throws SQLException {
        return EncodedMessageSchedule.builder()
                .id(resultSet.getLong("encoded_message_schedule_id"))
                .encodedMessage(resultSet.getString("encoded_message_schedule_message"))
                .build();
    }
}
