package kr.co.lotteon.controller;

import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.service.TermsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final TermsService termsService;

    // 로그인 이동
    @GetMapping("/member/login")
    public String login(){
        return "/member/login";
    }
    
    // 회원가입 약관 동의 이동
        @GetMapping("/member/signup")
    public String terms(Model model, String user_role){

        UserDTO userDTO = new UserDTO();
        userDTO.setUserRole(user_role);
        TermsDTO termsDTO = termsService.selectTerms();
        model.addAttribute(termsDTO);
        model.addAttribute(userDTO);

        log.info("userdto" +userDTO);

        return "/member/signup";
    }

    // 회원가입 유형 선택(구매 / 판매) 이동
    @GetMapping("/member/join")
    public String join(){
        return "/member/join";
    }

    // 구매 회원가입 이동
    @GetMapping("/member/register")
    public String register(){
        return "/member/register";
    }

    // 판매 회원가입 이동
    @GetMapping("/member/registerSeller")
    public String registerSeller(){
        return "/member/registerSeller";
    }



}
