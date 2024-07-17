package co.kr.suhyeong.project.product.domain.model.converter;

import co.kr.suhyeong.project.product.domain.constant.CategoryType;

import javax.persistence.AttributeConverter;

public class CategoryTypeConverter implements AttributeConverter<CategoryType, String> {

    @Override
    public String convertToDatabaseColumn(CategoryType attribute) {
        return attribute.name().toLowerCase();
    }

    @Override
    public CategoryType convertToEntityAttribute(String dbData) {
        return CategoryType.valueOf(dbData.toUpperCase());
    }
}
