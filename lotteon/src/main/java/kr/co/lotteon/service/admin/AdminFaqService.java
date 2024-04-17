package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.FaqDTO;
import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.entity.Faq;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.repository.admin.AdminFaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminFaqService {

    private final AdminFaqRepository adminFaqRepository;
    private final ModelMapper modelMapper;

    // 관리자페이지 고객센터 메뉴 자주묻는질문 글등록
    public void FaqAdminInsert(FaqDTO faqDTO){

        // noticeDTO를 Notice엔티티로 변환
        Faq faq = modelMapper.map(faqDTO, Faq.class);
        log.info("faq ={}", faq.toString());
        // 매핑된 Notice 엔티티를 DB에 저장
        Faq savedFaq = adminFaqRepository.save(faq);
        log.info("savedFaq ={}", savedFaq.toString());
    }
}
