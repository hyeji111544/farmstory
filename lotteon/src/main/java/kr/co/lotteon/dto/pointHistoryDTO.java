package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class pointHistoryDTO {

    private String pointUsedNo;
    private LocalDateTime pointUsed;
    private String pointUsedDate;
    private String pointCode;
    private int pointNo;

}

