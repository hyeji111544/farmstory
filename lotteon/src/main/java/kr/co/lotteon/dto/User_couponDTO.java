package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User_couponDTO {

    private String couponId;
    private LocalDateTime ucpDate;
    private LocalDateTime ucpUseDate;
    private String ucpStatus;
    private String userId;
    private String cpNo;

}
