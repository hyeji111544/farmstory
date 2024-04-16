package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {


    // 로그인 이동
    @GetMapping("/member/login")
    public String login(){
        return "/member/login";
    }
    
    // 회원가입 약관 동의 이동
    @GetMapping("/member/signup")
    public String signup(){
        return "/member/signup";
    }

    // 회원가입 유형 선택(구매 / 판매) 이동
    @GetMapping("/member/join")
    public String join(){
        return "/member/join";
    }

    // 구매 회원가입 이동
    @GetMapping("/member/register")
    public String register(){
        return "/member/register";
    }

    // 판매 회원가입 이동
    @GetMapping("/member/registerSeller")
    public String registerSeller(){
        return "/member/registerSeller";
    }



}
