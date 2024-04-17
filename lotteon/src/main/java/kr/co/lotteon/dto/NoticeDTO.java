package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeDTO {
// 카멜 표기법으로 수정할 것
    private Integer notice_no;
    private String notice_title;
    private String notice_content;
    private String notice_type;
    private String notice_cate;
    private LocalDateTime notice_date;
}
