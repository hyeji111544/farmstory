package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.FaqDTO;
import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.repository.admin.AdminFaqRepository;
import kr.co.lotteon.service.admin.AdminFaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminFaqController {

    private final AdminFaqService adminFaqService;

    @GetMapping("/admin/cs/faq/list")
    public String csFaqList(){

        return "/admin/cs/faq/list";
    }
    // 관리자 고객센터 자주묻는질문 등록폼
    @GetMapping("/admin/cs/faq/write")
    public String csFaqWriteForm(){
        return "/admin/cs/faq/write";
    }
    // 관리자 고객센터 공지사항 등록
    @PostMapping("/admin/cs/faq/write")
    public String csFaqWrite(FaqDTO faqDTO){

        adminFaqService.FaqAdminInsert(faqDTO);
        return "redirect:/admin/cs/faq/list";
    }

    @GetMapping("/admin/cs/faq/modify")
    public String csFaqModify(){

        return "/admin/cs/faq/modify";
    }

    @GetMapping("/admin/cs/faq/view")
    public String csFaqView(){

        return "/admin/cs/faq/view";
    }

}
