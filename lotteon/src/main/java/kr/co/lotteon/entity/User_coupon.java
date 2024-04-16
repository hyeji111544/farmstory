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
@Table(name="user_coupon")
public class User_coupon {

    @Id
    private int couponId;

    @CreationTimestamp
    private LocalDateTime ucpDate;
    private LocalDateTime ucpUseDate;

    private String ucpStatus;
    private String userId;
    private String cpNo;

}
