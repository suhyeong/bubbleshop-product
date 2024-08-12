package com.bubbleshop.product.infrastructure;

import com.bubbleshop.product.domain.service.MemberRepository;
import com.bubbleshop.product.infrastructure.feignclient.MemberClient;
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
