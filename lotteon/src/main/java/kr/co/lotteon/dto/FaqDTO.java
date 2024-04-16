package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaqDTO {

    private Integer faq_no;
    private String faq_title;
    private String faq_content;
    private String faq_type;
    private String faq_cate;
    private LocalDateTime faq_date;
}
