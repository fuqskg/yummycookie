//package com.cookie.yummy.controller;
//
//import com.cookie.yummy.config.auth.PrincipalDetail;
//import com.cookie.yummy.entity.User;
//import com.cookie.yummy.repository.MemberRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.security.Principal;
//import java.util.Iterator;
//
//@Controller
//public class YummyController {
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    //메인인덱스
//    @GetMapping("") //http://localhost:8090/ 로 가면 인덱스가 나옴
//    public @ResponseBody String index(@AuthenticationPrincipal PrincipalDetail principal){
//        //컨트롤러에서 세션을 찾는 방법
//        System.out.println("로그인 사용자 아이디: " + principal.getUsername());
//
//        return "index";
//    }
//
//    @GetMapping("/user")
//    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetail principal) {
//        System.out.println("Principal : " + principal);
//        System.out.println("로그인 사용자 아이디: " + principal.getUsername());
//
//        // iterator 순차 출력 해보기
//        Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
//        while (iter.hasNext()) {
//            GrantedAuthority auth = iter.next();
//            System.out.println(auth.getAuthority());
//        }
//
//        return "유저 페이지입니다.";
//    }
//
//
//
//
//    @GetMapping("/yummy")
//    public String yummy(){
//        return "index";
//    }
//
//    //테스트
//    @GetMapping("/test")
//    public String test(Model model, @AuthenticationPrincipal PrincipalDetail principalDetail){
//        model.addAttribute("username", principalDetail.getUsername());
//        return "test";
//    }
//
//
//
//
//
//}
