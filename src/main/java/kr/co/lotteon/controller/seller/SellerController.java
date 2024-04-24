package kr.co.lotteon.controller.seller;

import kr.co.lotteon.dto.ProductPageRequestDTO;
import kr.co.lotteon.dto.ProductPageResponseDTO;
import kr.co.lotteon.dto.SellerInfoDTO;
import kr.co.lotteon.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SellerController {

    private final SellerService sellerService;

    /*
        판매자 관리페이지 - 홈 이동
        - orderDetail 테이블에서 prodSeller로 상품 검색
            - count(*) = 주문 건수
            - sum(detailPrice) = 총 주문 금액
            - detailStatus = [입금대기, 배송준비, 취소요청, 교환요청, 반품요청]
     */
    @GetMapping("/seller/index")
    public String sellerIndex(String prodSeller, Model model){
        SellerInfoDTO sellerInfoDTO = sellerService.selectSellerInfo(prodSeller);
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

}
