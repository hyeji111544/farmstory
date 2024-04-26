package kr.co.lotteon.controller;

import kr.co.lotteon.dto.CartInfoDTO;
import kr.co.lotteon.service.CartService;
import kr.co.lotteon.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;

    // 결제 페이지 조회(장바구니-> 결제)
    @PostMapping("/product/order")
    public String maketBuy(@RequestParam("cartProdNo") List<Integer> cartProdNos,
                           @RequestParam("user") String userId, Model model){
        log.info("user" + userId);
        log.info("cartProdNo" + cartProdNos);
        for (Integer cartProdNo : cartProdNos) {
            log.info("cartProdNo" + cartProdNo);
            Map<String, List<CartInfoDTO>> cartInfos = orderService.findCartProdNo(cartProdNo);
            log.info("orderProducts" + cartInfos);
        }


        return "/product/order";
    }


}
