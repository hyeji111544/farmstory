package kr.co.lotteon.controller;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.service.CsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CsController {

    private final CsService csService;


// cs-index //
    // cs 인덱스페이지 이동
    @GetMapping("/cs/index")
    public String csIndex(Model model){

        model.addAttribute("notice",csService.noticeList());
        model.addAttribute("qna",csService.qnaList());
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

    // cs-notice list //
    @GetMapping("/cs/notice/list")
    public String noticeList(Model model, PageRequestDTO pageRequestDTO){

    PageResponseDTO pageResponseDTO = csService.selectNoticePages(pageRequestDTO);
    log.info("pageResponseDTO : " + pageResponseDTO);
    model.addAttribute("pageResponseDTO", pageResponseDTO);

    return "/cs/notice/list";
}
    
    // notice 글보기
    @GetMapping("/cs/notice/view")
    public String noticeView(Model model, int noticeNo){
        model.addAttribute("view",csService.noticeView(noticeNo));
        return "/cs/notice/view";
    }

// cs-qna //
    // qna 글목록 이동
    @GetMapping("/cs/qna/list")
    public String qnaList(Model model, PageRequestDTO pageRequestDTO){

        PageResponseDTO pageResponseDTO = csService.selectQnaPages(pageRequestDTO);
        log.info("pageResponseDTO : " + pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/cs/qna/list";
    }

    // qna 글쓰기 이동
    @GetMapping("/cs/qna/write")
    public String qnaWriteForm(){
        return "/cs/qna/write";
    }

    // qna 글쓰기
    @PostMapping("/cs/qna/write")
    public String qnaWrite(QnaDTO qnaDTO){

        qnaDTO.setUserId("gudals1234");
        csService.insertQna(qnaDTO);
        log.info("QnaDTO {}", qnaDTO);
        return "redirect:/cs/qna/list";
    }
    // qna 글보기
    @GetMapping("/cs/qna/view")
    public String qnaView(Model model, int qnaNo){
        model.addAttribute("view",csService.qnaView(qnaNo));
        return "/cs/qna/view";
    }
}
