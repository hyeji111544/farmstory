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
    private String userId;
    private String userPw;
    private String userName;
    private LocalDateTime userBirth;
    private String userHp;
    private String userEmail;
    private String userGrade;
    private String userRole;
    private String userZip;
    private String userAddr1;
    private String userAddr2;
    private String userPromo;
    private String userStatus;
    private LocalDateTime userRegDate;
    private LocalDateTime userVisitDate;
    private String userProvider;
    private LocalDateTime userUpdate;
    private String userProfile;
    private int userPoint;
}
