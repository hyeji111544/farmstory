package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.ProductPageRequestDTO;
import kr.co.lotteon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepositoryCustom {
    
    // ADMIN 페이지 프로덕트 조회
    public Page<Tuple> selectProducts(ProductPageRequestDTO pageRequestDTO, Pageable pageable);
}
