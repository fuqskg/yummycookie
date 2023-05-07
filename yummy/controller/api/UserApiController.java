//package com.cookie.yummy.controller.api;
//
//import com.cookie.yummy.dto.ResponseDto;
//import com.cookie.yummy.entity.User;
//import com.cookie.yummy.entity.RoleType;
//import com.cookie.yummy.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class UserApiController {
//
//    @Autowired
//    private MemberService memberService;
//
//    // Json 데이터를 받으려면 @RequestBody로 받아야함
//    // 회원가입
//    @PostMapping("/yummy/auth/joinProc")
//    public ResponseDto<Integer> save(@RequestBody User user) {
//        System.out.println("UAC: save 호출됨");
//
//        user.setRole(RoleType.USER);
//        memberService.join(user);
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON으로 변환하여 전송(Jackson)
//    }
//
//    //전통적방식 로그인
////    @PostMapping("/yummy/login")
////    public ResponseDto<Integer> login(@RequestBody MemberEntity memberEntity, HttpSession session){
////        System.out.println("UAC: login 호출됨");
////
////        MemberEntity principal = memberService.login(memberEntity); //principal(접근주체)
////        if(principal != null){
////            session.setAttribute("principal", principal); //principal을 키값으로 하고 그대로 담으면 세션이 만들어짐
////        }
////        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON으로 변환하여 전송(Jackson)
////
////
////    }
//
//    /*
//    // 이메일 중복검사
//    @PostMapping("/yummy/email-check")
//    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
//        System.out.println("memberEmail = " + memberEmail);
//        String checkResult = memberService.emailCheck(memberEmail);
//        return checkResult;
//    }
//
//     */
//}