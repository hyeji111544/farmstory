package kr.co.lotteon.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.entity.Banner;
import kr.co.lotteon.entity.QBanner;
import kr.co.lotteon.repository.BannerRepository;
import kr.co.lotteon.repository.custom.BannerRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BannerRepositoryImpl implements BannerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private final QBanner qBanner = QBanner.banner;
    //배너 선택삭제
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
    //배너 불러오기
    public List<Banner> selectBanners(String banImgCate){

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        return jpaQueryFactory
                .selectFrom(qBanner)
                .where(qBanner.banUsable.eq("YES"))
                .where(qBanner.banSdate.before(date))
                .where(qBanner.banEdate.after(date))
                .where(qBanner.banStime.before(time))
                .where(qBanner.banEtime.after(time))
                .where(qBanner.banImgCate.eq(banImgCate))
                .fetch();
    }

    //배너 활성화
    public List<Banner> selectBannerUsable(String banImgCate) {
        return jpaQueryFactory
                .selectFrom(qBanner)
                .where(qBanner.banImgCate.eq(banImgCate))
                .fetch();
    }

}
