package com.company.repository;

import com.company.model.dto.EncodedMessageScheduleDTO;
import com.company.model.entity.EncodedMessageSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EncodedMessageScheduleRepository implements CustomRepository<EncodedMessageSchedule, Long> {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;
    private final RowMapper<EncodedMessageSchedule> encodedMessageScheduleRowMapper;

    private final String forInsert =
            "INSERT INTO encoded_message_schedule(encoded_message_schedule_message) VALUES (:message)";

    private final String eachRowSelect =
            "SELECT * FROM encoded_message_schedule";

    @Override
    public int save(EncodedMessageSchedule encodedMessageScheduleDTO) {
        final SqlParameterSource parameterSource =
                new MapSqlParameterSource()
                        .addValue("message", encodedMessageScheduleDTO.getEncodedMessage());

        return parameterJdbcTemplate.update(forInsert, parameterSource);
    }

    @Override
    public List<EncodedMessageSchedule> findAll() {
        return jdbcTemplate.query(eachRowSelect, encodedMessageScheduleRowMapper);
    }


}
