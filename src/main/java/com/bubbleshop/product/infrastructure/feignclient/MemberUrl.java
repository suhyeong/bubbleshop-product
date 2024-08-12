package com.bubbleshop.product.infrastructure.feignclient;

public class MemberUrl {
    protected static final String MEMBER_BASE_URL = "/member/v1";

    public static final String GET_MEMBER = MEMBER_BASE_URL + "/member/{memberNo}";
}
