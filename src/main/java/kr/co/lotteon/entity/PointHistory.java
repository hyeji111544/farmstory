package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="pointhistory")
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointHisNo;
    private int pointNo;
    private int changePoint;

    @CreationTimestamp
    private LocalDateTime changeDate;
    private String changeCode;
    private String changeType;
}
