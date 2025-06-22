package com.bubbleshop.product.domain.model.converter;

import com.bubbleshop.product.domain.constant.FeatureType;
import jakarta.persistence.AttributeConverter;

public class ProductFeaturesTypeConverter implements AttributeConverter<FeatureType, String> {
    @Override
    public String convertToDatabaseColumn(FeatureType attribute) {
        return attribute.getCode();
    }

    @Override
    public FeatureType convertToEntityAttribute(String dbData) {
        return FeatureType.find(dbData);
    }
}
