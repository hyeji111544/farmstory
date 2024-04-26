package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import kr.co.lotteon.dto.CartInfoDTO;
import kr.co.lotteon.entity.CartProduct;
import kr.co.lotteon.entity.ProdOptDetail;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.entity.Productimg;
import kr.co.lotteon.repository.CartProductRepository;
import kr.co.lotteon.repository.ProdOptDetailRepository;
import kr.co.lotteon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartProductRepository cartProductRepository;
    private final ProdOptDetailRepository prodOptDetailRepository;
    private final ProductRepository productRepository;

    // 장바구니 조회
    public Map<String, List<CartInfoDTO>> findCartProdNo(int cartProdNo) {

        Optional<CartProduct> carts = cartProductRepository.findById(cartProdNo);

        int optNo = carts.get().getOptNo();
        int prodNo = carts.get().getProdNo();
        //log.info("findCartProdNo....3: " + prodNo);

        ProdOptDetail optProdOptDetail = prodOptDetailRepository.selectOptDetailWihtName(optNo);
        //log.info("findCartProdNo....4: " + optProdOptDetail);

        Product product = null;
        try {
            Tuple productTuple = productRepository.selectProductById(prodNo);
            //log.info("findCartProdNo....5: " + productTuple);

            if (productTuple != null) {
                product = productTuple.get(0, Product.class);
                Productimg productImg = productTuple.get(1, Productimg.class);
                if (productImg != null) {
                    product.setThumb190(productImg.getThumb190());
                }
                //log.info("findCartProdNo....6: " + product);

            }else {
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }

        List<CartInfoDTO> cartInfoDTOs = new ArrayList<>();
        try {
            CartInfoDTO cartInfoDTO = new CartInfoDTO();
            cartInfoDTO.setProdNo(product.getProdNo());
            cartInfoDTO.setProdName(product.getProdName());
            cartInfoDTO.setProdInfo(product.getProdInfo());
            cartInfoDTO.setProdDiscount(product.getProdDiscount());
            cartInfoDTO.setProdPrice(product.getProdPrice());
            cartInfoDTO.setProdCompany(product.getProdCompany()); // 회사명 추가
            cartInfoDTO.setProdDeliveryFee(product.getProdDeliveryFee()); // 배송비 추가
            if (product.getThumb190() != null) {
                cartInfoDTO.setThumb190(product.getThumb190());
            }
            if (optProdOptDetail != null) {
                cartInfoDTO.setOptValue1(optProdOptDetail.getOptValue1());
                cartInfoDTO.setOptValue2(optProdOptDetail.getOptValue2());
                cartInfoDTO.setOptValue3(optProdOptDetail.getOptValue3());
                cartInfoDTO.setOptStock(optProdOptDetail.getOptStock());
                cartInfoDTO.setOptPrice(optProdOptDetail.getOptPrice());
            }
            cartInfoDTO.setCount(carts.get().getCount());
            cartInfoDTO.setCartProdNo(carts.get().getCartProdNo());

            cartInfoDTOs.add(cartInfoDTO);
            log.info("product...:" + cartInfoDTOs);

        }catch (Exception e){
            log.info(e.getMessage());
        }


        Map<String, List<CartInfoDTO>> companyMap = new HashMap<>();

        for (CartInfoDTO info : cartInfoDTOs) {
            // 회사명이 이미 Map에 있는지 확인
            // contailnsKey = Map 에 지정된 key가 있는지 확인하는데 사용
            if (companyMap.containsKey(info.getProdCompany())) {

                // 이미 있는 경우, 해당 회사명의 List를 가져와서 DTO를 추가
                List<CartInfoDTO> existingCompany = companyMap.get(info.getProdCompany());
                existingCompany.add(info);

            } else {

                // 없는 경우, 새로운 List를 생성하고 DTO를 추가한 후 Map에 추가
                List<CartInfoDTO> newList = new ArrayList<>();
                newList.add(info);
                companyMap.put(info.getProdCompany(), newList);
            }
        }

        // 결과 출력
        for (Map.Entry<String, List<CartInfoDTO>> entry : companyMap.entrySet()) {
            System.out.println("회사명: " + entry.getKey());
            System.out.println("상품 리스트: " + entry.getValue());
            System.out.println();
        }

        return companyMap;
    }

}
