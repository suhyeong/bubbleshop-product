package co.kr.suhyeong.project.product.domain.model.converter;


import co.kr.suhyeong.project.product.domain.constant.MainCategoryCode;

import javax.persistence.AttributeConverter;

public class MainCategoryCodeConverter implements AttributeConverter<MainCategoryCode, String> {
    @Override
    public String convertToDatabaseColumn(MainCategoryCode attribute) {
        return attribute.getCode();
    }

    @Override
    public MainCategoryCode convertToEntityAttribute(String dbData) {
        return MainCategoryCode.find(dbData);
    }
}
