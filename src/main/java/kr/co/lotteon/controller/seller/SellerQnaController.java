package kr.co.lotteon.controller.seller;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.service.seller.SellerQnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SellerQnaController {

    private final SellerQnaService sellerQnaService;

    // 판매자 관리페이지 - QNA List
    @GetMapping("/seller/cs/qna/list")
    public String sellerQnaList(String prodSeller, Model model, PageRequestDTO pageRequestDTO){

        PageResponseDTO prodQnaList = sellerQnaService.selectSellerQnaList(prodSeller, pageRequestDTO);

        model.addAttribute("prodQnaList", prodQnaList);
        log.info("prodQnaList : " + prodQnaList);

        return "/seller/cs/qna/list";
    }

}
