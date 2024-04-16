package kr.co.lotteon.controller;

import groovy.util.logging.Slf4j;
import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TermsController {

    private final TermsService termsService;


}
