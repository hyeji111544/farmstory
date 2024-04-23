package kr.co.lotteon.repository;

import kr.co.lotteon.entity.ProdOptDetail;
import kr.co.lotteon.repository.custom.ProdOptDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdOptDetailRepository extends JpaRepository<ProdOptDetail, Integer>, ProdOptDetailRepositoryCustom {
}
