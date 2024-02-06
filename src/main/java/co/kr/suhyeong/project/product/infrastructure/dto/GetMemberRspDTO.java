package co.kr.suhyeong.project.product.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class GetMemberRspDTO {
    private String memberId;
    private String memberName;
}
