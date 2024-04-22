package kr.co.lotteon.repository;

import kr.co.lotteon.entity.OrderDetail;
import kr.co.lotteon.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderdetailRepository extends JpaRepository<OrderDetail, Integer> {
    public List<OrderDetail>  findByOrderNo(int OrderNo);
}
