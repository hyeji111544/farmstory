package kr.co.lotteon.repository.admin;

import kr.co.lotteon.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminQnaRepository extends JpaRepository<Qna, Integer> {
}
