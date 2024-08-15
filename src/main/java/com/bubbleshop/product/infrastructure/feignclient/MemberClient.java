package com.bubbleshop.product.infrastructure.feignclient;

import com.bubbleshop.config.MemberClientConfig;
import com.bubbleshop.product.infrastructure.dto.GetMemberRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member", url = "${host.member}", configuration = MemberClientConfig.class)
public interface MemberClient {
    @GetMapping(value = MemberUrl.GET_MEMBER)
    GetMemberRspDTO getMember(@PathVariable("memberNo") String memberNo);
}
