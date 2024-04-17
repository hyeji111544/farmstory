package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.service.ProductService;
import kr.co.lotteon.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;
    private final ProductService productService;

    // 관리자 인덱스페이지 이동
    @GetMapping("/admin/index")
    public String adminIndex(){

        return "/admin/index";
    }

// admin - config //
    // 관리자 설정 배너 이동
    @GetMapping("/admin/config/banner")
    public String configBanner(){

        return "/admin/config/banner";
    }

    // 관리자 설정 정보 이동
    @GetMapping("/admin/config/info")
    public String configInfo(){

        return "/admin/config/info";
    }

// admin - product //
    // 관리자 상품 목록 이동
    @GetMapping("/admin/product/list")
    public String productList(Model model, ProductPageRequestDTO productPageRequestDTO){

        ProductPageResponseDTO pageResponseDTO = null;

        // productPageRequestDTO가 null인 경우, 기본값으로 객체 생성
        if (productPageRequestDTO == null) {
            productPageRequestDTO = new ProductPageRequestDTO();
        }

        // productPageRequestDTO의 keyword 속성이 null인 경우, 전체 상품 목록 조회
        if (productPageRequestDTO.getKeyword() == null) {
            pageResponseDTO = productService.selectProductsForAdmin(productPageRequestDTO);
        } else {
            // keyword 속성이 있는 경우, 키워드를 포함하는 상품 목록 조회
            //pageResponseDTO = productService.searchProductsForAdmin(productPageRequestDTO);
        }

        model.addAttribute(pageResponseDTO);
        log.info("here....!!!"+pageResponseDTO);

        return "/admin/product/list";
    }

    // 관리자 상품 등록 이동
    @GetMapping("/admin/product/register")
    public String productRegister(){

        return "/admin/product/register";
    }

    // 관리자 상품등록시 대분류에 따른 중분류 자동출력
    @PutMapping("/admin/product/cate")
    public ResponseEntity<?> changeCate(@RequestBody Cate02DTO cate02DTO){
        String cate01No = cate02DTO.getCate01No();
        try {
            ResponseEntity<List<Cate02DTO>> selectedCate02 = adminService.selectCate02(cate01No);
            List<Cate02DTO> cate02DTOList = selectedCate02.getBody();

            return ResponseEntity.ok().body(cate02DTOList);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }

   // 관리자 상품 등록
    @PostMapping("/admin/product/register")
    public String registerProduct(ProductDTO productDTO, ProductimgDTO productimgDTO){
        log.info(productDTO.toString());
        log.info(productimgDTO.toString());
        productService.registerProduct(productDTO, productimgDTO);

        return "redirect:/admin/product/list";
    }

}
