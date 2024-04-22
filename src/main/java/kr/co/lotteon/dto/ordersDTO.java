package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ordersDTO {

    private int orderNo;
    private String userId;
    private String orderReceiver;
    private String orderHp;
    private String orderAddr;
    private String orderPay;
    private int orderPrice;
    private int userUsedPoint;
    private String orderMemo;
    private String orderStatus;
    private LocalDateTime orderDate;

}
