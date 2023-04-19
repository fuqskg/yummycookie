package com.cookie.yummy.controller.api;

import com.cookie.yummy.dto.ResponseDto;
import com.cookie.yummy.entity.MemberEntity;
import com.cookie.yummy.entity.RoleType;
import com.cookie.yummy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private MemberService memberService;

    // Json 데이터를 받으려면 @RequestBody로 받아야함
    // 회원가입
    @PostMapping("/yummy/joinProc")
    public ResponseDto<Integer> save(@RequestBody MemberEntity memberEntity) {
        System.out.println("UAC: save 호출됨");

        memberEntity.setRole(RoleType.USER);
        int result = memberService.join(memberEntity);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), result); // 자바 오브젝트를 JSON으로 변환하여 전송(Jackson)
    }
}