package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    // 상품 목록 이동
    @GetMapping("/product/list")
    public String prodList(){
        return "/product/list";
    }

    // 상품 상세보기 이동
    @GetMapping("/product/view")
    public String prodView(){
        return "/product/view";
    }

    // 상품 검색 이동
    @GetMapping("/product/search")
    public String prodSearch(){
        return "/product/search";
    }

    // 장바구니 이동
    @GetMapping("/product/cart")
    public String prodCart(){
        return "/product/cart";
    }

    // 상품 주문 이동
    @GetMapping("/product/order")
    public String prodOrder(){
        return "/product/order";
    }

    // 상품 주문 완료 이동
    @GetMapping("/product/complete")
    public String prodComplete(){
        return "/product/complete";
    }

}
