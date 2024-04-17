package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteon.dto.SellerDTO;
import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.service.MemberService;
import kr.co.lotteon.service.TermsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final TermsService termsService;
    private final MemberService memberService;

    // 로그인 이동
    @GetMapping("/member/login")
    public String login(){
        return "/member/login";
    }
    
    // 회원가입 약관 동의 이동
        @GetMapping("/member/signup")
    public String terms(Model model, String userRole){

        log.info("terms..." +model);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserRole(userRole);
        TermsDTO termsDTO = termsService.selectTerms();
        model.addAttribute(termsDTO);
        model.addAttribute(userDTO);

        log.info("userDTO" +userDTO);

        return "/member/signup";
    }

    // 회원가입 유형 선택(구매 / 판매) 이동
    @GetMapping("/member/join")
    public String join(){
        return "/member/join";
    }

    // User회원 등록
    @PostMapping("/member/register")
    public String registerUser(UserDTO userDTO){

        userDTO.setUserRole("USER");
        userDTO.setUserGrade("Starter");
        userDTO.setUserStatus("1");


        LocalDateTime now = LocalDateTime.now();
        userDTO.setUserRegDate(now);

        log.info("registerUser : " +userDTO);

        User user = memberService.registerUser(userDTO);
        return "redirect:/member/login";
    }

    // Seller회원 등록
    @PostMapping("/member/registerSeller")
    public String registerSeller(UserDTO userDTO){

        userDTO.setUserRole("SELLER");
        userDTO.setUserGrade("Starter");
        userDTO.setSellerGrade("Starter");
        userDTO.setUserStatus("1");

        LocalDateTime now = LocalDateTime.now();
        userDTO.setUserRegDate(now);

        int result = memberService.registerSeller(userDTO);

        if (result > 0) {
            return "redirect:/member/login";
        }else {
            return "redirect:/member/registerSeller";
        }
    }

    //로그인 기능
    @GetMapping("/checkLogin")
    public String checkLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return "로그인 상태입니다.";
        }else {
            return "로그인 되지 않았습니다.";
        }
    }

    // 구매 회원가입 이동
    @GetMapping("/member/register")
    public String register(Model model, String userPromo){
        model.addAttribute("userPromo", userPromo);
        return "/member/register";
    }

    // 판매 회원가입 이동
    @GetMapping("/member/registerSeller")
    public String registerSeller(Model model, String userPromo){
        model.addAttribute("userPromo", userPromo);
        return "/member/registerSeller";
    }



}
