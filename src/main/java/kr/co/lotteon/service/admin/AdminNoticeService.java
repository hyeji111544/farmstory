package kr.co.lotteon.service.admin;

import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.repository.admin.AdminNoticeRepository;
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
public class AdminNoticeService {

    private final AdminNoticeRepository adminNoticeRepository;
    private final ModelMapper modelMapper;

    // 관리자페이지 고객센터 메뉴 공지사항 글등록
    public void NoticeAdminInsert(NoticeDTO noticeDTO){

        // noticeDTO를 Notice엔티티로 변환
        Notice notice = modelMapper.map(noticeDTO, Notice.class);
        log.info("notice ={}", notice.toString());
        // 매핑된 Notice 엔티티를 DB에 저장
        Notice savedNotice = adminNoticeRepository.save(notice);
        log.info("savedNotice ={}", savedNotice.toString());
    }
    // 관리자페이지 고객센터 메뉴 공지사항 특정 글 불러오기
    public NoticeDTO NoticeAdminView(int noticeNo){

        // .orElse(null); optional 객체가 비어있을경우 대비
        Notice notice = adminNoticeRepository.findById(noticeNo).orElse(null);
        log.info("특정 글 불러오기 modify {}",notice);
        if(notice !=null){
            // DTO로 변환후에 반환
            return modelMapper.map(notice, NoticeDTO.class);
        }
        return null; // 해당 번호의 글이 없는 경우 null반환
    }

    // 관리자페이지 고객센터 메뉴 공지사항 리스트 출력
    public PageResponseDTO NoticeAdminSelect(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("noticeNo");

        Page<Notice> pageNotice = adminNoticeRepository.findAll(pageable);

        log.info("NoticeAdminSelect...1 : " + pageNotice);

        List<NoticeDTO> dtoList = pageNotice.getContent().stream()
                .map(article -> modelMapper.map(article, NoticeDTO.class))
                .toList();

        log.info("NoticeAdminSelect...2 : " + dtoList);

        int total = (int) pageNotice.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
    // 관리자페이지 고객센터 메뉴 공지사항 글 수정
    public void noticeAdminUpdate(NoticeDTO noticeDTO){

        // 게시글 번호
        int noticeNo = noticeDTO.getNoticeNo();

        Notice notice = modelMapper.map(noticeDTO, Notice.class);
        Notice savedNotice = adminNoticeRepository.save(notice);
    }


    // 관리자페이지 고객센터 메뉴 공지사항 글 삭제
    public void noticeAdminDelete(int noticeNo){
        adminNoticeRepository.deleteById(noticeNo);
    }
}