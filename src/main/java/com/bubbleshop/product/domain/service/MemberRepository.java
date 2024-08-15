package com.bubbleshop.product.domain.service;

import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository {
    void getMember(String memberNo);
}
