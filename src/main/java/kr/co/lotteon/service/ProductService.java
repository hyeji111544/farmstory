package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProdOptionRepository prodOptionRepository;
    private final ProdOptDetailRepository prodOptDetailRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final WishRepository wishRepository;
    private final ModelMapper modelMapper;

    // 인덱스 페이지 상품 조회
    public List<ProductDTO> selectIndexProducts(String sort){
        List<Tuple> tuples = productRepository.selectIndexProducts(sort);

        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Tuple tuple : tuples) {
            Product product = tuple.get(0, Product.class);
            Productimg productImg = tuple.get(1, Productimg.class);
            if (product != null) {
                ProductDTO productDTO = new ProductDTO();
                // Product 정보를 ProductDTO로 매핑
                modelMapper.map(product, productDTO);
                // Productimg 정보를 ProductDTO에 설정
                if (productImg != null) {
                    productDTO.setThumb230(productImg.getThumb230());
                }
                // 리스트에 ProductDTO 추가
                productDTOs.add(productDTO);
            }
        }

        return productDTOs;
    }

    // 카테고리 눌러서 상품 조회
    public ProductPageResponseDTO selectProductsByCate(ProductPageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("prodNo");
        Page<Tuple> pageProd = productRepository.selectProductsByCate(pageRequestDTO, pageable);
        log.info("selectProdsByCate...."+pageProd.toString());
        List<ProductDTO> products = pageProd.getContent().stream()
                .map(tuple -> {
                    Product product = tuple.get(0, Product.class);
                    Productimg productImg = tuple.get(1, Productimg.class);

                    // Productimg에서 썸네일 정보를 가져와서 ProductDTO에 설정
                    if (productImg != null) {
                        product.setThumb190(productImg.getThumb190());
                    }

                    return modelMapper.map(product, ProductDTO.class);
                })
                .toList();

        int total = (int) pageProd.getTotalElements();
        return ProductPageResponseDTO.builder()
                .productPageRequestDTO(pageRequestDTO)
                .dtoList(products)
                .total(total)
                .build();
    }

    // 상품 상세보기 페이지로 이동
    public ProductDTO selectProduct(int prodNo) {
        Tuple tuple = productRepository.selectProduct(prodNo);

        Product product = tuple.get(0, Product.class);
        if (product != null) {
            product.setProdHit(product.getProdHit()+1);
            log.info("productHit....!: "+product.toString());
            product = productRepository.save(product);
            Productimg productImg = tuple.get(1, Productimg.class);
            if (productImg != null) {
                product.setThumb230(productImg.getThumb230());
                product.setThumb456(productImg.getThumb456());
                product.setThumb940(productImg.getThumb940());
            }
        }
        return modelMapper.map(product, ProductDTO.class);
    }

    // 상품 위시리스트 등록
    public List<Wish> insertWish(List<Wish> wishes){
        List<Wish> savedWishes = new ArrayList<>();
        for (Wish wish : wishes) {
            log.info("insertWishAtService....:" + wish.toString());
            Wish savedWish = wishRepository.save(wish);
            savedWishes.add(savedWish);
        }
        return savedWishes;
    }

    // 상품 장바구니 등록을 위한 장바구니 번호 조회
    public int findCartNoByUserId(String userId){
        Optional<Cart> optCart = cartRepository.findCartNoByUserId(userId);

        if (optCart.isPresent()) {
            Cart cart = optCart.get();
            return cart.getCartNo();
        }
        return 0;
    }

    // 상품 장바구니 등록
    public List<CartProduct> insertCart(List<CartProduct> cartProducts){
        List<CartProduct> savedCartProducts = new ArrayList<>();
        for (CartProduct cartProduct : cartProducts) {
            log.info("insertCart....:" + cartProduct.toString());
            CartProduct savedCart = cartProductRepository.save(cartProduct);
            savedCartProducts.add(savedCart);
        }

        return savedCartProducts;
    }


    // 상품 옵션 조회
    public ResponseOptionDTO selectProductOption(int prodNo){
        return prodOptionRepository.selectProductOption(prodNo);
    };

    // 장바구니 조회
    public List<CartProduct> findCartProdNo(int cartNo){

        List<CartProduct> optCart = cartProductRepository.findByCartNo(cartNo);
        log.info("findCartProdNo...."+optCart.toString());

        return optCart;

        // CartNo로 cartProduct에서 상품 조회 -> prodNo & optdetailNo 얻기
        // optDetail테이블에서 optdetailNo로 옵션 정보 얻기 & [optNo1, optNo2, optNo3] 얻기 [optValue1, optNo2, optNo3]
        // product 테이블에서 prodNo로 상품 정보 조회 & 상품 이미지

        // optNo1, optNo2, optNo3으로 option테이블에서 옵션 이름 조회

        // 


    }
}