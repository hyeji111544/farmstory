package kr.co.lotteon.service.admin;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteon.entity.CartProduct;
import kr.co.lotteon.repository.CartProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartProductRepository cartProductRepository;

    public ResponseEntity<?> deleteProducts(HttpServletRequest req, int cartProdNo){
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
}
