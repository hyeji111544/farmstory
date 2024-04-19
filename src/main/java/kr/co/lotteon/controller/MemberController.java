package kr.co.lotteon.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.SellerDTO;
import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.service.MemberService;
import kr.co.lotteon.service.TermsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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

    // 회원가입 유저 정보 중복 체크 (아이디, 전화번호, 이메일)
    @GetMapping("/member/checkUser/{type}/{value}")
    public ResponseEntity<?> registerUserCheck(HttpSession session, @PathVariable("type") String type, @PathVariable("value") String value){
        log.info("type : " + type);
        log.info("value : " + value);

        // service에서 중복 체크
        int result = memberService.registerUserCheck(session, type, value);
        
        // json 형식으로 변환
        Map<String, Integer> data = new HashMap<>();
        data.put("result", result);
        return ResponseEntity.ok().body(data);
    }

    //회원가입 유효성 검사
    @GetMapping("member/checkEmailCode/{inputCode}")
    public ResponseEntity<?> checkEmailCode(HttpSession session, @PathVariable("inputCode") String inputCode){
        // 서버에서 발급한 인증 코드
        String code = (String) session.getAttribute("code");
        // 회원가입하는 사용자가 입력한 코드
        String checkCode = inputCode;

        Map<String, Integer> data = new HashMap<>();
        if(code.equals(checkCode)){
            //json 형식으로 변환
            data.put("result", 0);
            return ResponseEntity.ok().body(data);
        }else {
            //json 형식으로 변환
            data.put("result", 1);
            return ResponseEntity.ok().body(data);
        }

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


    // 구매 회원가입 이동
    @GetMapping("/member/register2")
    public String register2(){

        return "/member/register2";
    }
}
