package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProdQnaDTO {
    private int prodQnaNo;
    private String userId;
    private int prodNo;
    private String prodQnaTitle;
    private String prodQnaContent;
    private LocalDateTime prodQnaDate;
    private String prodQnaSecret;
    private String prodQnaStatus;
    
    // 판매자 관리페이지 - FAQ List - 목록 조회를 위한 추가 필드
    private String prodName;
}
