package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Cate03;
import kr.co.lotteon.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Integer> {
    // userId로 userCoupon 조회
    public List<UserCoupon> findByUserId(String userId);

    public int countByUserIdAndUcpStatus(String userId, String couponStatus);
}
