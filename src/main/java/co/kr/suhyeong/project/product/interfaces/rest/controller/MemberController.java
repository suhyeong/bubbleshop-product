package co.kr.suhyeong.project.product.interfaces.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member API", description = "회원 API FOR TEST")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "member/v1")
public class MemberController extends BaseController {

    @GetMapping("/members")
    public ResponseEntity<Void> getMembers(@RequestParam(name = "maxCnt", required = false) int maxCount,
                                           @RequestParam(name = "memberNo", required = false) String memberNo,
                                           @RequestParam(name = "name", required = false) String memberName,
                                           @RequestParam(name = "age", required = false) int age,
                                           @RequestParam(name = "birthMonth", required = false) int birthMonth,
                                           @RequestParam(name = "phoneNo", required = false) String phoneNo) {
        // SpringQueryMap 테스트를 위한 Controller
        return ResponseEntity.ok().build();
    }

}
