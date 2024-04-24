package kr.co.lotteon.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserPointRepositoryImpl implements UserPointRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QUserPoint qUserPoint = QUserPoint.userPoint;
    private final QPointHistory qPointHistory = QPointHistory.pointHistory;
    
    //My/point 내역 조회
    public Page<PointHistory> selectPoints(String userId, PageRequestDTO pageRequestDTO, Pageable pageable){
        // userPoint 테이블에서 pointNO 구하고, pointHistory 조회
        log.info("IMPL 시작");

        // UserPoint, PointHistory 조인
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

        // limit(0, 10)

        log.info("selectPointNo : " +selectPointNo);

        List<PointHistory> selectPointHistory = selectPointNo.getResults();
        long total = selectPointNo.getTotal();

        log.info("selectPointHistory : " +selectPointHistory);

        return new PageImpl<>(selectPointHistory, pageable, total) ;

    }

}
