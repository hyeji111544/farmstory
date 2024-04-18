package kr.co.lotteon.service;

import kr.co.lotteon.dto.Cate01DTO;
import kr.co.lotteon.dto.Cate02DTO;
import kr.co.lotteon.dto.Cate03DTO;
import kr.co.lotteon.entity.Cate01;
import kr.co.lotteon.entity.Cate02;
import kr.co.lotteon.entity.Cate03;
import kr.co.lotteon.repository.Cate01Repository;
import kr.co.lotteon.repository.Cate02Repository;
import kr.co.lotteon.repository.Cate03Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProdCateService {

    private final Cate01Repository cate01Repository;
    private final Cate02Repository cate02Repository;
    private final Cate03Repository cate03Repository;
    private final ModelMapper modelMapper;

    // sideBar 카테고리 정보 조회
    public Map<String, List<?>> selectProdCate(){
        // cate01 DB에서 조회 후 List<cate01DTO>로 변환
        List<Cate01DTO> cate01DTOs = cate01Repository.findAll().stream()
                .map(cate -> modelMapper.map(cate, Cate01DTO.class))
                .toList();
        // cate02 DB에서 조회 후 List<cate02DTO>로 변환
        List<Cate02DTO> cate02DTOs = cate02Repository.findAll().stream()
                .map(cate -> modelMapper.map(cate, Cate02DTO.class))
                .toList();
        // cate03 DB에서 조회 후 List<cate03DTO>로 변환
        List<Cate03DTO> cate03DTOs = cate03Repository.findAll().stream()
                .map(cate -> modelMapper.map(cate, Cate03DTO.class))
                .toList();

        log.info("cate01DTOs : " + cate01DTOs);
        log.info("cate02DTOs : " + cate02DTOs);
        log.info("cate03DTOs : " + cate03DTOs);

        Map<String, List<?>> cateMap = new HashMap<>();
        cateMap.put("cate01DTOs", cate01DTOs);
        cateMap.put("cate02DTOs", cate02DTOs);
        cateMap.put("cate03DTOs", cate03DTOs);
        return cateMap;
    }
}
