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
    private String prodInfo;
    private String prodOrg;
    private int prodSold;
    private LocalDateTime prodRdate;
    private String prodCompany;
    private int prodDeliveryFee;
    private String cateCode;
    private String prodStatus;
    private String prodSeller;
    private String prodBusinessType;
    private String prodReceipt;
    private String prodTax;
}