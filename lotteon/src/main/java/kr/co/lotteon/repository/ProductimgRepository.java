package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Product;
import kr.co.lotteon.entity.Productimg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductimgRepository extends JpaRepository<Productimg, Integer> {
    // 대분류 선택에 따른 중분류 조회

}
