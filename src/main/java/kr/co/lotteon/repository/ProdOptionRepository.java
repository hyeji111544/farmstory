package kr.co.lotteon.repository;

import kr.co.lotteon.entity.ProdOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProdOptionRepository extends JpaRepository<ProdOption, String> {
    public List<ProdOption> findByProdNo(int prodNo);
}
