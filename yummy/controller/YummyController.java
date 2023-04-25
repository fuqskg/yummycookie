package com.cookie.yummy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class YummyController {

    //메인인덱스
    @GetMapping("/yummy") //http://localhost:8090/ 로 가면 인덱스가 나옴
    public String index(){

        return "index";
    }

    //테스트
    @GetMapping("/test")
    public String test(){
        return "test";
    }





}
