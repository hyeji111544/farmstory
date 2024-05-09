package kr.co.lotteon.repository;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.ProdQna;
import kr.co.lotteon.repository.custom.ProdQnaRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdQnaRepository extends JpaRepository<ProdQna, Integer>, ProdQnaRepositoryCustom {


}
