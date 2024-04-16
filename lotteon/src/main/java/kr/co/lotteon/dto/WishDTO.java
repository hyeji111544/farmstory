package kr.co.lotteon.dto;

import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishDTO {

    private String wishRdate;
    private String wishStatus;
    private String prodNo;

}
