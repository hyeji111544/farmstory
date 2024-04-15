package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    //장바구니 이동
    @GetMapping("/product/cart/list")
    public String cartList(){
        return "/product/cart/list";
    }

}
