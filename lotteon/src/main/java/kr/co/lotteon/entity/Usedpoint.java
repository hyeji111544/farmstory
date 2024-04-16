package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name="usedpoint")
public class Usedpoint {

    @Id
    private int pointUsedNo;
    private int pointUsed;

    @CreationTimestamp
    private LocalDateTime pointUsedDate;
    private String pointCode;
    private int pointNo;


}
