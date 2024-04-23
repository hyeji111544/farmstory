package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Faq;
import kr.co.lotteon.entity.Notice;
import kr.co.lotteon.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Integer> {

    public Page<Qna> findByQnaCate(String cate, Pageable pageable);
}
