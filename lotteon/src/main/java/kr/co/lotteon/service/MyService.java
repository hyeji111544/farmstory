package kr.co.lotteon.service;

import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /*
        마이페이지 출력을 위한 service
         - user_id로 user테이블 조회 후 userDTO 반환
     */
    public UserDTO selectUserInfo(String userId){
        User user = userRepository.selectUserInfo(userId);
        return modelMapper.map(user, UserDTO.class);
    };

}
