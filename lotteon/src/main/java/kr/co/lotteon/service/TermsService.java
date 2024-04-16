package kr.co.lotteon.service;

import groovy.util.logging.Slf4j;
import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.mapper.TermsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TermsService {

    private final TermsMapper termsMapper;

    //이용약관 동의 출력
    public TermsDTO selectTerms(){
        return termsMapper.selectTerms();
    }
}
