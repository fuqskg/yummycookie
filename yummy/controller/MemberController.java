package com.cookie.yummy.controller;

import com.cookie.yummy.dto.MemberDTO;
import com.cookie.yummy.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    //생성자 주입
    private final MemberService memberService;

    //회원가입 페이지 출력 요청
    @GetMapping("/yummy/join")
    public String joinForm(){

        return "join";
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
    @GetMapping("/yumm/join")
    public String join(){
        return "join";
    }

    //로그인 페이지 출력 요청
    @GetMapping("/yummy/login")
    public String loginForm(){
        return "login";
    }

    //로그인처리

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




}
