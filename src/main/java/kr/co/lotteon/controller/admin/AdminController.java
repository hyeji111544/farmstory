package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.ProdOptDetail;
import kr.co.lotteon.service.admin.AdminProductService;
import kr.co.lotteon.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;
    private final AdminProductService adminproductService;

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
            pageResponseDTO = adminproductService.selectProductsForAdmin(productPageRequestDTO);
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
        adminproductService.registerProduct(productDTO, productimgDTO);

        return "redirect:/admin/product/register";
    }

    // 관리자 상품 옵션 등록 페이지 이동
    @GetMapping("/admin/product/option")
    public String registerProductOption(Model model, @RequestParam("prodNo") int prodNo){
        Map<String, Object> resultMap = adminproductService.selectProductOption(prodNo);
        ProductDTO productDTO = (ProductDTO) resultMap.get("productDTO");
        List<ProdOptionDTO> optionDTOList = (List<ProdOptionDTO>) resultMap.get("optionDTOList");

        log.info("productDTO : " + productDTO);
        log.info("optionDTOList : " + optionDTOList);

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("optionDTOList", optionDTOList);

        return "/admin/product/option";
    }

    /*
        상품 옵션 등록
        - 사용자가 입력한 상품 옵션 (최대 3개까지) / prodOption1,2,3
            - [상품 번호, 옵션 이름, 옵션 값]
            ex) [ [22, 사이즈, small] / [22, 사이즈, large] / [22, 색상, black] / [22, 색상, blue] ... ]

        - 사용자가 입력한 상품 옵션의 연관관계(prodOptDetail)
            - [번호, 옵션1 값, 옵션2 값, 옵션3 값, 가격, 재고]
            ex) [ [1, small, black, 남성, 2000원, 100개] / [1, small, black, 여성, 3000원, 100개] ... ]

        - body에서 json데이터 꺼낸 후 각각 이중 List로 변환
            - 자바스크립트에서 배열로 데이터를 전송했기 떄문에 이중 List로 변환

        - 각각의  이중 List들을 List<DTO>로 변환 후 service로 전달
     */
    @PostMapping("/admin/product/regOption")
    public ResponseEntity<?> registerOption(@RequestBody Map<String, Object> requestData){
        ArrayList<ArrayList<String>> prodOption1List = (ArrayList<ArrayList<String>>) requestData.get("prodOption1");
        ArrayList<ArrayList<String>> prodOption2List = (ArrayList<ArrayList<String>>) requestData.get("prodOption2");
        ArrayList<ArrayList<String>> prodOption3List = (ArrayList<ArrayList<String>>) requestData.get("prodOption3");
        ArrayList<ArrayList<String>> prodOptionDTOList = (ArrayList<ArrayList<String>>) requestData.get("prodOptionDTO");
        int prodNo = Integer.parseInt(prodOption1List.get(0).get(0));

        // option1 List<ProdOptionDTO>로 변환
        List<ProdOptionDTO> optionDTO1 = new ArrayList<>();
        for (ArrayList<String> eachOption : prodOption1List){
            ProdOptionDTO optionDTO = new ProdOptionDTO();
            optionDTO.setProdNo(Integer.parseInt(eachOption.get(0)));
            optionDTO.setOptName(eachOption.get(1));
            optionDTO.setOptValue(eachOption.get(2));
            optionDTO1.add(optionDTO);
        }
        log.info("optionDTO1 : " + optionDTO1);

        // option2 List<ProdOptionDTO>로 변환
        List<ProdOptionDTO> optionDTO2 = new ArrayList<>();
        for (ArrayList<String> eachOption : prodOption2List){
            ProdOptionDTO optionDTO = new ProdOptionDTO();
            optionDTO.setProdNo(Integer.parseInt(eachOption.get(0)));
            optionDTO.setOptName(eachOption.get(1));
            optionDTO.setOptValue(eachOption.get(2));
            optionDTO2.add(optionDTO);
        }
        log.info("optionDTO2 : " + optionDTO2);

        // option3 List<ProdOptionDTO>로 변환
        List<ProdOptionDTO> optionDTO3 = new ArrayList<>();
        for (ArrayList<String> eachOption : prodOption3List){
            ProdOptionDTO optionDTO = new ProdOptionDTO();
            optionDTO.setProdNo(Integer.parseInt(eachOption.get(0)));
            optionDTO.setOptName(eachOption.get(1));
            optionDTO.setOptValue(eachOption.get(2));
            optionDTO3.add(optionDTO);
        }
        log.info("optionDTO3 : " + optionDTO3);

        // prodOptDetail List<prodOptDetailDTO>로 변환
        List<prodOptDetailDTO> optDetailDTOS = new ArrayList<>();
        for (ArrayList<String> eachDetail : prodOptionDTOList){
            prodOptDetailDTO OptDetailDTO = new prodOptDetailDTO();
            OptDetailDTO.setProdNo(prodNo);
            OptDetailDTO.setOptDetailNo(Integer.parseInt(eachDetail.get(0)));
            OptDetailDTO.setOptPrice(Integer.parseInt(eachDetail.get(4)));
            OptDetailDTO.setOptStock(Integer.parseInt(eachDetail.get(5)));
            optDetailDTOS.add(OptDetailDTO);
        }
        log.info("optDetailDTOS : " + optDetailDTOS);

        return adminproductService.registerProdOption(optionDTO1, optionDTO2, optionDTO3, optDetailDTOS);
    }



}
