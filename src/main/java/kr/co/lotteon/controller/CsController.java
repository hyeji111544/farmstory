package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CsController {


// cs-index //
    // cs 인덱스페이지 이동
    @GetMapping("/cs/index")
    public String csIndex(){
        return "/cs/index";
    }

// cs-faq //
    // faq 글목록 이동
    @GetMapping("/cs/faq/list")
    public String faqList(){
        return "/cs/faq/list";
    }

    // faq 글보기 이동
    @GetMapping("/cs/faq/view")
    public String faqView(){
        return "/cs/faq/view";
    }

// cs-notice //
    // notice 글목록 이동
    @GetMapping("/cs/notice/list")
    public String noticeList(){
        return "/cs/notice/list";
    }
    
    // notice 글보기 이동
    @GetMapping("/cs/notice/view")
    public String noticeView(){
        return "/cs/notice/view";
    }

// cs-qna //
    // qna 글목록 이동
    @GetMapping("/cs/qna/list")
    public String qnaList(){
        return "/cs/qna/list";
    }

    // qna 글보기 이동
    @GetMapping("/cs/qna/view")
    public String qnaView(){
        return "/cs/qna/view";
    }

    // qna 글쓰기 이동
    @GetMapping("/cs/qna/write")
    public String qnaWrite(){
        return "/cs/qna/write";
    }
}
