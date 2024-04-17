package kr.co.lotteon.repository.custom;

import kr.co.lotteon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepositoryCustom {
    
    // 마이페이지 출력을 위해 user_id로 유저 정보 조회
    public User selectUserInfo(String userId);
}
