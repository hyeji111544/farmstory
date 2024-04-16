package kr.co.lotteon.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserpointDTO {

    //user_point
    private int pointNo;
    private String userId;
    private String pointBalance;
}
