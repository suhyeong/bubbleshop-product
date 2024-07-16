package co.kr.suhyeong.project.product.infrastructure;

import co.kr.suhyeong.project.product.domain.service.MemberRepository;
import co.kr.suhyeong.project.product.infrastructure.dto.GetMembersRqtDto;
import co.kr.suhyeong.project.product.infrastructure.feignclient.MemberClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberClient memberClient;

    @Override
    public void getMember(String memberNo) {
        memberClient.getMember(memberNo);
    }

    @Override
    public void getMembers() {
        GetMembersRqtDto dto = GetMembersRqtDto.builder()
                .maxCount(1)
                .memberNo("memberNo")
                .memberName("name")
                .age(20)
                .maxAge(0)
                .minAge(0)
                .birthMonth(10)
                .phoneNumber("01011111111")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typedef = new TypeReference<>() {};
        Map<String, Object> map = objectMapper.convertValue(dto, typedef);

        memberClient.getMembers(map);
    }
}
