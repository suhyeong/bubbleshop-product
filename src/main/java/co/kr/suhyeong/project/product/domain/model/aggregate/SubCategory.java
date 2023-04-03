package co.kr.suhyeong.project.product.domain.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
//@Table(name = ) // TODO 테이블 이름 작성
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory implements Serializable {
    @EmbeddedId
    private SubCategoryId subCategoryId;

}
