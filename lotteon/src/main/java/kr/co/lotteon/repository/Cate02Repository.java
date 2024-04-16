package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Cate02;
import kr.co.lotteon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Cate02Repository extends JpaRepository<Cate02, String> {
    List<Cate02> findByCate01No(String cate01No);

}
