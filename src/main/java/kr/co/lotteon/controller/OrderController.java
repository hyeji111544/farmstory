package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.service.CartService;
import kr.co.lotteon.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;

    // 결제 페이지 조회(장바구니-> 결제)
    @PostMapping("/product/orderDirect")
    public String maketBuyDirect(){

        return "/product/order";
    }
    // 결제 페이지 조회(장바구니-> 결제)
    @PostMapping("/product/order")
    public String maketBuy(@RequestParam("cartProdNo") List<Integer> cartProdNos,
                           @RequestParam("user") String userId, Model model){

        UserDTO userDTO = orderService.selectUser(userId);
        model.addAttribute("user", userDTO);
        
        Map<String, List<CartInfoDTO>> orderProducts = new HashMap<>();
        for (Integer cartProdNo : cartProdNos) {
            Map<String, List<CartInfoDTO>> cartInfos = orderService.findCartProdNo(cartProdNo);
            //orderProducts.putAll(cartInfos);
            for (Map.Entry<String, List<CartInfoDTO>> entry : cartInfos.entrySet()) {
                String company = entry.getKey();
                List<CartInfoDTO> cartInfoList = entry.getValue();
                if (orderProducts.containsKey(company)) {
                    // 이미 해당 회사가 있는 경우에만 추가
                    orderProducts.get(company).addAll(cartInfoList);
                } else {
                    // 해당 회사가 없는 경우 새로운 항목 추가
                    orderProducts.put(company, cartInfoList);
                }
            }
        }
        model.addAttribute("orderProducts", orderProducts);
        log.info("orderProducts" + orderProducts);
        return "/product/order";
    }

    @PostMapping("/product/order/checkout")
    public ResponseEntity<?> checkoutOrder(@RequestBody OrderInfoDTO orderInfoDTO){
        int orderNo = orderService.insertOrders(orderInfoDTO);
        log.info("orderList" + orderNo);

        return ResponseEntity.ok().body(orderNo);
    }

    // 상품 주문 완료 이동
    @GetMapping("/product/complete")
    public String prodComplete(@RequestParam("orders") int orderNo, Model model){

        Orders order = orderService.orderComplete(orderNo);
        List<CartInfoDTO> cartInfoDTOs = orderService.orderDetailComplete(orderNo);
        model.addAttribute("order", order);
        model.addAttribute("cartInfoDTOs",cartInfoDTOs);
        log.info("order: " + order);
        log.info("cartInfoDTOs: " + cartInfoDTOs);

        return "/product/complete";
    }
}
