package kr.co.lotteon.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.entity.QUser;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.custom.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QUser qUser = QUser.user;

    // 마이페이지 출력을 위해 user_id로 유저 정보 조회
<<<<<<< HEAD
    public User selectUserInfo(String userId){
         return jpaQueryFactory
                    .selectFrom(qUser)
                    .where(qUser.userId.eq(userId))
                    .fetchOne();
    };
    // 아이디 찾기
=======
    public User selectUserInfo(String userId) {
        return jpaQueryFactory
                .selectFrom(qUser)
                .where(qUser.userId.eq(userId))
                .fetchOne();
    }

    // UserId 찾기
>>>>>>> 6fd9a52db59616ecebf985f89af5226c86248930
    public Optional<User> findUserIdByUserNameAndUserEmail(String userName, String userEmail) {
        User user = jpaQueryFactory
                .selectFrom(qUser)
                .where(qUser.userName.eq(userName)
                        .and(qUser.userEmail.eq(userEmail)))
                .fetchOne();

        return Optional.ofNullable(user);
    }
<<<<<<< HEAD
=======

    // UserPw update
    @Transactional
    public long updateUserPwByUserIdAndUserEmail(String userId, String userPw, String userEmail) {
        try {
            long result = jpaQueryFactory
                    .update(qUser)
                    .set(qUser.userPw, userPw)
                    .where(qUser.userId.eq(userId)
                            .and(qUser.userEmail.eq(userEmail)))
                    .execute();
            log.info("impl : " + result);
            return result;
        } catch (Exception e) {
            log.error("Error msg :" + e.getMessage());
            return -1;
        }
    }
>>>>>>> 6fd9a52db59616ecebf985f89af5226c86248930
}