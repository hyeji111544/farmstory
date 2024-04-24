package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.ProdQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerQnaRepositoryCustom {
    // 판매자 관리페이지 - QNA List - 목록 조회
    public Page<Tuple> selectSellerQnaList(String prodSeller, PageRequestDTO pageRequestDTO, Pageable pageable);
}
