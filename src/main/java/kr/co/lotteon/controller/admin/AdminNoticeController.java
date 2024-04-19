package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.service.admin.AdminNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Log4j2
@Controller
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;

    // 관리자 고객센터 공지사항 리스트 출력
    @GetMapping("/admin/cs/notice/list")
    public String csNoticeList(Model model, PageRequestDTO pageRequestDTO){

        PageResponseDTO pageResponseDTO = adminNoticeService.NoticeAdminSelect(pageRequestDTO);
        log.info("pageResponseDTO : " + pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/admin/cs/notice/list";
    }

    // 관리자 고객센터 공지사항 수정폼 불러오기
    @GetMapping("/admin/cs/notice/modify")
    public String csNoticeModifyForm(@RequestParam("noticeNo") int noticeNo, Model model){

        NoticeDTO noticeDTO = adminNoticeService.NoticeAdminView(noticeNo);
        model.addAttribute("notice",noticeDTO);
        return "/admin/cs/notice/modify";
    }
    // 관리자 고객센터 공지사항 수정
    @PostMapping("/admin/cs/notice/modify")
    public String csNoticeModify(@RequestParam int noticeNo, NoticeDTO noticeDTO){

        log.info("수정 noticeDTO {}", noticeDTO);
        // 기존 글 조회
        NoticeDTO noticeViewDTO = adminNoticeService.NoticeAdminView(noticeNo);

        // 수정된 내용 설정
        noticeViewDTO.setNoticeCate(noticeDTO.getNoticeCate());
        noticeViewDTO.setNoticeTitle(noticeDTO.getNoticeTitle());
        noticeViewDTO.setNoticeContent(noticeDTO.getNoticeContent());

        // 수정된 글 업데이트
        adminNoticeService.noticeAdminUpdate(noticeViewDTO);

        return "redirect:/admin/cs/notice/list";
    }
    // 관리자 고객센터 공지사항 글보기
    @GetMapping("/admin/cs/notice/view")
    public String csNoticeView(Model model, int noticeNo){

        model.addAttribute("view",adminNoticeService.NoticeAdminView(noticeNo));
        return "/admin/cs/notice/view";
    }

    // 관리자 고객센터 공지사항 등록폼 불러오기
    @GetMapping("/admin/cs/notice/write")
    public String csNoticeWriteForm(){
        return "/admin/cs/notice/write";
    }

    // 관리자 고객센터 공지사항 등록
    @PostMapping("/admin/cs/notice/write")
    public String csNoticeWrite(NoticeDTO noticeDTO){

        adminNoticeService.NoticeAdminInsert(noticeDTO);
        return "redirect:/admin/cs/notice/list";
    }

    // 관리자 고객센터 공지사항 글 삭제
    @GetMapping("/admin/cs/notice/delete")
    public String delete(int noticeNo){
        adminNoticeService.noticeAdminDelete(noticeNo);
        return "redirect:/admin/cs/notice/list";
    }
}
