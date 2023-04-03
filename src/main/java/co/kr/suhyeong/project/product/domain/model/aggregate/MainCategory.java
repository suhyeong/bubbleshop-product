package co.kr.suhyeong.project.product.domain.model.aggregate;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
//@Table(name = ) // TODO 테이블 이름 작성
@NoArgsConstructor
@AllArgsConstructor
public class MainCategory implements Serializable {

    @Id
    @Description("메인 카테고리 코드")
    private String mainCategoryCode;

}
