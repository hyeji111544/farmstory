package kr.co.lotteon.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    //user
    private String user_id;
    private String user_pw;
    private String user_name;
    private LocalDateTime user_birth;
    private String user_hp;
    private String user_email;
    private String user_grade;
    private String user_role;
    private String user_zip;
    private String user_addr1;
    private String user_addr2;
    private String user_promo;
    private String user_status;
    private LocalDateTime user_regDate;
    private LocalDateTime user_visit_Date;
    private String user_provider;
    private LocalDateTime user_update;
    private String user_profile;
    private int user_point;
    private int userpoint_point_no;

}
