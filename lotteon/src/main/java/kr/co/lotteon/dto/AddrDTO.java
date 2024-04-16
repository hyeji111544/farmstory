package kr.co.lotteon.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddrDTO {

    private int addNo;
    private String addrName;
    private String receiver;
    private String hp;
    private int zip;
    private String addr1;
    private String addr2;
    private String defaultAddr;
    private String userId;

}
