package kr.co.lotteon.repository.custom;

import kr.co.lotteon.dto.MyOrderDTO;
import kr.co.lotteon.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepositoryCustom {
    public List<MyOrderDTO> selectMyOrders(String UserId);
}
