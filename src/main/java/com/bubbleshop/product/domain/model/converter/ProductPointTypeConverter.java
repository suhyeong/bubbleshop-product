package com.bubbleshop.product.domain.model.converter;

import com.bubbleshop.product.domain.constant.PointType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

public record ProductPointTypeConverter(ObjectMapper objectMapper) implements AttributeConverter<PointType, String> {

    @Override
    public String convertToDatabaseColumn(PointType attribute) {
        return attribute.getCode();
    }

    @Override
    public PointType convertToEntityAttribute(String dbData) {
        return PointType.find(dbData);
    }
}
