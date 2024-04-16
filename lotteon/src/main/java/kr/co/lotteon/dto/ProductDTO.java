package kr.co.lotteon.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDTO {

    private int prod_no;
    private String prod_name;
    private int prod_price;
    private int prod_stock;
    private String prod_company;
    private String prod_info;
    private int prod_sold;
    private LocalDateTime prod_rdate;
    private String prod_status;

    private String prod_tax;
    private String prod_receipt;
    private String prod_business_type;
    private String prod_org;
    private int discount;
    private int prod_point;
    private int prod_delivery_fee;
    private String thumb;
    private String prod_seller;

    private String cate_code;
}
