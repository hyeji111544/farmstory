package kr.co.lotteon.service.seller;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.ProdQna;
import kr.co.lotteon.repository.SellerQnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerQnaService {

    private final SellerQnaRepository sellerQnaRepository;
    private final ModelMapper modelMapper;


    // 판매자 관리페이지 - QNA List - 목록 조회
    public PageResponseDTO selectSellerQnaList(String prodSeller, PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("prodNo");
        Page<Tuple> selectProdList = sellerQnaRepository.selectSellerQnaList(prodSeller, pageRequestDTO, pageable);

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


}
