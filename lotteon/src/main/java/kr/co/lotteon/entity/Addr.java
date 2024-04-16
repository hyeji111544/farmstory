package kr.co.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="addr")
public class Addr {

    @Id
    private int addrNo;
    private String addrName;
    private String receiver;
    private String hp;
    private int zip;
    private String addr1;
    private String addr2;
    private String defaultAddr;
    private String userId;

}
