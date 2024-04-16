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
@Table(name="wish")
public class Wish {

    @Id
    private int wishNo;

    @CreationTimestamp
    private LocalDateTime wishRdate;

    private String wishStatus;
    private String userId;
    private String prodNo;

}
