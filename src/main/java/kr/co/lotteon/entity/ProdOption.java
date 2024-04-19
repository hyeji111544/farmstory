package kr.co.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "prodoption")
public class ProdOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int optNo;
    public int prodNo;
    public String optName;
    public String optValue;
    public int optPrice;
    public int optStock;
}
