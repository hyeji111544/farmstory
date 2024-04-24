package kr.co.lotteon.controller.seller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import kr.co.lotteon.dto.ProdQnaDTO;
import kr.co.lotteon.dto.ProdQnaNoteDTO;
import kr.co.lotteon.service.seller.SellerQnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    // 판매자 관리페이지 - QNA View
    @GetMapping("/seller/cs/qna/view")
    public String sellerQnaView(int qnaNo, Model model, PageRequestDTO pageRequestDTO){

        Map<String, Object> resultQna = sellerQnaService.selectSellerQnaView(qnaNo);

        ProdQnaDTO prodQnaDTO = (ProdQnaDTO) resultQna.get("prodQnaDTO");
        List<ProdQnaNoteDTO> prodQnaNoteList = (List<ProdQnaNoteDTO>) resultQna.get("prodQnaNoteList");

        model.addAttribute("prodQnaDTO",prodQnaDTO);
        model.addAttribute("prodQnaNoteList",prodQnaNoteList);

        return "/seller/cs/qna/view";
    }

    // 판매자 관리페이지 - QNA View - 상품 문의 답글 등록
    @PostMapping("/seller/cs/qna/insertQnaNote")
    public ResponseEntity<?> insertQnaNote(HttpSession httpSession, @RequestBody Map<String, String> requestData) {
        String content = requestData.get("qnaComment");
        String prodQnaNo = requestData.get("prodQnaNo");
        String sellerNo = requestData.get("sellerNo");
        ProdQnaNoteDTO prodQnaNoteDTO = new ProdQnaNoteDTO();
        prodQnaNoteDTO.setContent(content);
        prodQnaNoteDTO.setProdQnaNo(Integer.parseInt(prodQnaNo));
        prodQnaNoteDTO.setSellerNo(sellerNo);
        prodQnaNoteDTO.setCQnaDate(LocalDateTime.now());

        return sellerQnaService.insertQnaNote(prodQnaNoteDTO);
    }
}
