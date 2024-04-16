package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDTO {

    private Integer qna_no;
    private String qna_title;
    private String qna_content;
    private String qna_type;
    private String qna_cate;
    private String qna_status;
    private LocalDateTime qna_date;
}
