package kr.co.lotteon.service;

import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.entity.Qna;
import kr.co.lotteon.repository.NoticeRepository;
import kr.co.lotteon.repository.QnaRepository;
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
public class CsService {

    private final QnaRepository qnaRepository;
    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    // 고객센터 문의글 등록
    public void insertQna(QnaDTO qnaDTO){

        // qnaDTO를 Qna엔티티로 변환
        Qna qna = modelMapper.map(qnaDTO, Qna.class);
        log.info("qna ={}", qna.toString());
        // 매핑된 Notice 엔티티를 DB에 저장
        Qna savedQna = qnaRepository.save(qna);
        log.info("savedQna ={}", savedQna.toString());
    }
    // notice 리스트
    public List<Notice> noticeList(){
        return noticeRepository.findAll();
    }
    // notice page 리스트 출력
    public PageResponseDTO selectNoticePages(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("noticeNo");

        Page<Notice> pageNotice = noticeRepository.findAll(pageable);

        log.info("selectNoticePages...1 : " + pageNotice);

        List<NoticeDTO> dtoList = pageNotice.getContent().stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .toList();

        log.info("selectNoticePages...2 : " + dtoList);

        int total = (int) pageNotice.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
    // 관리자페이지 고객센터 메뉴 공지사항 특정 글 불러오기
    public NoticeDTO noticeView(int noticeNo){

        // .orElse(null); optional 객체가 비어있을경우 대비
        Notice notice = noticeRepository.findById(noticeNo).orElse(null);
        log.info("특정 글 불러오기 view {}",notice);
        if(notice !=null){
            // DTO로 변환후에 반환
            return modelMapper.map(notice, NoticeDTO.class);
        }
        return null; // 해당 번호의 글이 없는 경우 null반환
    }

    //  qna 리스트
    public List<Qna> qnaList(){
        return qnaRepository.findAll();
    }

}
