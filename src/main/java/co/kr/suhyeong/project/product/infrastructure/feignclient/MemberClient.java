package co.kr.suhyeong.project.product.infrastructure.feignclient;

import co.kr.suhyeong.project.config.MemberClientConfig;
import co.kr.suhyeong.project.product.infrastructure.dto.GetMemberRspDTO;
import co.kr.suhyeong.project.product.infrastructure.dto.GetMembersRqtDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static co.kr.suhyeong.project.product.infrastructure.feignclient.MemberUrl.GET_MEMBER;
import static co.kr.suhyeong.project.product.infrastructure.feignclient.MemberUrl.GET_MEMBERS;

@FeignClient(name = "member", url = "${host.member}", configuration = MemberClientConfig.class)
public interface MemberClient {
    @GetMapping(value = GET_MEMBER)
    GetMemberRspDTO getMember(@PathVariable("memberNo") String memberNo);

    @GetMapping(value = GET_MEMBERS)
    void getMembers(@SpringQueryMap GetMembersRqtDto dto);

    @GetMapping(value = GET_MEMBERS)
    void getMembers(@SpringQueryMap Map<String, Object> dto);

    @GetMapping(value = GET_MEMBERS)
    void getMembers(@RequestParam(name = "maxCnt", required = false) int maxCount,
                    @RequestParam(name = "memberNo", required = false) String memberNo,
                    @RequestParam(name = "name", required = false) String memberName,
                    @RequestParam(name = "age", required = false) int age,
                    @RequestParam(name = "maxAge", required = false) int maxAge,
                    @RequestParam(name = "minAge", required = false) int minAge,
                    @RequestParam(name = "birthMonth", required = false) int birthMonth,
                    @RequestParam(name = "phoneNo", required = false) String phoneNo);
}
