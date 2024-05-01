package kr.co.lotteon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CompanyController {



    @GetMapping("/company/index")
    public String companyIndex(){

        return "/company/index";
    }

    @GetMapping("/company/story")
    public String companyStory(){

        return "/company/story";
    }

    @GetMapping("/company/culture")
    public String companyCulture(){

        return "/company/culture";
    }

    @GetMapping("/company/recruit")
    public String companyRecruit(){

        return "/company/recruit";
    }

    @GetMapping("/company/media")
    public String companyMedia(){

        return "/company/media";
    }
}
