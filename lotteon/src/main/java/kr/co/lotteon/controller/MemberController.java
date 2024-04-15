package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    //회원가입 이동
    @GetMapping("/member/register")
    public String register(){
        return "/member/register";
    }

    //로그인 이동
    @GetMapping("/member/login")
    public String login(){
        return "/member/login";
    }

}
