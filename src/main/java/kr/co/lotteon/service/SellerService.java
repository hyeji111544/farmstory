package kr.co.lotteon.service;

import kr.co.lotteon.dto.SellerInfoDTO;
import kr.co.lotteon.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    public SellerInfoDTO selectSellerInfo(String prodSeller){
        return sellerRepository.selectSellerInfo(prodSeller);
    }

}
