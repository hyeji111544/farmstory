package kr.co.lotteon.repository.custom;

import kr.co.lotteon.entity.Banner;

import java.util.List;

public interface BannerRepositoryCustom {
    
    // 배너삭제
    public boolean deleteBanner(int[] banNo);

    // 배너 불러오기
    public List<Banner> selectBanners(String banImgCate);

    // 배너 활성화
    public List<Banner> selectBannerUsable(String banImgCate);
}
