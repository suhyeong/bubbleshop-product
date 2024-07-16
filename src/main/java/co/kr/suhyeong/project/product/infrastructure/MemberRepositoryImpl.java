package co.kr.suhyeong.project.product.infrastructure;

import co.kr.suhyeong.project.product.domain.service.MemberRepository;
import co.kr.suhyeong.project.product.infrastructure.feignclient.MemberClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberClient memberClient;

    @Override
    public void getMember(String memberNo) {
        memberClient.getMember(memberNo);
    }
}
