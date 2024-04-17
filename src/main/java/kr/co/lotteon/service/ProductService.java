package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.StringPath;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.entity.Productimg;
import kr.co.lotteon.repository.ProductRepository;
import kr.co.lotteon.repository.ProductimgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ProductimgRepository productimgRepository;

    // 어드민 페이지 물건 조회 메서드 (+ 검색)
    public ProductPageResponseDTO selectProductsForAdmin(ProductPageRequestDTO productPageRequestDTO){

        Pageable pageable = productPageRequestDTO.getPageable("prodNo");

        Page<Tuple> tuplePage = productRepository.selectProducts(productPageRequestDTO, pageable);
        log.info("selectProduct!....:"+tuplePage.toString());
        List<ProductDTO> dtoList = tuplePage.getContent().stream()
                .map(tuple -> {
                    Product product = tuple.get(0, Product.class);
                    Productimg productImg = tuple.get(1, Productimg.class);

                    // Productimg에서 썸네일 정보를 가져와서 ProductDTO에 설정
                    if (productImg != null) {
                        product.setThumb190(productImg.getThumb190());
                        product.setThumb230(productImg.getThumb230());
                        product.setThumb456(productImg.getThumb456());
                        product.setThumb940(productImg.getThumb940());
                    }

                    return modelMapper.map(product, ProductDTO.class);
                })
                .toList();
        log.info("selectProducts!....:"+dtoList.toString());
        int total = (int) tuplePage.getTotalElements();
        return ProductPageResponseDTO.builder()
                .productPageRequestDTO(productPageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    // 어드민 물건등록 기능
    public void registerProduct(ProductDTO productDTO, ProductimgDTO productimgDTO){

        Product product = modelMapper.map(productDTO, Product.class);

        Productimg uploadedImages = new Productimg();

        uploadedImages.setThumb190(fileUpload(productimgDTO.getMultThumb190(), "thumb190"));
        uploadedImages.setThumb230(fileUpload(productimgDTO.getMultThumb230(), "thumb230"));
        uploadedImages.setThumb456(fileUpload(productimgDTO.getMultThumb456(), "thumb456"));
        uploadedImages.setThumb940(fileUpload(productimgDTO.getMultThumb940(), "thumb940"));
        log.info(uploadedImages.getThumb190());

        Product savedProduct = productRepository.save(product);
        log.info("saved product: " + savedProduct);
        uploadedImages.setProdNo(savedProduct.getProdNo());
        log.info("saved product1.....: " + savedProduct.getProdNo());
        Productimg savedProductimg = productimgRepository.save(uploadedImages);
        log.info("registerProduct....."+savedProductimg.toString());


    }

    //이미지 업로드 메서드
    @Value("lotteon/uploads/")
    private String fileUploadPath;
    public String fileUpload(MultipartFile images, String thumbnailSize){
        String path = new File(fileUploadPath).getAbsolutePath();

        if (!images.isEmpty()){
            String oName = images.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf(".")); //확장자
            String sName = UUID.randomUUID().toString()+ ext;

            try {
                // 이미지를 메모리에 로드하여 썸네일 생성 후 저장하지 않고 경로 반환
                String thumbnailPath = mkThumbnail(path, sName, images, thumbnailSize);

                return thumbnailPath; // 썸네일 이미지 경로 반환
            } catch (IOException e) {
                log.error("Failed to create thumbnail: " + e.getMessage());
                return null;
            }
        }

        return null;
    }

    //썸네일 생성 메서드
    private String mkThumbnail(String path, String sName, MultipartFile images, String thumbnailSize) throws IOException {
        switch (thumbnailSize) {
            case "thumb190":
                Thumbnails.of(images.getInputStream())
                        .size(190, 190)
                        .toFile(new File(path, "thumb190_" + sName));
                return "thumb190_" + sName;
            case "thumb230":
                Thumbnails.of(images.getInputStream())
                        .size(230, 230)
                        .toFile(new File(path, "thumb230_" + sName));
                return "thumb230_" + sName;
            case "thumb456":
                Thumbnails.of(images.getInputStream())
                        .size(456, 456)
                        .toFile(new File(path, "thumb456_" + sName));
                return "thumb456_" + sName;
            case "thumb940":
                Thumbnails.of(images.getInputStream())
                        .width(940)
                        .toFile(new File(path, "thumb940_" + sName));
                return "thumb940_" + sName;
            default:
                return null;
        }
    }
}