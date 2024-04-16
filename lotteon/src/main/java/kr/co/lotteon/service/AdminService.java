package kr.co.lotteon.service;

import kr.co.lotteon.dto.Cate02DTO;
import kr.co.lotteon.entity.Cate02;
import kr.co.lotteon.repository.Cate02Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {
    private final Cate02Repository cate02Repository;
    private final ModelMapper modelMapper;

    public ResponseEntity<List<Cate02DTO>> selectCate02(String cate01No){
        log.info("selectCateService....:"+cate01No);
        List<Cate02> listCate02 = cate02Repository.findByCate01No(cate01No);
        log.info("selectCateService1...:"+listCate02);

        List<Cate02DTO> dtoList = listCate02.stream()
                .map(entity-> modelMapper.map(entity, Cate02DTO.class))
                .toList();

        return ResponseEntity.ok().body(dtoList);
    }
}
