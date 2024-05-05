package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyService {

    private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;
    private final OrdersRepository ordersRepository;
    private final OrderdetailRepository orderdetailRepository;
    private final WishRepository wishRepository;

    private final CouponsRepository couponsRepository;
    private final ModelMapper modelMapper;
    private final UserPointRepository userPointRepository;
    private final SellerRepository sellerRepository;


    /*
        마이페이지 출력을 위한 service
         - user_id로 user테이블 조회 후 userDTO 반환

    public int myServiceCheck(HttpSession session, String type, String value){
        int result = 0;

        if(type.equals("userEmail"))
    }*/

    public Map<String, Object> selectUserInfo(String userId){
        // 유저테이블에서 아이디로 정보 검색
        Optional<User> optUser = userRepository.findById(userId);
        SellerDTO sellerDTO = new SellerDTO();
        UserDTO userDTO = new UserDTO();
        if(optUser.isPresent()){
            if(optUser.get().getUserRole().equals("SELLER")){
                // 사용자가 seller인 경우
                Optional<Seller> optSeller = sellerRepository.findByUserId(userId);
                sellerDTO = modelMapper.map(optSeller.get(), SellerDTO.class);
            }
            userDTO = modelMapper.map(optUser.get(), UserDTO.class);
        }
        // 그냥 user인 경우

        Map<String, Object> result = new HashMap<>();
        result.put("user", userDTO);
        result.put("seller", sellerDTO);

        return result;
    };
    public UserDTO selectSellerInfo(String sellerId) {
        Optional<User> seller = userRepository.findById(sellerId);
        log.info("seller :" + seller);
        if (seller.isPresent()) {
            return modelMapper.map(seller.get(),UserDTO.class);
        }
        return null;
    }
    // 마이페이지 - 판매자 이름 수정
    public ResponseEntity<?> myInfoUpdateSellerName(String userId, String sellerName){
        long result = sellerRepository.updateSellerNameByUserId(userId,sellerName);
        log.info("userId :" + userId);
        log.info("sellerName :" + sellerName);

        if(result > 0){
            //업데이트가 됐을경우
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else{
            // 업데이트 실패
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("문제가 발생했습니다.");
        }
    }
    // 마이페이지 - 판매자 연락처 수정
    public ResponseEntity<?> myInfoUpdateSellerHp(String userId, String sellerHp){
        long result = sellerRepository.updateSellerHpByUserId(userId,sellerHp);
        log.info("userId :" + userId);
        log.info("sellerName :" + sellerHp);

        if(result > 0){
            //업데이트가 됐을경우
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else{
            // 업데이트 실패
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("문제가 발생했습니다.");
        }
    }
    // 마이페이지 - 판매자 팩스번호 수정
    public ResponseEntity<?> myInfoUpdateFax(String userId, String fax){
        Optional<Seller> optUser = sellerRepository.findByUserId(userId);
        log.info("userId :" + userId);
        log.info("fax :" + fax);
        Map<String,String> result = new HashMap<>();
        if(optUser.isPresent()){
            optUser.get().setFax(fax);
            Seller saveSeller = sellerRepository.save(optUser.get());
            log.info("saveSeller :" + saveSeller);
            if(saveSeller.getFax().equals(fax)){
                result.put("status", "ok");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else{
                result.put("status", "fail");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        }else{
            result.put("status","notfound");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    // 마이페이지 - 연락처 수정
    public ResponseEntity<?> myInfoUpdateHp(String userId, String userHp){
        Optional<User> optUser = userRepository.findById(userId);
        Map<String, String> result = new HashMap<>();
        if (optUser.isPresent()) {
            optUser.get().setUserHp(userHp);
            User saveUser = userRepository.save(optUser.get());
            if (saveUser.getUserHp().equals(userHp)){
                result.put("status", "ok");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else{
                result.put("status", "fail");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        }else {
            result.put("status", "notfound");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
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

    //마이페이지 주소 수정
    public ResponseEntity<?> myInfoUpdateAddr(UserDTO userDTO) {
        log.info("myInfoUpdateAddr :" + userDTO);

        Optional<User> findId = userRepository.findById(userDTO.getUserId());

        if (findId.isPresent()) {

            User user = findId.get();

            user.setUserZip(userDTO.getUserZip());
            user.setUserAddr1(userDTO.getUserAddr1());
            user.setUserAddr2(userDTO.getUserAddr2());
            User savedUser = userRepository.save(user);

            Map<String, User> result = new HashMap<>();
            result.put("data", savedUser);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);
        }else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("not found");
        }
    }

    // 마이페이지 - 회원 탈퇴
    public ResponseEntity<?> myInfoUpdateRole(String userId, String userRole){
        Optional<User> optUser = userRepository.findById(userId);
        Map<String, String> result = new HashMap<>();
        if(optUser.isPresent()){
            optUser.get().setUserRole("DELETE");
            User savedUser = userRepository.save(optUser.get());
            if(savedUser.getUserRole().equals("DELETE")){
                result.put("status", "ok");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else{
                result.put("status","fail");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("result");
            }
        }else{
            result.put("status", "not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }

    // 마이페이지 - 주문내역 조회
    public MyOrderPageResponseDTO selectOrders(String UserId, MyOrderPageRequestDTO myOrderPageRequestDTO) {
        //페이징 처리
        Pageable pageable = myOrderPageRequestDTO.getPageable("no");

        // userId로 Orders 조회
        return ordersRepository.selectMyOrdersByDate(UserId, pageable, myOrderPageRequestDTO);
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

    // 마이페이지 - 포인트내역 조회
    public PageResponseDTO selectPoints(String userId, PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("pointHisNo");

        Page<PointHistory> pagePointHistory = userPointRepository.selectPoints(userId, pageRequestDTO, pageable);

        List<PointHistoryDTO> dtoList = pagePointHistory.getContent().stream()
                .map(history -> {
                    PointHistoryDTO pointHistoryDTO = new PointHistoryDTO();
                    pointHistoryDTO.setPointNo(history.getPointHisNo());
                    pointHistoryDTO.setPointNo(history.getPointNo());
                    pointHistoryDTO.setChangePoint(history.getChangePoint());
                    pointHistoryDTO.setChangeDate(history.getChangeDate());
                    pointHistoryDTO.setChangeCode(history.getChangeCode());
                    pointHistoryDTO.setChangeType(history.getChangeType());

                    return pointHistoryDTO;
                    // for (String aa : aaaa) {}
                })
                .toList();
        int total = (int) pagePointHistory.getTotalElements();
        log.info("total : " + total);

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    //myHome 포인트내역
    public List<PointHistoryDTO> myHomeselectPoints (String userId){

        List<PointHistory> myPointHistory = userPointRepository.myHomeSelectPoints(userId);
        List<PointHistoryDTO> myPointHistoryDTO = myPointHistory.stream()
                .map(pointHistory -> {
                    PointHistoryDTO pointHistoryDTO = new PointHistoryDTO();
                    pointHistoryDTO.setPointNo(pointHistory.getPointHisNo());
                    pointHistoryDTO.setPointNo(pointHistory.getPointNo());
                    pointHistoryDTO.setChangePoint(pointHistory.getChangePoint());
                    pointHistoryDTO.setChangeDate(pointHistory.getChangeDate());
                    pointHistoryDTO.setChangeCode(pointHistory.getChangeCode());
                    pointHistoryDTO.setChangeType(pointHistory.getChangeType());

                    return pointHistoryDTO;
                })
                .toList();

        log.info("myHomeselectPoints : " + myPointHistoryDTO);

        return myPointHistoryDTO;
    }

    //myHome 주문내역
    public  LinkedHashMap<Integer, List<OrderDetailDTO>> myHomeSelectOrder (String UserId){

        return ordersRepository.selectMyOrdersHome(UserId);

    }

    // my-review 작성
    public void writeReview(PdReviewDTO pdReviewDTO, MultipartFile revImage){
        // 1. 이미지 저장
        
        // 2. 리뷰정보 DB 저장

    }

    // my - wish 조회
    public PageResponseDTO selectUserWish(String userId, PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("no");
        Page<Tuple> userWish = wishRepository.selectUserWish(userId, pageRequestDTO, pageable);

        List<WishDTO> wishDTOList = userWish.getContent().stream()
                .map(tuple -> {
                    Wish wish = tuple.get(0, Wish.class);
                    String prodName = tuple.get(1, String.class);
                    Integer prodPrice = tuple.get(2, Integer.class);
                    String thumb190 = tuple.get(3, String.class);
                    WishDTO wishDTO = modelMapper.map(wish, WishDTO.class);
                    wishDTO.setProdName(prodName);
                    wishDTO.setProdPrice(prodPrice);
                    wishDTO.setThumb190(thumb190);
                    return wishDTO;
                        }
                    ).toList();

        log.info("wishDTOList : " + wishDTOList);

        List<WishDTO> resultWishDTO = new ArrayList<>();
        for (WishDTO eachWish : wishDTOList) {
            if (eachWish.getOptNo() != 0) {
                eachWish.setOptName(wishRepository.selectProdOption(eachWish.getOptNo()));
            }else {
                eachWish.setOptName("");
            }
            resultWishDTO.add(eachWish);
        }

        int total = (int) userWish.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(resultWishDTO)
                .total(total)
                .build();
    }
}
