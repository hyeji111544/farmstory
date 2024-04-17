package kr.co.lotteon.service;

import kr.co.lotteon.dto.SellerDTO;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.SellerRepository;
import kr.co.lotteon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

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
