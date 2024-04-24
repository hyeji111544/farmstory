package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPointDTO {
    private int pointNo;
    private String userId;
    private int pointBalance;

    private int pointHisNo;
    private String changePoint;

    private LocalDateTime changeDate;
    private String changeCode;
    private String changeType;
}
