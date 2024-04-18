package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    // 상품 목록 이동
    @GetMapping("/product/list")
    public String prodList(@RequestParam("cateCode") String cateCode, ProductPageRequestDTO productPageRequestDTO, Model model){
        log.info("selectProductsCate.........:"+cateCode);
        ProductPageResponseDTO pageResponseDTO;
        productPageRequestDTO.setCateCode(cateCode);
        pageResponseDTO = productService.selectProductsByCate(productPageRequestDTO);
        //서비스 호출 . cate01, cate 02 조회 해서 가져와서 model
        String setSortType = productPageRequestDTO.getSort();
        model.addAttribute("setSortType", setSortType);
        model.addAttribute(pageResponseDTO);
        // model.add
        log.info("selectProductsByCateCode: " + pageResponseDTO);
        return "/product/list";
    }

    // 상품 상세보기 이동
    @GetMapping("/product/view")
    public String prodView(@RequestParam("prodNo") int prodNo, Model model){
        log.info("prodView....!!!"+prodNo);
        ProductDTO productDTO = productService.selectProduct(prodNo);
        model.addAttribute("product", productDTO);
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
