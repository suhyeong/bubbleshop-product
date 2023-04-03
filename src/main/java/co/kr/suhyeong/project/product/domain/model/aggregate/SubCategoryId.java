package co.kr.suhyeong.project.product.domain.model.aggregate;

import jdk.jfr.Description;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class SubCategoryId implements Serializable {
    @Description("메인 카테고리 코드")
    private String mainCategoryCode;

    @Description("서브 카테고리 코드")
    private String subCategoryCode;
}
