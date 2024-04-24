package kr.co.lotteon.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.ProdQna;
import kr.co.lotteon.entity.QProdQna;
import kr.co.lotteon.entity.QProduct;
import kr.co.lotteon.repository.custom.SellerQnaRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SellerQnaRepositoryImpl implements SellerQnaRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct qProduct = QProduct.product;
    private final QProdQna qprodQna = QProdQna.prodQna;

    // 판매자 관리페이지 - QNA List - 목록 조회
    public Page<Tuple> selectSellerQnaList(String prodSeller, PageRequestDTO pageRequestDTO, Pageable pageable){

        // 전체 조회
        if (pageRequestDTO.getKeyword() == null){

            QueryResults<Tuple> selectQnaList = jpaQueryFactory
                                            .select(qprodQna, qProduct.prodName)
                                            .from(qprodQna)
                                            .join(qProduct)
                                            .on(qprodQna.prodNo.eq(qProduct.prodNo))
                                            .where(qProduct.prodSeller.eq(prodSeller))
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetchResults();

            log.info("selectQnaList : " + selectQnaList.toString());

            List<Tuple> qnaList = selectQnaList.getResults();

            int total = (int)selectQnaList.getTotal();

            return new PageImpl<>(qnaList, pageable, total);

        }
        // 검색 조회
        return null;
    }

}
