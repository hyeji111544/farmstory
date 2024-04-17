package kr.co.lotteon.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishDTO {
    private int wishNo;
    private String userId;
    private int prodNo;
    private LocalDateTime wishRdate;
    private String wishStatus;
}