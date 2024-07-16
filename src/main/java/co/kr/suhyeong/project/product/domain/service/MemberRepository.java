package co.kr.suhyeong.project.product.domain.service;

import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository {
    void getMember(String memberNo);
    void getMembers();
}
