package kr.co.lotteon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SellerController {

    // 판매자 관리페이지 - 홈 이동
    @GetMapping("/seller/index")
    public String sellerIndex(){


        return "/seller/index";
    }

}
