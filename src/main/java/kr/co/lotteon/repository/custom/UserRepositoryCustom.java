package kr.co.lotteon.repository.custom;

import kr.co.lotteon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepositoryCustom {
    
    // 마이페이지 출력을 위해 user_id로 유저 정보 조회
    public User selectUserInfo(String userId);
<<<<<<< HEAD
    // 아이디 찾기
    public Optional<User> findUserIdByUserNameAndUserEmail(String userName, String userEmail);
=======

    // userId 찾기
    Optional<User> findUserIdByUserNameAndUserEmail(String userName, String userEmail);

    // userPw 수정
    public long updateUserPwByUserIdAndUserEmail(String userId,String userPw, String userEmail);
>>>>>>> 6fd9a52db59616ecebf985f89af5226c86248930
}
