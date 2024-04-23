package kr.co.lotteon.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.GraphInfoDTO;
import kr.co.lotteon.dto.SellerInfoDTO;
import kr.co.lotteon.entity.OrderDetail;
import kr.co.lotteon.entity.QOrderDetail;
import kr.co.lotteon.repository.custom.SellerRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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

    // seller-index 페이지 출력 정보 조회
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

        return sellerInfoDTO;
    }

}
