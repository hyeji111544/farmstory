package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDTO {
    private int prodNo;
    private String prodName;
    private int prodPrice;
    private int prodDiscount;
    private int prodPoint;
    private int prodStock;
    private String prodInfo;
    private String prodOrg;
    private int prodSold;

    private int tReviewCount;
    private int tReviewScore;

    private LocalDateTime prodRdate;
    private String prodCompany;
    private int prodDeliveryFee;
    private String cateCode;
    private String prodStatus;
    private String prodSeller;
    private String prodBusinessType;
    private String prodReceipt;
    private String prodTax;

    // 이미지 출력을 위한 컬럼 추가
    private String thumb190;
    private String thumb230;
    private String thumb456;
    private String thumb940;
}
