package kr.co.lotteon.controller;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.service.ProdCateService;
import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final ProdCateService prodCateService;
    private final ProductService productService;

    /* 
        메인페이지
         1. product 출력
         2. 
         3. 
     */
    @GetMapping("/")
    public String index(Model model){

        // index 페이지 product 출력
        String prodDiscount = "prodDiscount";
        String prodSold     = "prodSold";
        String prodRdate    = "prodRdate";
        String prodScore    = "prodScore";
        String prodHit      = "prodHit";
        List<ProductDTO> discountList = productService.selectIndexProducts(prodDiscount);
        List<ProductDTO> soldList = productService.selectIndexProducts(prodSold);
        List<ProductDTO> rDateList = productService.selectIndexProducts(prodRdate);
        List<ProductDTO> scoreList = productService.selectIndexProducts(prodScore);
        List<ProductDTO> hitList = productService.selectIndexProducts(prodHit);

        model.addAttribute("discount", discountList);
        model.addAttribute("sold", soldList);
        model.addAttribute("rDate", rDateList);
        model.addAttribute("score", scoreList);
        model.addAttribute("hit", hitList);
        return "/index";
    }
}
