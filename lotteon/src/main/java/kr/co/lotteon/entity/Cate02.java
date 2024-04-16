package kr.co.lotteon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.mapping.Value;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="cate02")
public class Cate02 {
    @Id
    @Column(name="cate02_no")
    private String cate02No;

    @Column(name="cate01_no")
    private String cate01No;

    @Column(name="cate02_name")
    private String cate02Name;
 }
