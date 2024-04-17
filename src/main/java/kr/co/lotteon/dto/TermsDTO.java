package kr.co.lotteon.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermsDTO {

    //terms
    private String buyer;
    private String finance;
    private String location;
    private String privacy;
    private String seller;

}
