package co.kr.suhyeong.project.product.domain.model.converter;


import javax.persistence.AttributeConverter;

import static co.kr.suhyeong.project.constants.StaticValues.COMMON_N;
import static co.kr.suhyeong.project.constants.StaticValues.COMMON_Y;

public class YOrNToBooleanConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? COMMON_Y : COMMON_N;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return dbData.equals(COMMON_Y);
    }
}
