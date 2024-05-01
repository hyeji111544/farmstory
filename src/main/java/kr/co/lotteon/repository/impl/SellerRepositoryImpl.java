package kr.co.lotteon.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.GraphInfoDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.ProductPageRequestDTO;
import kr.co.lotteon.dto.SellerInfoDTO;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.custom.SellerRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class SellerRepositoryImpl implements SellerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QOrderDetail qOrderDetail = QOrderDetail.orderDetail;
    private final QProduct qProduct = QProduct.product;
    private final QProductimg qProductimg = QProductimg.productimg;
    private final QOrders qOrders = QOrders.orders;

    private final QSeller qSeller = QSeller.seller;

    // 판매자 관리페이지 - 홈 출력 정보 조회
    @Override
    public SellerInfoDTO selectSellerInfo(String prodSeller){

        SellerInfoDTO sellerInfoDTO = new SellerInfoDTO();

        // 신규 주문 건수 & 신규 주문 금액 (하루)
        LocalDate oneDay = LocalDate.now().minusDays(1);
        Tuple oneDayInfo = jpaQueryFactory
                            .select(qOrderDetail.count(), qOrderDetail.detailPrice.sum())
                            .from(qOrderDetail)
                            .where(qOrderDetail.prodSeller.eq(prodSeller))
                            .where(qOrderDetail.detailDate.between(oneDay, LocalDate.now()))
                            .fetchOne();

        log.info("oneDayInfo : " + oneDayInfo);

        // 주간 주문 건수 & 주간 주문 금액 (최근 일주일)
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        Tuple oneWeekInfo = jpaQueryFactory
                .select(qOrderDetail.count(), qOrderDetail.detailPrice.sum())
                .from(qOrderDetail)
                .where(qOrderDetail.prodSeller.eq(prodSeller))
                .where(qOrderDetail.detailDate.between(oneWeekAgo, LocalDate.now()))
                .fetchOne();
        log.info("oneWeekInfo : " + oneWeekInfo);

        // 월간 주문 건수 & 월간 주문 금액 (최근 한달)
        LocalDate oneMonthsAgo = LocalDate.now().minusMonths(1);
        Tuple oneMonthsInfo = jpaQueryFactory
                .select(qOrderDetail.count(), qOrderDetail.detailPrice.sum())
                .from(qOrderDetail)
                .where(qOrderDetail.prodSeller.eq(prodSeller))
                .where(qOrderDetail.detailDate.between(oneMonthsAgo, LocalDate.now()))
                .fetchOne();
        log.info("oneMonthsInfo : " + oneMonthsInfo);

        // 전체 주문 건수 & 전체 주문 금액
        Tuple allInfo = jpaQueryFactory
                .select(qOrderDetail.count(), qOrderDetail.detailPrice.sum())
                .from(qOrderDetail)
                .where(qOrderDetail.prodSeller.eq(prodSeller))
                .fetchOne();
        log.info("allInfo : " + allInfo);

        // 각 status별 개수 조회
        List<Tuple> allStatus = jpaQueryFactory
                .select(qOrderDetail.detailStatus, qOrderDetail.count())
                .from(qOrderDetail)
                .where(qOrderDetail.prodSeller.eq(prodSeller))
                .groupBy(qOrderDetail.detailStatus)
                .fetch();
        log.info("allStatus : " + allStatus);

        // 각 status별 개수를 Key-Value값으로 저장
        Map<String, Long> statusCountMap = new HashMap<>();
        for (Tuple tuple : allStatus) {
            String status = tuple.get(0, String.class);
            Long count = tuple.get(1, Long.class);
            statusCountMap.put(status, count);
        }
        log.info("statusCountMap : " + statusCountMap);

        // 그래프 출력을 위한
        List<OrderDetail> GraphInfo = jpaQueryFactory
                                .selectFrom(qOrderDetail)
                                .where(qOrderDetail.prodSeller.eq(prodSeller))
                                .where(qOrderDetail.detailDate.between(oneMonthsAgo, LocalDate.now()))
                                .orderBy(qOrderDetail.detailDate.asc())
                                .fetch();
        log.info("GraphInfo : " + GraphInfo);

        // 모든 날짜를 포함하는 기간을 정의
        List<LocalDate> allDatesInRange = Stream.iterate(oneMonthsAgo, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(oneMonthsAgo, LocalDate.now()) + 1)
                .collect(Collectors.toList());

        log.info("allDatesInRange : " + allDatesInRange);

        List<GraphInfoDTO> graphInfoList = new ArrayList<>();

        for (int i = allDatesInRange.size() - 1; i >= 0; i--) {
            LocalDate date = allDatesInRange.get(i);
            GraphInfoDTO InfoDTO = new GraphInfoDTO();

            for (OrderDetail infoData : GraphInfo){
                if (date.equals(infoData.getDetailDate())){
                    int addPrice = InfoDTO.getPrice() + infoData.getDetailPrice();
                    InfoDTO.setCount(InfoDTO.getCount() + 1);
                    InfoDTO.setPrice(addPrice);
                    InfoDTO.setDate(date);
                }
                log.info("InfoDTO : " + InfoDTO);
            }
            graphInfoList.add(InfoDTO);
        }

        log.info("graphInfoList : " + graphInfoList);

        // 여기서 구한 값들을 하나의 DTO에 넣어서 반환 시키면 끝
        if (oneDayInfo.get(0, Long.class) != null && oneDayInfo.get(0, Long.class) != 0) {
            sellerInfoDTO.setOneDayCount(oneDayInfo.get(0, Long.class));
            sellerInfoDTO.setOneDayPrice(oneDayInfo.get(1, Integer.class));
        }else {
            sellerInfoDTO.setOneDayCount(0L);
            sellerInfoDTO.setOneDayPrice(0);
        }

        log.info("sellerInfoDTO : " + sellerInfoDTO);
        if (oneWeekInfo.get(0, Long.class) != null && oneWeekInfo.get(0, Long.class) != 0) {
            sellerInfoDTO.setOneWeekCount(oneWeekInfo.get(0, Long.class));
            sellerInfoDTO.setOneWeekPrice(oneWeekInfo.get(1, Integer.class));
        }else {
            sellerInfoDTO.setOneWeekCount(0L);
            sellerInfoDTO.setOneWeekPrice(0);
        }
        log.info("sellerInfoDTO : " + sellerInfoDTO);

        if (oneMonthsInfo.get(0, Long.class) != null && oneMonthsInfo.get(0, Long.class) != 0) {
            sellerInfoDTO.setOneMonthsCount(oneMonthsInfo.get(0, Long.class));
            sellerInfoDTO.setOneMonthsPrice(oneMonthsInfo.get(1, Integer.class));
        }else {
            sellerInfoDTO.setOneMonthsCount(0L);
            sellerInfoDTO.setOneMonthsPrice(0);
        }
        log.info("sellerInfoDTO : " + sellerInfoDTO);

        if (allInfo.get(0, Long.class) != null && allInfo.get(0, Long.class) != 0) {
            sellerInfoDTO.setAllCount(allInfo.get(0, Long.class));
            sellerInfoDTO.setAllPrice(allInfo.get(1, Integer.class));
        }else {
            sellerInfoDTO.setAllCount(0L);
            sellerInfoDTO.setAllPrice(0);
        }
        log.info("sellerInfoDTO : " + sellerInfoDTO);

        sellerInfoDTO.setStatusCountMap(statusCountMap);
        sellerInfoDTO.setGraphInfoDTO(graphInfoList);

        log.info("sellerInfoDTO : " + sellerInfoDTO);


        // count, price 속성의 최댓값을 구함
        OptionalInt maxCount = graphInfoList.stream()
                .mapToInt(GraphInfoDTO::getCount)
                .max();
        OptionalInt maxPrice = graphInfoList.stream()
                .mapToInt(GraphInfoDTO::getPrice)
                .max();
        // 최댓값이 존재하는지 확인하고, 존재한다면 값을 가져옴
        if (maxCount.isPresent() && maxPrice.isPresent()) {
            int count = maxCount.getAsInt();
            int price = maxPrice.getAsInt();
            sellerInfoDTO.setMaxCount(count);
            sellerInfoDTO.setMaxPrice(price);
            log.info("최댓값: " + count);
            log.info("최댓값: " + price);
        } else {
            log.info("최댓값없음");
        }
        return sellerInfoDTO;
    }

    // 판매자 관리페이지 - 상품목록 - 상품관리 (전체 상품 목록)
    public Page<Tuple> selectProductForSeller(String prodSeller, ProductPageRequestDTO productPageRequestDTO, Pageable pageable) {

        // product - productImg 테이블 join / 판매자 아이디와 일치하는 경우 최신순
        QueryResults<Tuple> selectProducts = jpaQueryFactory
                                    .select(qProduct, qProductimg.thumb190)
                                    .from(qProduct)
                                    .join(qProductimg)
                                    .on(qProduct.prodNo.eq(qProductimg.prodNo))
                                    .where(qProduct.prodSeller.eq(prodSeller))
                                    .orderBy(qProduct.prodRdate.desc())
                                    .offset(pageable.getOffset())
                                    .limit(pageable.getPageSize())
                                    .fetchResults();

        List<Tuple> productsResults = selectProducts.getResults();
        long total = selectProducts.getTotal();
        log.info("total : " + total);

        return new PageImpl<>(productsResults, pageable, total);
    }

    // 판매자 관리페이지 - 상품목록 - 상품관리 (검색 상품 목록)
    public Page<Tuple> searchProductForSeller(String prodSeller, ProductPageRequestDTO productPageRequestDTO, Pageable pageable) {

        String type = productPageRequestDTO.getType();
        String keyword = productPageRequestDTO.getKeyword();
        BooleanExpression expression = null;

        // 검색 종류에 따른 where절 표현식 생성
        if(type.equals("prodName")){
            expression = qProduct.prodName.contains(keyword);

        }else if(type.equals("prodNo")){
            expression = qProduct.prodNo.eq(Integer.parseInt(keyword));

        }else if(type.equals("prodCompany")){
            expression = qProduct.prodCompany.contains(keyword);
        }

        // product - productImg 테이블 join / 판매자 아이디와 일치하는 경우 / type과 keyword가 일치하는 경우 / 최신순
        QueryResults<Tuple> selectProducts = jpaQueryFactory
                .select(qProduct, qProductimg.thumb190)
                .from(qProduct)
                .join(qProductimg)
                .on(qProduct.prodNo.eq(qProductimg.prodNo))
                .where(qProduct.prodSeller.eq(prodSeller))
                .where(expression)
                .orderBy(qProduct.prodRdate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Tuple> productsResults = selectProducts.getResults();
        long total = selectProducts.getTotal();
        return new PageImpl<>(productsResults, pageable, total);
    }

    // 판매자 관리페이지 - 주문관리 - 주문현황 (최근 한달 주문 건수 for 그래프)
    // 판매자 관리페이지 - 주문관리 - 매출현황 (최근 한달 주문 금액 for 그래프)
    public List<OrderDetail> selectSalesForMonth(String prodSeller) {
        LocalDate oneMonthsAgo = LocalDate.now().minusMonths(1);
        return jpaQueryFactory
                    .selectFrom(qOrderDetail)
                    .where(qOrderDetail.prodSeller.eq(prodSeller))
                    .where(qOrderDetail.detailDate.between(oneMonthsAgo, LocalDate.now()))
                    .orderBy(qOrderDetail.detailDate.desc())
                    .fetch();
    }
    
    // 판매자 관리페이지 - 주문관리 - 주문현황 (주문 상품 정보 출력)
    public Page<Tuple> selectProdSalesInfo(String prodSeller, PageRequestDTO pageRequestDTO, Pageable pageable) {

        QueryResults<Tuple> selectOrders = null;
        BooleanExpression expression = null;

        if (pageRequestDTO.getKeyword() != null) {
            log.info("keyword : " + pageRequestDTO.getKeyword());
            if (pageRequestDTO.getKeyword().equals("waiting")){
                expression = qOrderDetail.detailStatus.contains("입금대기");
            }else if (pageRequestDTO.getKeyword().equals("ready")){
                expression = qOrderDetail.detailStatus.contains("배송준비");
            }else if (pageRequestDTO.getKeyword().equals("doing")){
                expression = qOrderDetail.detailStatus.contains("배송중");
            }else if (pageRequestDTO.getKeyword().equals("finish")){
                expression = qOrderDetail.detailStatus.contains("배송완료");
            }else if (pageRequestDTO.getKeyword().equals("cancel")){
                expression = qOrderDetail.detailStatus.contains("취소요청");
            }else if (pageRequestDTO.getKeyword().equals("exchange")){
                expression = qOrderDetail.detailStatus.contains("교환요청");
            }else if (pageRequestDTO.getKeyword().equals("refund")){
                expression = qOrderDetail.detailStatus.contains("환불요청");
            }
            selectOrders = jpaQueryFactory
                    .select(qOrderDetail, qProduct.prodName, qOrders)
                    .from(qOrderDetail)
                    .join(qProduct)
                    .on(qOrderDetail.prodNo.eq(qProduct.prodNo))
                    .join(qOrders)
                    .on(qOrderDetail.orderNo.eq(qOrders.orderNo))
                    .where(qOrderDetail.prodSeller.eq(prodSeller))
                    .where(expression)
                    .orderBy(qOrderDetail.detailDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();
        }else {
            selectOrders = jpaQueryFactory
                    .select(qOrderDetail, qProduct.prodName, qOrders)
                    .from(qOrderDetail)
                    .join(qProduct)
                    .on(qOrderDetail.prodNo.eq(qProduct.prodNo))
                    .join(qOrders)
                    .on(qOrderDetail.orderNo.eq(qOrders.orderNo))
                    .where(qOrderDetail.prodSeller.eq(prodSeller))
                    .orderBy(qOrderDetail.detailDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();
        }
        List<Tuple> orderResults = selectOrders.getResults();
        long total = selectOrders.getTotal();
        return new PageImpl<>(orderResults, pageable, total);
    }

    // 판매자 관리페이지 - 주문관리 - 매출현황 (전체 기간 매출 요약)
    public List<OrderDetail> selectSalesForTotal(String prodSeller) {
        return jpaQueryFactory
                .selectFrom(qOrderDetail)
                .where(qOrderDetail.prodSeller.eq(prodSeller))
                .orderBy(qOrderDetail.detailDate.desc())
                .fetch();
    }

    // 판매자 관리페이지 - 주문관리 - 매출현황 (최근 일년 매출 요약)
    public List<OrderDetail> selectSalesForYear(String prodSeller) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        return jpaQueryFactory
                .selectFrom(qOrderDetail)
                .where(qOrderDetail.prodSeller.eq(prodSeller))
                .where(qOrderDetail.detailDate.between(oneYearAgo, LocalDate.now()))
                .orderBy(qOrderDetail.detailDate.desc())
                .fetch();
    }

    // 판매자 관리페이지 - 주문관리 - 매출현황 (최근 일주일 매출 요약)
    public List<OrderDetail> selectSalesForWeek(String prodSeller) {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        return jpaQueryFactory
                .selectFrom(qOrderDetail)
                .where(qOrderDetail.prodSeller.eq(prodSeller))
                .where(qOrderDetail.detailDate.between(oneWeekAgo, LocalDate.now()))
                .orderBy(qOrderDetail.detailDate.desc())
                .fetch();
    }

    @Transactional
    public long updateSellerNameByUserId(String userId, String sellerName) {
        try{
            long result = jpaQueryFactory
                    .update(qSeller)
                    .set(qSeller.sellerName, sellerName)
                    .where(qSeller.userId.eq(userId))
                    .execute();
            //성공시 1로 반환
            return result;
        }catch(Exception e){
            log.error("error msg :" + e.getMessage());
            return -1;
        }
    }

    @Transactional
    public long updateSellerHpByUserId(String userId, String sellerHp) {
        try{
            long result = jpaQueryFactory
                    .update(qSeller)
                    .set(qSeller.sellerHp, sellerHp)
                    .where(qSeller.userId.eq(userId))
                    .execute();
            return result;
        }catch(Exception e){
            log.error("error msg: " + e.getMessage());
            return -1;
        }
    }
/*
    @Transactional
    public long updateSellerFaxByUserId(String userId, String fax) {
        try{
            long result = jpaQueryFactory
                    .update(qSeller)
                    .set(qSeller.fax, fax)
                    .where(qSeller.userId.eq(userId))
                    .execute();
            return result;
        }catch(Exception e){
            log.error("error msg: " + e.getMessage());
            return -1;
        }
    }*/
}
