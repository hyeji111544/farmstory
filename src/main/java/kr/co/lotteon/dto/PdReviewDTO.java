package kr.co.lotteon.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PdReviewDTO {
    private int revNo;
    private String userId;
    private int prodNo;
    private String revContent;
    private int revScore;
    private LocalDateTime revAddDate;
}
