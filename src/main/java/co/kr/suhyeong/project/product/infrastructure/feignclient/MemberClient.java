package co.kr.suhyeong.project.product.infrastructure.feignclient;

import co.kr.suhyeong.project.config.MemberClientConfig;
import co.kr.suhyeong.project.product.infrastructure.dto.GetMemberRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static co.kr.suhyeong.project.product.infrastructure.feignclient.MemberUrl.GET_MEMBER;

@FeignClient(name = "member", url = "${host.member}", configuration = MemberClientConfig.class)
public interface MemberClient {
    @GetMapping(value = GET_MEMBER)
    GetMemberRspDTO getMember(@PathVariable("memberNo") String memberNo);
}
