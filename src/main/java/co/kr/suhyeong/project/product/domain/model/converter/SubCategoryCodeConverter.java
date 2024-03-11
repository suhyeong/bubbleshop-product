package co.kr.suhyeong.project.product.domain.model.converter;


import co.kr.suhyeong.project.product.domain.constant.SubCategoryCode;

import javax.persistence.AttributeConverter;

public class SubCategoryCodeConverter implements AttributeConverter<SubCategoryCode, String> {
    @Override
    public String convertToDatabaseColumn(SubCategoryCode attribute) {
        return attribute.getCode();
    }

    @Override
    public SubCategoryCode convertToEntityAttribute(String dbData) {
        return SubCategoryCode.find(dbData);
    }
}
