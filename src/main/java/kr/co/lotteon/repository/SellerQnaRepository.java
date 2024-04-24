package kr.co.lotteon.repository;

import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.ProdQna;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.repository.custom.SellerQnaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerQnaRepository extends JpaRepository<Seller, String>, SellerQnaRepositoryCustom {


}
