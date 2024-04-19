package kr.co.lotteon.controller;

import kr.co.lotteon.dto.Cate01DTO;
import kr.co.lotteon.dto.Cate02DTO;
import kr.co.lotteon.dto.Cate03DTO;
import kr.co.lotteon.service.ProdCateService;
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

    /* 
        메인페이지
         1. sideBar 카테고리 정보 조회
         2. 
         3. 
     */
    @GetMapping("/")
    public String index(Model model){
        
        // sideBar 카테고리 정보 조회
        Map<String, List<?>> cateMap = prodCateService.selectProdCate();
        List<Cate01DTO> cate01DTOs = (List<Cate01DTO>) cateMap.get("cate01DTOs");
        List<Cate02DTO> cate02DTOs = (List<Cate02DTO>) cateMap.get("cate02DTOs");
        List<Cate03DTO> cate03DTOs = (List<Cate03DTO>) cateMap.get("cate03DTOs");

        model.addAttribute("cate01DTOs", cate01DTOs);
        model.addAttribute("cate02DTOs", cate02DTOs);
        model.addAttribute("cate03DTOs", cate03DTOs);

        //

        return "/index";
    }
}
