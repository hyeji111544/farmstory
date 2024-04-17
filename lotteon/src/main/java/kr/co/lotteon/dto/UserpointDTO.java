package kr.co.lotteon.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserpointDTO {
    private int pointNo;
    private String userId;
    private int pointBalance;
}
