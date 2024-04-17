package kr.co.lotteon.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerDTO {

    private String sellerId;
    private String company;
    private String userId;
    private String sellerName;
    private String sellerHp;
    private String businessNum;
    private String licenseNum;
    private String sellerGrade;
    private String fax;


}
