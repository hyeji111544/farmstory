package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyController {

    //마이페이지 이동
    @GetMapping("/my/home")
    public String myHome(){
        return "/my/home";
    }

}
