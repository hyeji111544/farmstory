package kr.co.lotteon.controller;

import kr.co.lotteon.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SellerController {

    private final SellerService sellerService;

    /*
        판매자 관리페이지 - 홈 이동
        - orderDetail 테이블에서 prodSeller로 상품 검색
            - count(*) = 주문 건수
            - sum(detailPrice) = 총 주문 금액
            - detailStatus = [입금대기, 배송준비, 취소요청, 교환요청, 반품요청]
     */
    @GetMapping("/seller/index")
    public String sellerIndex(String prodSeller){
        sellerService.selectSellerInfo(prodSeller);

        return "/seller/index";
    }

}
