package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Coupons;
import kr.co.lotteon.entity.PointHistory;
import kr.co.lotteon.repository.UserPointRepository;
import kr.co.lotteon.repository.pointHistoryRepository;
import kr.co.lotteon.service.MyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyController {

    private final MyService myService;
    private final UserPointRepository userPointRepository;
    private final PasswordEncoder passwordEncoder;

    //마이페이지-홈 이동
    @GetMapping("/my/home")
    public String myHome(Model model, String UserId){

    log.info("My home" +UserId);

        // 회원 정보



        // 최근주문내역
        LinkedHashMap<Integer, List<OrderDetailDTO>> myOrder = myService.myHomeSelectOrder(UserId);
        model.addAttribute("myOrder", myOrder);


        // 포인트적립내역
        List<PointHistoryDTO> myPoint = myService.myHomeselectPoints(UserId);
        model.addAttribute("myPoint", myPoint);

        log.info("myPoint : " + myPoint);
        // 상품평


        // 문의내역




        return "/my/home";

    }

    //마이페이지-쿠폰 이동
    @GetMapping("/my/coupon")
    public String myCoupon(String UserId, Model model){

        List<Coupons> haveCoupons = myService.selectCoupons(UserId);
        model.addAttribute("haveCoupons", haveCoupons); // Map<String ,List<Coupons>>  / Map<String, int>
        return "/my/coupon";
    }

    //마이페이지-정보 이동
    @GetMapping("/my/info")
    public String myInfo(Model model, String userId){
        Map<String, Object> result = myService.selectUserInfo(userId);
        UserDTO userDTO = (UserDTO) result.get("user");
        SellerDTO sellerDTO = (SellerDTO) result.get("seller");

        log.info("userDTO : " + userDTO);
        log.info("sellerDTO : " + sellerDTO);

        model.addAttribute("userDTO", userDTO);
        model.addAttribute("sellerDTO", sellerDTO);
        return "/my/info";
    }

    // 마이페이지 - 판매자 이름 수정
    @PostMapping("/my/updateSellerName")
    public ResponseEntity<?> myInfoUpdateSellerName(@RequestBody Map<String, String> requestData){
        String userId = requestData.get("userId");
        String sellerName = requestData.get("sellerName");
        log.info("userId : " + userId);
        log.info("sellerName : " + sellerName);
        return myService.myInfoUpdateSellerName(userId, sellerName);
    }
    // 마이페이지 - 판매자 연락처 수정
    @PostMapping("/my/updateSellerHp")
    public ResponseEntity<?> myInfoUpdateSellerHp(@RequestBody Map<String, String> requestData){
        String userId = requestData.get("userId");
        String sellerHp = requestData.get("sellerHp");
        log.info("userId : " + userId);
        log.info("sellerHp : " + sellerHp);
        return myService.myInfoUpdateSellerHp(userId, sellerHp);
    }
    // 마이페이지 - 판매자 팩스번호 수정
    @PostMapping("/my/updateFax")
    public ResponseEntity<?> myInfoUpdateFax(@RequestBody Map<String, String> requestData){
        String userId = requestData.get("userId");
        String fax = requestData.get("fax");
        log.info("userId : " + userId);
        log.info("fax : " + fax);
        return myService.myInfoUpdateFax(userId,fax);
    }

    // 마이페이지 - 연락처 수정
    @PostMapping("/my/updateHp")
    public ResponseEntity<?> myInfoUpdateHp(@RequestBody Map<String, String> requestData){

        String userId = requestData.get("userId");
        String userHp = requestData.get("userHp");
        log.info("userId : " + userId);
        log.info("userHp : " + userHp);
        return myService.myInfoUpdateHp(userId, userHp);
    }

    // 마이페이지 - 이메일 수정
    @PostMapping("/my/updateEmail")
    public ResponseEntity<?> myInfoUpdateEmail(@RequestBody Map<String, String> requestData){

        String userId = requestData.get("userId");
        String userEmail = requestData.get("userEmail");
        log.info("userId : " + userId);
        log.info("userEmail : " + userEmail);
        return myService.myInfoUpdateEmail(userId, userEmail);
    }

    // 마이페이지 - 비밀번호 수정
    @PostMapping("/my/updatePw")
    public ResponseEntity<?> myInfoUpdatePw(@RequestBody Map<String, String> requestData){

        String userId = requestData.get("userId");
        String userPw = requestData.get("userPw");
        log.info("userId : " + userId);
        log.info("userPw : " + userPw);
        String encodedPassword = passwordEncoder.encode(userPw);
        return myService.myInfoUpdatePw(userId, encodedPassword);
    }
    // 마이페이지 - 주소 수정
    @PostMapping("/my/updateAddr")
    public ResponseEntity<?> myInfoUpdateAddr(@RequestBody UserDTO userDTO){
        log.info("here...! :" + userDTO);
        return myService.myInfoUpdateAddr(userDTO);
    }
    // 마이페이지 - 회원 탈퇴
    @PostMapping("/my/updateRole")
    public ResponseEntity<?> myInfoUpdateRole(@RequestBody Map<String, String> requestData){

        String userId = requestData.get("userId");
        String userRole = requestData.get("userRole");
        return myService.myInfoUpdateRole(userId,userRole);
    }

    //마이페이지-주문내역 이동
    @GetMapping("/my/order")
    public String myOrder(String userId, Model model, MyOrderPageRequestDTO myOrderPageRequestDTO){
        log.info(userId);
        log.info(myOrderPageRequestDTO.toString());

        MyOrderPageResponseDTO MyOrderDTOList = myService.selectOrders(userId, myOrderPageRequestDTO);
        model.addAttribute("MyOrderDTOList", MyOrderDTOList);

        return "/my/order";
    }

    //마이페이지-포인트 이동
    @GetMapping("/my/point")
    public String myPoint(String userId,
                          Model model,
                          PageRequestDTO pageRequestDTO){

        PageResponseDTO pageResponseDTO = myService.selectPoints(userId, pageRequestDTO);
        //userPointRepository.selectPoints(userId, pageRequestDTO);

        log.info("pageResponseDTO : " +pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/my/point";
    }

    //마이페이지-QnA 이동
    @GetMapping("/my/qna")
    public String myQna(){
        return "/my/qna";
    }

    //마이페이지-리뷰 작성
    @PostMapping("/my/writeReview")
    public String writeReview(Authentication authentication, PdReviewDTO pdReviewDTO, MultipartFile revImage){
        pdReviewDTO.setUserId(authentication.getName());

        log.info("pdReviewDTO : " + pdReviewDTO);
        log.info("revImage : " + revImage);

        myService.writeReview(pdReviewDTO, revImage);

        return null;
    }

    //마이페이지-리뷰 이동
    @GetMapping("/my/review")
    public String myReview(){
        return "/my/review";
    }


}
