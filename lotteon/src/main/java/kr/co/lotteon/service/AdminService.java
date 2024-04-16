package kr.co.lotteon.service;

import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.repository.NoticeRepository;
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
public class AdminService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    // 관리자페이지 고객센터 메뉴 공지사항 글등록
    public void insertNoticeAdmin(NoticeDTO noticeDTO){

        Notice notice = modelMapper.map(noticeDTO, Notice.class);
        log.info("notice ={}", notice.toString());

        Notice savedNotice = noticeRepository.save(notice);
        log.info("savedNotice ={}", savedNotice.toString());
    }

    // 관리자페이지 고객센터 메뉴 공지사항 리스트 출력
    public PageResponseDTO selectNoticeAdmin(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("notice_no");

        Page<Notice> pageNotice = noticeRepository.findAll(pageable);

        log.info("selectNoticeAdmin...1 : " + pageNotice);

        List<NoticeDTO> dtoList = pageNotice.getContent().stream()
                .map(article -> modelMapper.map(article, NoticeDTO.class))
                .toList();

        log.info("selectNoticeAdmin...2 : " + dtoList);

        int total = (int) pageNotice.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
}
