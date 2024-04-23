package kr.co.lotteon.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteon.service.admin.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

    //제품 삭제
    @PutMapping("cart/{selectedCartList}")
    public ResponseEntity<?> deleteCart(@RequestBody Map<String, List<Integer>> map, HttpServletRequest req){

        List<Integer> cartProNoList = map.get("cartProdNo");
        Map<String, String> response = new HashMap<>();
        try {
            for (Integer cartProdNo : cartProNoList){
                cartService.deleteProducts(req, cartProdNo);
                log.info("deleteProd..!"+cartProdNo);
                response.put("message", "상품이 성공적으로 삭제되었습니다.");
            }
            return ResponseEntity
                    .ok()
                    .body(response);
        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "상품 삭제에 실패했습니다.");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorResponse);
        }

    }

}



