package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CsController {

    //cs 인덱스페이지 이동
    @GetMapping("/cs/index")
    public String csIndex(){
        return "/cs/index";
    }

    //cs 공지사항 이동
    @GetMapping("/cs/notice/list")
    public String noticeList(){
        return "/cs/notice/list";
    }

    //faq 문의하기 이동
    @GetMapping("/cs/faq/list")
    public String faq(){
        return "/cs/faq/list";
    }

}
