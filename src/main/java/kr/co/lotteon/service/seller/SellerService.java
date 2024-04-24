package kr.co.lotteon.service.seller;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.ProductPageRequestDTO;
import kr.co.lotteon.dto.ProductPageResponseDTO;
import kr.co.lotteon.dto.SellerInfoDTO;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;

    // 판매자 관리페이지 - 홈
    public SellerInfoDTO selectSellerInfo(String prodSeller){
        return sellerRepository.selectSellerInfo(prodSeller);
    }

    // 판매자 관리페이지 - 상품목록 - 상품관리 (전체 상품 목록)
    public ProductPageResponseDTO selectProductForSeller(String prodSeller, ProductPageRequestDTO productPageRequestDTO){

        Pageable pageable = productPageRequestDTO.getPageable("prodNo");

        Page<Tuple> pageProducts = sellerRepository.selectProductForSeller(prodSeller, productPageRequestDTO, pageable);

        List<ProductDTO> dtoList = pageProducts.getContent().stream()
                .map(tuple -> {
                    Product product = tuple.get(0, Product.class);
                    String thumb190 = tuple.get(1, String.class);
                    product.setThumb190(thumb190);
                    return modelMapper.map(product, ProductDTO.class);
                })
                .toList();
        int total = (int) pageProducts.getTotalElements();

        return ProductPageResponseDTO.builder()
                .productPageRequestDTO(productPageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    // 판매자 관리페이지 - 상품목록 - 상품관리 (검색 상품 목록)
    public ProductPageResponseDTO searchProductForSeller(String prodSeller, ProductPageRequestDTO productPageRequestDTO){

        Pageable pageable = productPageRequestDTO.getPageable("prodNo");

        Page<Tuple> pageProducts = sellerRepository.searchProductForSeller(prodSeller, productPageRequestDTO, pageable);

        List<ProductDTO> dtoList = pageProducts.getContent().stream()
                .map(tuple -> {
                    Product product = tuple.get(0, Product.class);
                    String thumb190 = tuple.get(1, String.class);
                    product.setThumb190(thumb190);
                    return modelMapper.map(product, ProductDTO.class);
                })
                .toList();
        int total = (int) pageProducts.getTotalElements();

        return ProductPageResponseDTO.builder()
                .productPageRequestDTO(productPageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

}
