package kr.co.lotteon.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.entity.QBanner;
import kr.co.lotteon.repository.custom.BannerRepositoryCumtom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BannerRepositoryImpl implements BannerRepositoryCumtom {

    private final JPAQueryFactory jpaQueryFactory;

    private final QBanner qBanner = QBanner.banner;
    @Transactional
    public boolean deleteBanner(int[] banNo) {
        try{
            for(int i=0; i<banNo.length; i++){
                long result = jpaQueryFactory
                        .delete(qBanner)
                        .where(qBanner.banNo.eq(banNo[i]))
                        .execute();
                if(result == 0){
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            log.error("err:" + e.getMessage());
        }
        return false;
    }
}
