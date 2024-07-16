package co.kr.suhyeong.project.product.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetMembersRqtDto {
    @JsonProperty("maxCnt")
    int maxCount;
    String memberNo;
    @JsonProperty("name")
    String memberName;
    int age;
    int maxAge;
    int minAge;
    int birthMonth;
    @JsonProperty("phoneNo")
    String phoneNumber;
}
