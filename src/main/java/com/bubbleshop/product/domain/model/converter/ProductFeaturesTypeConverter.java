package com.bubbleshop.product.domain.model.converter;

import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.util.JsonConvertUtils;

import javax.persistence.AttributeConverter;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductFeaturesTypeConverter implements AttributeConverter<Set<FeatureType>, String> {
    @Override
    public String convertToDatabaseColumn(Set<FeatureType> attribute) {
        Set<String> featureTypeCodes = attribute.stream().map(FeatureType::getCode).collect(Collectors.toSet());
        return JsonConvertUtils.convertSetToJson(featureTypeCodes);
    }

    @Override
    public Set<FeatureType> convertToEntityAttribute(String dbData) {
        Set<String> featureTypeCodes = JsonConvertUtils.convertJsonToSet(dbData);
        return featureTypeCodes.stream().map(FeatureType::find).collect(Collectors.toSet());
    }
}
