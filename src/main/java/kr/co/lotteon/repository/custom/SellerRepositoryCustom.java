package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.ProductPageRequestDTO;
import kr.co.lotteon.dto.SellerInfoDTO;
import kr.co.lotteon.entity.OrderDetail;
import kr.co.lotteon.entity.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepositoryCustom {
    // 판매자 관리페이지 - 홈
    public SellerInfoDTO selectSellerInfo(String prodSeller);

    // 판매자 관리페이지 - 상품목록 - 상품관리 (전체 상품 목록)
    public Page<Tuple> selectProductForSeller(String prodSeller, ProductPageRequestDTO productPageRequestDTO, Pageable pageable);

    // 판매자 관리페이지 - 상품목록 - 상품관리 (검색 상품 목록)
    public Page<Tuple> searchProductForSeller(String prodSeller, ProductPageRequestDTO productPageRequestDTO, Pageable pageable);
    
    // 판매자 관리페이지 - 주문관리 - 주문현황 (최근 한달 주문 건수 for 그래프)
    // 판매자 관리페이지 - 주문관리 - 매출현황 (최근 한달 매출 요약)
    public List<OrderDetail> selectSalesForMonth(String prodSeller);

    // 판매자 관리페이지 - 주문관리 - 주문현황 (주문 상품 정보 출력)
    public Page<Tuple> selectProdSalesInfo(String prodSeller, PageRequestDTO pageRequestDTO, Pageable pageable);

    // 판매자 관리페이지 - 주문관리 - 매출현황 (전체 기간 매출 요약)
    public List<OrderDetail> selectSalesForTotal(String prodSeller);

    // 판매자 관리페이지 - 주문관리 - 매출현황 (최근 일년 매출 요약)
    public List<OrderDetail> selectSalesForYear(String prodSeller);

    // 판매자 관리페이지 - 주문관리 - 매출현황 (최근 일주일 매출 요약)
    public List<OrderDetail> selectSalesForWeek(String prodSeller);
}
