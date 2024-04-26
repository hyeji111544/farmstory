package kr.co.lotteon.controller.seller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.service.admin.AdminProductService;
import kr.co.lotteon.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.AuthProvider;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SellerController {

    private final SellerService sellerService;
    private final AdminProductService adminproductService;


    /*
        판매자 관리페이지 - 홈 이동
        - orderDetail 테이블에서 prodSeller로 상품 검색
            - count(*) = 주문 건수
            - sum(detailPrice) = 총 주문 금액
            - detailStatus = [입금대기, 배송준비, 취소요청, 교환요청, 반품요청]
     */
    @GetMapping("/seller/index")
    public String sellerIndex(HttpSession session, Authentication authentication, Model model){
        String UserId = authentication.getName();
        SellerInfoDTO sellerInfoDTO = sellerService.selectSellerInfo(session, UserId);

        model.addAttribute("sellerInfoDTO", sellerInfoDTO);
        return "/seller/index";
    }

    // 판매자 관리페이지 - 상품목록 - 상품관리
    @GetMapping("/seller/product/list")
    public String sellerProdList(String prodSeller, Model model, ProductPageRequestDTO productPageRequestDTO){

        log.info("productPageRequestDTO.getKeyword() : " + productPageRequestDTO.getKeyword());
        log.info("productPageRequestDTO.getType() : " + productPageRequestDTO.getType());
        ProductPageResponseDTO pageResponseDTO = null;

        if(productPageRequestDTO.getKeyword() == null) {
            // 판매자의 전체 상품 목록 조회
            pageResponseDTO = sellerService.selectProductForSeller(prodSeller, productPageRequestDTO);
        }else {
            log.info("하이");
            // 판매자의 검색 상품 목록 조회
            pageResponseDTO = sellerService.searchProductForSeller(prodSeller, productPageRequestDTO);
        }
        log.info("pageResponseDTO : " + pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/seller/product/list";
    }


    // 판매자 상품 등록 이동
    @GetMapping("/seller/product/register")
    public String productRegister(){

        return "/seller/product/register";
    }

    // 판매자 상품 등록
    @PostMapping("/seller/product/register")
    public String registerProduct(ProductDTO productDTO, ProductimgDTO productimgDTO){
        log.info(productDTO.toString());
        log.info(productimgDTO.toString());
        adminproductService.registerProduct(productDTO, productimgDTO);

        return "redirect:/seller/product/register";
    }

    // 판매자 상품 옵션 등록 페이지 이동
    @GetMapping("/seller/product/option")
    public String registerProductOption(Model model, @RequestParam("prodNo") int prodNo){
        Map<String, Object> resultMap = adminproductService.selectProductOption(prodNo);
        ProductDTO productDTO = (ProductDTO) resultMap.get("productDTO");
        List<ProdOptionDTO> optionDTOList = (List<ProdOptionDTO>) resultMap.get("optionDTOList");
        List<prodOptDetailDTO> prodOptDetailDTOList = (List<prodOptDetailDTO>) resultMap.get("prodOptDetailDTOList");

        log.info("productDTO : " + productDTO);
        log.info("optionDTOList : " + optionDTOList);

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("optionDTOList", optionDTOList);
        model.addAttribute("optionDetail", prodOptDetailDTOList);

        return "/seller/product/option";
    }

}
