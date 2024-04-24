package kr.co.lotteon.service.seller;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.ProdQna;
import kr.co.lotteon.entity.ProdQnaNote;
import kr.co.lotteon.repository.ProdQnaNoteRepository;
import kr.co.lotteon.repository.ProdQnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerQnaService {

    private final ProdQnaRepository prodQnaRepository;
    private final ProdQnaNoteRepository prodQnaNoteRepository;
    private final ModelMapper modelMapper;


    // 판매자 관리페이지 - QNA List - 목록 조회
    public PageResponseDTO selectSellerQnaList(String prodSeller, PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("prodNo");
        Page<Tuple> selectProdList = prodQnaRepository.selectSellerQnaList(prodSeller, pageRequestDTO, pageable);

        List<ProdQnaDTO> prodQnaList = selectProdList.getContent().stream()
                .map(tuple -> {
                    ProdQna prodQna = tuple.get(0, ProdQna.class);
                    String prodName = tuple.get(1, String.class);
                    ProdQnaDTO prodQnaDTO = modelMapper.map(prodQna, ProdQnaDTO.class);
                    prodQnaDTO.setProdName(prodName);
                    return prodQnaDTO;
                })
                .toList();
        int total = (int) selectProdList.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(prodQnaList)
                .total(total)
                .build();
    }

    // 판매자 관리페이지 - QNA View
    public Map<String, Object> selectSellerQnaView(int qnaNo) {
        return prodQnaRepository.selectSellerQnaView(qnaNo);
    }

    // 판매자 관리페이지 - QNA View - 상품 문의 답글 등록
    public ResponseEntity<?> insertQnaNote(ProdQnaNoteDTO prodQnaNoteDTO){
        ProdQnaNote prodQnaNote = modelMapper.map(prodQnaNoteDTO, ProdQnaNote.class);
        ProdQnaNote saveQnaNote = prodQnaNoteRepository.save(prodQnaNote);
        Optional<ProdQna> optProdQna = prodQnaRepository.findById(prodQnaNoteDTO.getProdQnaNo());
        if (optProdQna.isPresent()){
            optProdQna.get().setProdQnaStatus("답변 등록");
            prodQnaRepository.save(optProdQna.get());
        }
        Map<String, String> resultMap = new HashMap<>();
        if (saveQnaNote.getContent() != null) {
            resultMap.put("result", "success");
            return ResponseEntity.ok().body(resultMap);
        }else {
            resultMap.put("result", "fail");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
        }
    }

}
