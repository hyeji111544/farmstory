package kr.co.lotteon.repository.custom;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.PageResponseDTO;
import org.springframework.data.domain.Pageable;

public interface PdReviewRepositoryCustom {
    public PageResponseDTO selectReviews(String UserId, Pageable pageable, PageRequestDTO pageRequestDTO);

}
