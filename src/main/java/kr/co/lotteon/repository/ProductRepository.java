package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Cate02;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.repository.custom.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {
    // 대분류 선택에 따른 중분류 조회

}
