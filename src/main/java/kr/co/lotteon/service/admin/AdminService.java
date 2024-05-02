package kr.co.lotteon.service.admin;


import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Banner;
import kr.co.lotteon.entity.Cate02;
import kr.co.lotteon.repository.BannerRepository;
import kr.co.lotteon.repository.Cate02Repository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;
    private final Cate02Repository cate02Repository;
    private final BannerRepository bannerRepository;

    // 대분류 선택에 따른 중분류 조회
    public ResponseEntity<List<Cate02DTO>> selectCate02(String cate01No) {
        //log.info("selectCateService....:"+cate01No);
        List<Cate02> listCate02 = cate02Repository.findByCate01No(cate01No);
        //log.info("selectCateService1...:"+listCate02);

        List<Cate02DTO> dtoList = listCate02.stream()
                .map(entity-> modelMapper.map(entity, Cate02DTO.class))
                .toList();

        return ResponseEntity.ok().body(dtoList);
    }

    // 관리자페이지 고객센터 메뉴 공지사항 글등록
    public void insertNoticeAdmin(NoticeDTO noticeDTO){

        Notice notice = modelMapper.map(noticeDTO, Notice.class);
        log.info("notice ={}", notice.toString());

        Notice savedNotice = noticeRepository.save(notice);
        log.info("savedNotice ={}", savedNotice.toString());
    }

    // 관리자페이지 고객센터 메뉴 공지사항 리스트 출력
    public PageResponseDTO selectNoticeAdmin(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("notice_no");

        Page<Notice> pageNotice = noticeRepository.findAll(pageable);

        log.info("selectNoticeAdmin...1 : " + pageNotice);

        List<NoticeDTO> dtoList = pageNotice.getContent().stream()
                .map(article -> modelMapper.map(article, NoticeDTO.class))
                .toList();

        log.info("selectNoticeAdmin...2 : " + dtoList);

        int total = (int) pageNotice.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
    // 배너 DB 저장 메서드
    public Banner bannerInsert(BannerDTO bannerDTO) {
        Banner banner = modelMapper.map(bannerDTO, Banner.class);
        Banner saveBanner = bannerRepository.save(banner);

        log.info("saveBanner : " + saveBanner);
        return saveBanner;
    }
    // 배너 목록 조회 메서드
    public List<BannerDTO> bannerList(String banImgCate) {
        List<Banner> banner = bannerRepository.findByBanImgCate(banImgCate);
        List<BannerDTO> resultBanner = new ArrayList<>();
        if(banner != null){
            for (Banner eachBanner : banner) {
                BannerDTO bannerDTO = modelMapper.map(eachBanner, BannerDTO.class);
                resultBanner.add(bannerDTO);
            }
            return resultBanner;
        }
        return null;
    }
    // 배너 DB 삭제 메서드
    public ResponseEntity<?> deleteBanner(int[] banNo){

        boolean result = bannerRepository.deleteBanner(banNo);

        Map<String, String> response = new HashMap<>();
        if(result){
            response.put("data","삭제 성공");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            response.put("data","삭제 실패");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 배너 이미지 업로드 메서드
    @Value("${file.upload.path}")
    private String fileUploadPath;
    public String bannerUpload(String banImgCate, MultipartFile file){
        String path = new File(fileUploadPath).getAbsolutePath();
        log.info("bannerImg : " + file);
        if (!file.isEmpty()){
            String oName = file.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf(".")); //확장자
            String sName = UUID.randomUUID().toString()+ ext;

            try {
                // 이미지를 메모리에 로드하여 썸네일 생성 후 저장하지 않고 경로 반환
                String bannerPath = mkBannerImg(path, sName, file, banImgCate);

                return bannerPath; // 썸네일 이미지 경로 반환
            } catch (IOException e) {
                log.error("Failed to create thumbnail: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    // 배너 썸네일 생성 메서드
    private String mkBannerImg(String path, String sName, MultipartFile file, String banImgCate) throws IOException {
        switch (banImgCate) {
            case "main1":
                Thumbnails.of(file.getInputStream())
                        .size(1200, 80)
                        .toFile(new File(path, "bannerMainTop_" + sName));
                return "bannerMainTop_" + sName;
            case "main2":
                Thumbnails.of(file.getInputStream())
                        .size(985, 447)
                        .toFile(new File(path, "bannerMainBot_" + sName));
                return "bannerMainBot_" + sName;
            case "product1":
                Thumbnails.of(file.getInputStream())
                        .size(456, 60)
                        .toFile(new File(path, "bannerProduct_" + sName));
                return "bannerProduct_" + sName;
            case "member1":
                Thumbnails.of(file.getInputStream())
                        .size(425,260)
                        .toFile(new File(path, "bannerMember_" + sName));
                return "bannerMember_" + sName;
            case "my1":
                Thumbnails.of(file.getInputStream())
                        .size(810,86)
                        .toFile(new File(path, "bannerMy_" + sName));
                return "bannerMy_" + sName;
            default:
                return null;
        }
    }
}
