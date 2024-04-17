package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.Cate02DTO;
import kr.co.lotteon.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;

    // 관리자 인덱스페이지 이동
    @GetMapping("/admin/index")
    public String adminIndex(){

        return "/admin/index";
    }

// admin - config //
    // 관리자 설정 배너 이동
    @GetMapping("/admin/config/banner")
    public String configBanner(){

        return "/admin/config/banner";
    }

    // 관리자 설정 정보 이동
    @GetMapping("/admin/config/info")
    public String configInfo(){

        return "/admin/config/info";
    }

// admin - product //
    // 관리자 상품 목록 이동
    @GetMapping("/admin/product/list")
    public String productList(){

        return "/admin/product/list";
    }

    // 관리자 상품 등록 이동
    @GetMapping("/admin/product/register")
    public String productRegister(){

        return "/admin/product/register";
    }

    // ??
    @PutMapping("/admin/product/cate")
    public ResponseEntity<?> changeCate(@RequestBody String cate01_no, Model model){

        try {
            ResponseEntity<List<Cate02DTO>> selectedCate02 = adminService.selectCate02(cate01_no);
            log.info("selectCate....:"+selectedCate02);

            List<Cate02DTO> cate02DTOList = selectedCate02.getBody();
            log.info("selectCate2....:"+cate02DTOList);

            return ResponseEntity.ok().body(cate02DTOList);

        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("not found");
        }

    }



}
