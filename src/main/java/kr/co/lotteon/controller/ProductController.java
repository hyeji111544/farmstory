package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.ProdOption;
import kr.co.lotteon.service.ProdCateService;
import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final ProdCateService prodCateService;

    // 상품 목록 이동
    @GetMapping("/product/list")
    public String prodList(@RequestParam("cateCode") String cateCode, ProductPageRequestDTO productPageRequestDTO, Model model){
        ProductPageResponseDTO pageResponseDTO;
        productPageRequestDTO.setCateCode(cateCode);
        pageResponseDTO = productService.selectProductsByCate(productPageRequestDTO);
        String setSortType = productPageRequestDTO.getSort();
        model.addAttribute("setSortType", setSortType);
        model.addAttribute(pageResponseDTO);
        String cate01 = "";
        String cate02 = "";
        String cate03 = "";
        // cateCode AA / 101 / A101
        if (cateCode.length() == 2){
            cate01 = cateCode.substring(0,2);
        }else if (cateCode.length() == 5) {
            cate01 = cateCode.substring(0,2);
            cate02 = cateCode.substring(2,5);
        }else if (cateCode.length() > 5) {
            cate01 = cateCode.substring(0,2);
            cate02 = cateCode.substring(2,5);
            cate03 = cateCode.substring(5,9);
        }
        log.info("cate01 : " + cate01);
        log.info("cate02 : " + cate02);
        log.info("cate03 : " + cate03);

        Map<String, String> resultMap = prodCateService.findCateName(cate01, cate02, cate03);
        log.info("resultMap : " + resultMap);
        model.addAttribute("resultMap", resultMap);


        return "/product/list";
    }

    // 상품 상세보기 이동
    @GetMapping("/product/view")
    public String prodView(@RequestParam("prodNo") int prodNo, Model model){
        log.info("prodView....!!!"+prodNo);
        ProductDTO productDTO = productService.selectProduct(prodNo);
        model.addAttribute("product", productDTO);

        // 상품 옵션 정보 조회
        ResponseOptionDTO responseOptionDTO = productService.selectProductOption(prodNo);
        log.info("responseOptionDTO : " + responseOptionDTO);
        model.addAttribute("OptionDTOs", responseOptionDTO);
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
