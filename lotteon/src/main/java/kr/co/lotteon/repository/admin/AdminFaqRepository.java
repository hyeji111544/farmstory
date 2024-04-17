package kr.co.lotteon.repository.admin;

import kr.co.lotteon.entity.Faq;
import kr.co.lotteon.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminFaqRepository extends JpaRepository<Faq, Integer> {

    // 자주묻는질문 글 리스트 출력 페이징 메서드
    public Page<Faq> findAll(Pageable pageable);
}
