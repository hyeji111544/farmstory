package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "qna")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qna_no;
    private String qna_title;
    private String qna_content;
    private String qna_type;
    private String qna_cate;
    private String qna_status;

    @CreationTimestamp
    private LocalDateTime qna_date;

    private String user_id;
}
