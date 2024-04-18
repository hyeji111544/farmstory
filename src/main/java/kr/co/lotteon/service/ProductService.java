package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.ProductPageRequestDTO;
import kr.co.lotteon.dto.ProductPageResponseDTO;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.entity.Productimg;
import kr.co.lotteon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

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
        Productimg productImg = tuple.get(1, Productimg.class);
        if (productImg != null) {
            product.setThumb230(productImg.getThumb230());
            product.setThumb456(productImg.getThumb456());
            product.setThumb940(productImg.getThumb940());
        }

        return modelMapper.map(product, ProductDTO.class);

    }
}