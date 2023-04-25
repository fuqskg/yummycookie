package com.cookie.yummy.controller;

import com.cookie.yummy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// 인증 안 된 사용자들이 출입할 수 있는 경로: /auth/**
// 주소가 / 면 메인페이지로 들어가는 것도 허용
// static이하에 있는 /js/**, /css/**, 이미지 등드 ㅇ허용
@Controller
@RequiredArgsConstructor
public class MemberController {

    //생성자 주입
    private final MemberService memberService;

    //회원가입 페이지 출력 요청
    @GetMapping("/yummy/auth/joinForm")
    public String joinForm(){

        return "joinForm";
    }


    //회원가입 처리
    /*
    @PostMapping("/yummy/join")
    public String join(@ModelAttribute MemberDTO memberDTO){
        System.out.println("MemberController.join");
        System.out.println("memberDTO = " +memberDTO);
        memberService.join(memberDTO);
        return "login";
    }

     */

    //로그인 페이지 출력 요청
    @GetMapping("/yummy/auth/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    //로그인처리
/*
    @PostMapping("/yummy/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null){
            //login success
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "redirect:/";
        }else{
            //login fail
            return "login";
        }
    }


 */



}
