package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDetailDTO {

    private int detailNo;
    private int orderNo;
    private int prodNo;
    private int optNo;
    private int count;
    private int detailPrice;
    private int detailPoint;
    private String detailStatus;
    private String prodSeller;
    private LocalDate detailDate;

    // 주문현황 페이지 처리를 위한 추가 필드
    private String userId;
    private String orderReceiver;
    private String orderHp;
    private String orderPay;
    private String orderMemo;
    private String orderAddr;
    private String prodName;
}