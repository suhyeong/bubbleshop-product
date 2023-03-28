package co.kr.suhyeong.project.product.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
//@Table(name = ) // TODO 테이블 이름 작성
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductImage {
    @EmbeddedId
    private ProductImageId productImageId;

    private String divCode;
}
