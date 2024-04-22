package kr.co.lotteon.service;

import kr.co.lotteon.dto.MyOrderDTO;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyService {

    private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;
    private final OrdersRepository ordersRepository;
    private final OrderdetailRepository orderdetailRepository;
    private final CouponsRepository couponsRepository;
    private final ModelMapper modelMapper;

    /*
        마이페이지 출력을 위한 service
         - user_id로 user테이블 조회 후 userDTO 반환
     */
    public UserDTO selectUserInfo(String userId){
        User user = userRepository.selectUserInfo(userId);
        return modelMapper.map(user, UserDTO.class);
    };

    // 마이페이지 - 연락처 수정
    public ResponseEntity<?> myInfoUpdateHp(String userId, String userHp){
        Optional<User> optUser = userRepository.findById(userId);
        Map<String, String> result = new HashMap<>();
        if (optUser.isPresent()) {
            optUser.get().setUserHp(userHp);
            User saveUser = userRepository.save(optUser.get());
            if (saveUser.getUserHp().equals(userHp)){
                result.put("status", "ok");
                return ResponseEntity.status(HttpStatus.OK).body("ok");
            }else{
                result.put("status", "fail");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("fail");
            }
        }else {
            result.put("status", "notfound");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("notfound");
        }
    }

    // 마이페이지 - 이메일 수정
    public ResponseEntity<?> myInfoUpdateEmail(String userId, String userEmail){
        Optional<User> optUser = userRepository.findById(userId);
        Map<String, String> result = new HashMap<>();
        if (optUser.isPresent()) {
            optUser.get().setUserEmail(userEmail);
            User saveUser = userRepository.save(optUser.get());
            if (saveUser.getUserEmail().equals(userEmail)){
                result.put("status", "ok");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else{
                result.put("status", "fail");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("result");
            }
        }else {
            result.put("status", "notfound");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("notfound");
        }
    }

    // 마이페이지 - 비밀번호 수정
    public ResponseEntity<?> myInfoUpdatePw(String userId, String userPw){
        Optional<User> optUser = userRepository.findById(userId);
        Map<String, String> result = new HashMap<>();
        if (optUser.isPresent()) {
            optUser.get().setUserPw(userPw);
            User saveUser = userRepository.save(optUser.get());
            if (saveUser.getUserPw().equals(userPw)){
                result.put("status", "ok");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else{
                result.put("status", "fail");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("result");
            }
        }else {
            result.put("status", "notfound");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("notfound");
        }
    }

    // 마이페이지 - 주문내역 조회
    public List<MyOrderDTO> selectOrders(String UserId) {
        // userId로 Orders 조회
        return ordersRepository.selectMyOrders(UserId);
    }



    // 마이페이지 - 쿠폰 조회
    public List<Coupons> selectCoupons(String UserId){
        // userId로 userCoupon 조회
        List<UserCoupon> selectUserCoupon = userCouponRepository.findByUserId(UserId);

        log.info("selectUserCoupon : " + selectUserCoupon);

        // userCoupon에서 조회한 cpNo로 쿠폰 정보 조회
        List<Coupons> haveCoupons = new ArrayList<>();

        log.info("haveCoupons : " + haveCoupons);

        if (selectUserCoupon !=null && selectUserCoupon.size()>0){
            for (UserCoupon haveCpNo : selectUserCoupon){
                Coupons findCoupon = couponsRepository.findByCpNo(haveCpNo.getCpNo());
                haveCoupons.add(findCoupon);
                log.info("for문 속 findCoupon : " + findCoupon);
            }
        }
        log.info("마지막 haveCoupons : " + haveCoupons);
        return haveCoupons;
    }

}
