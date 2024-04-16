package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="product")
public class Product {
    @Id
    private int prodNo;
    private String prodName;
    private int prodPrice;
    private int prodDiscount;
    private String prodInfo;
    private String prodOrg;
    private int prodSold;

    @CreationTimestamp
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
