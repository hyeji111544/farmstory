package kr.co.lotteon.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.entity.QUser;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.custom.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QUser qUser = QUser.user;

    // 마이페이지 출력을 위해 user_id로 유저 정보 조회
    public User selectUserInfo(String userId){
         return jpaQueryFactory
                    .selectFrom(qUser)
                    .where(qUser.userId.eq(userId))
                    .fetchOne();
    };
    // 아이디 찾기
    public Optional<User> findUserIdByUserNameAndUserEmail(String userName, String userEmail) {
        User user = jpaQueryFactory
                .selectFrom(qUser)
                .where(qUser.userName.eq(userName)
                        .and(qUser.userEmail.eq(userEmail)))
                .fetchOne();

        return Optional.ofNullable(user);
    }
}