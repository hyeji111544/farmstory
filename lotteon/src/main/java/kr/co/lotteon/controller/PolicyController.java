package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PolicyController {

    // 구매회원 이용약관 이동
    @GetMapping("/policy/buyer")
    public String buyer(){
        return "/policy/buyer";
    }

    // 전자금융거래 이용약관 이동
    @GetMapping("/policy/finance")
    public String finance(){
        return "/policy/finance";
    }

    // 위치서비스 이용약관 이동
    @GetMapping("/policy/location")
    public String location(){
        return "/policy/location";
    }

    // 개인정보 이용약관 이동
    @GetMapping("/policy/privacy")
    public String privacy(){
        return "/policy/privacy";
    }

    // 판매회원 이용약관 이동
    @GetMapping("/policy/seller")
    public String seller(){
        return "/policy/seller";
    }

}
