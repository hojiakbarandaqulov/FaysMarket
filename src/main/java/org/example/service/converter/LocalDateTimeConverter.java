package org.example.service.converter;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

    @Override
    public String convertToDatabaseColumn(LocalDateTime locDateTime) {
        return (locDateTime == null ? null : locDateTime.format(FORMATTER));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String sqlTimestamp) {
        return (sqlTimestamp == null ? null : LocalDateTime.parse(sqlTimestamp, FORMATTER));
    }
}