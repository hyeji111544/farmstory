package kr.co.lotteon.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SellerInfoDTO {

    // 주문건수, 주문금액 출력을 위한 필드
    private int oneDayCount;
    private int oneDayPrice;
    private int oneWeekCount;
    private int oneWeekPrice;
    private int oneMonthsCount;
    private int oneMonthsPrice;
    private int allCount;
    private int allPrice;

    // 주문상태 출력을 위한 필드
    private Map<String, Long> statusCountMap;
    
    // 그래프 출력을 위한 필드
    private List<GraphInfoDTO> graphInfoDTO;
}
