package kr.co.lotteon.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.SellerDTO;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.SellerRepository;
import kr.co.lotteon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    // email 전송
    private final JavaMailSender javaMailSender;
    private final HttpSession httpSession;

    // 회원가입 유저 정보 중복 체크
    public int registerUserCheck(HttpSession session, String type, String value){
        int result = 0; // 0은 사용 가능, 1은 사용 불가능

        // User 테이블 조회를 통해 중복 여부 확인
        if (type.equals("userId")) {
            // 아이디 중복 검사
            Optional<User> optUser = userRepository.findById(value);
            // optional이 비어있는지 체크
            if (optUser.isPresent()) {
                // 사용 불가능
                result = 1;
                return result;
            } else {
                // 사용 가능
                return result;
            }
        }else if(type.equals("userHp")) {
            //전화번호 중복검사
            Optional<User> optUser = userRepository.findByUserHp(value);
            //Optional이 비어있는지 체크
            if(optUser.isPresent()){
                // 사용 불가능
                result = 1;
                return result;
            }else {
                // 사용가능
                return result;
            }
        } else if (type.equals("userEmail")) {
            //이메일 중복검사
            Optional<User> optUser = userRepository.findByUserEmail(value);
            //Optional이 비어있는지 체크
            if(optUser.isPresent()){
                //사용 불가능
                result = 1;
                return  result;
            }else {
                // 사용 가능
                // 인증코드 발송
                sendEmailConde(session ,value);
                return result;
            }
        }


        return result;
    }

    @Value("${spring.mail.username}")
    private String sender;
    // 🎈이메일 인증코드 전송
    public void sendEmailConde(HttpSession session, String receiver){
        log.info("sender : " + sender);

        // MimeMessage 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        // 인증코드 생성 후 세션 저장
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        session.setAttribute("code", String.valueOf(code));

        log.info("code : " + code);

        String title = "🌻lotteon 인증코드 입니다.";
        String content = "<h1>인증코드는" +  code + "입니다.</h1>";

        try {
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            javaMailSender.send(message);

        } catch(Exception e){
            log.error("sendEmailCode : " + e.getMessage());
        }
    }


    //User 회원 등록
    public User registerUser(UserDTO userDTO){
        String encoded = passwordEncoder.encode(userDTO.getUserPw());
        userDTO.setUserPw(encoded);

        User user = modelMapper.map(userDTO, User.class);

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    //Seller 회원 등록
    public int registerSeller(UserDTO userDTO){
        // 랜덤 난수로 sellerId 생성
        String sellerNo = UUID.randomUUID().toString().substring(0, 8) + userDTO.getUserId();
        String encoded = passwordEncoder.encode(userDTO.getUserPw());

        SellerDTO saveSellerDTO = new SellerDTO();
        userDTO.setUserPw(encoded);
        saveSellerDTO.setSellerNo(sellerNo);
        saveSellerDTO.setUserId(userDTO.getUserId());
        saveSellerDTO.setSellerName(userDTO.getSellerName());
        saveSellerDTO.setSellerHp(userDTO.getSellerHp());
        saveSellerDTO.setCompany(userDTO.getCompany());
        saveSellerDTO.setBusinessNum(userDTO.getBusinessNum());
        saveSellerDTO.setLicenseNum(userDTO.getLicenseNum());
        saveSellerDTO.setSellerGrade(userDTO.getSellerGrade());
        saveSellerDTO.setFax(userDTO.getFax());

        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);

        Seller seller = modelMapper.map(saveSellerDTO, Seller.class);
        Seller savedSeller = sellerRepository.save(seller);

        int result = 0;
        if (savedUser.getUserId().equals(userDTO.getUserId())  && savedSeller.getUserId().equals(userDTO.getUserId())) {
            result = 1;
        }

        return result;
    }

}
