package kr.co.lotteon.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.MyOrderPageResponseDTO;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.PointHistory;
import kr.co.lotteon.entity.QPointHistory;
import kr.co.lotteon.entity.QUserPoint;
import kr.co.lotteon.repository.custom.UserPointRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserPointRepositoryImpl implements UserPointRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QUserPoint qUserPoint = QUserPoint.userPoint;
    private final QPointHistory qPointHistory = QPointHistory.pointHistory;
    
    //My/point 내역 조회
    public Page<PointHistory> selectPoints(String userId, PageRequestDTO pageRequestDTO, Pageable pageable) {
        // userPoint 테이블에서 pointNO 구하고, pointHistory 조회
        log.info("IMPL 시작");

        // 0. 카테고리에 따른 where절 작성
        // 1. orders테이블에서 10개 조회
        // SELECT orderNo FROM orders WHERE userId = '?' ORDER BY orderDate DESC LIMIT 10
        long total = 0;

        BooleanExpression expression = null; //where절 보관용
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);//일주일전
        LocalDate oneMonthsAgo = LocalDate.now().minusMonths(1);//한달전
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);//3개월전
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);//6개월전
        LocalDate yearOneAgo = LocalDate.now().minusYears(1);//1년전

        if (pageRequestDTO.getCate() != null) {
            // 날짜로 검색하는 경우
            if (pageRequestDTO.getCate().equals("week")) {
                expression = qPointHistory.changeDate.between(oneWeekAgo.atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay());
            } else if (pageRequestDTO.getCate().equals("month")) {
                expression = qPointHistory.changeDate.between(oneMonthsAgo.atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay());
            } else if (pageRequestDTO.getCate().equals("3month")) {
                expression = qPointHistory.changeDate.between(threeMonthsAgo.atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay());
            } else if (pageRequestDTO.getCate().equals("6month")) {
                expression = qPointHistory.changeDate.between(sixMonthsAgo.atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay());
            } else if (pageRequestDTO.getCate().equals("year")) {
                expression = qPointHistory.changeDate.between(yearOneAgo.atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay());
            } else if (pageRequestDTO.getCate().equals("custom")) {
                // 시작날짜
                // 마지막날짜

                LocalDate startDate = pageRequestDTO.getStartDate();
                LocalDate finalDate = pageRequestDTO.getFinalDate();

                log.info("시작날짜 : " +pageRequestDTO.getStartDate());
                log.info("마지막날짜 : " +pageRequestDTO.getFinalDate());

                expression = qPointHistory.changeDate.between(startDate.atStartOfDay(), finalDate.atStartOfDay().plusDays(1).minusSeconds(1));
                log.info("계산된 날짜 : " +expression);
            }
            QueryResults<PointHistory> selectPointNo = jpaQueryFactory
                    .select(qPointHistory)
                    .from(qUserPoint)
                    .join(qPointHistory)
                    .on(qUserPoint.pointNo.eq(qPointHistory.pointNo))
                    .where(qUserPoint.userId.eq(userId))
                    .where(expression)
                    .orderBy(qPointHistory.changeDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();

            total = jpaQueryFactory
                    .select(qPointHistory.count())
                    .from(qUserPoint)
                    .join(qPointHistory)
                    .on(qUserPoint.pointNo.eq(qPointHistory.pointNo))
                    .where(qUserPoint.userId.eq(userId))
                    .where(expression)
                    .orderBy(qPointHistory.changeDate.desc())
                    .fetchOne();

            List<PointHistory> pointHistoryResult = selectPointNo.getResults();
            total = selectPointNo.getTotal();
            log.info("total : " + total);

            return new PageImpl<>(pointHistoryResult, pageable, total);

        } else {
            // 날짜로 검색이 아닌경우
            QueryResults<PointHistory> selectPointNo = jpaQueryFactory
                    .select(qPointHistory)
                    .from(qUserPoint)
                    .join(qPointHistory)
                    .on(qUserPoint.pointNo.eq(qPointHistory.pointNo))
                    .where(qUserPoint.userId.eq(userId))
                    .orderBy(qPointHistory.changeDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();

            total = jpaQueryFactory
                    .select(qPointHistory.count())
                    .from(qUserPoint)
                    .join(qPointHistory)
                    .on(qUserPoint.pointNo.eq(qPointHistory.pointNo))
                    .where(qUserPoint.userId.eq(userId))
                    .orderBy(qPointHistory.changeDate.desc())
                    .fetchOne();

            List<PointHistory> pointHistoryResult = selectPointNo.getResults();
            total = selectPointNo.getTotal();


            return new PageImpl<>(pointHistoryResult, pageable, total);
        }
    }
}
