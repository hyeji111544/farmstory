package kr.co.lotteon.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import kr.co.lotteon.entity.CartProduct;
import kr.co.lotteon.repository.CartProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartProductRepository cartProductRepository;

    @Transactional
    public ResponseEntity<?> deleteProducts(HttpServletRequest req, int cartProdNo){

        log.info("cartProdNo : " + cartProdNo);

        Optional<CartProduct> optProducts = cartProductRepository.findById(cartProdNo);
        log.info("deleteProdAtService..1:"+optProducts);

        if (optProducts.isPresent()){
            cartProductRepository.deleteById(cartProdNo);

            log.info("deleteProdAtService..2:"+cartProdNo);
            return ResponseEntity
                    .ok()
                    .body(optProducts.get());

        }else {
            log.info("deleteProdAtService..3:");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("not found");
        }

    }

    public void updateCartProdCount(Map<String, List<Integer>> map){
        List<Integer> cartProdNoList = map.get("cartProdNo");
        List<Integer> countList = map.get("count");

        for (int i = 0; i < cartProdNoList.size(); i++) {
            Integer cartProdNo = cartProdNoList.get(i);
            Integer newCount = countList.get(i);

            // cartProdNo에 해당하는 CartProduct를 조회
            Optional<CartProduct> optCartProduct = cartProductRepository.findById(cartProdNo);

            if (optCartProduct.isPresent()) {
                // 조회된 CartProduct의 count를 업데이트
                CartProduct cartProduct = optCartProduct.get();
                cartProduct.setCount(newCount);
                cartProductRepository.save(cartProduct);
            }
        }
    }
}
