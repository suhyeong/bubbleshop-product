package co.kr.suhyeong.project.product.domain.model.converter;

import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;

import javax.persistence.AttributeConverter;

public class ProductImageCodeConverter implements AttributeConverter<ProductImageCode, String> {
    @Override
    public String convertToDatabaseColumn(ProductImageCode attribute) {
        return attribute.getCode();
    }

    @Override
    public ProductImageCode convertToEntityAttribute(String dbData) {
        return ProductImageCode.find(dbData);
    }
}
