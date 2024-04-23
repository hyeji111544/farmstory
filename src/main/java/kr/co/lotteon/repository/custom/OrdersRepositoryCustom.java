package kr.co.lotteon.repository.custom;

import kr.co.lotteon.dto.MyOrderDTO;
import kr.co.lotteon.dto.MyOrderPageRequestDTO;
import kr.co.lotteon.dto.MyOrderPageResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepositoryCustom {
    public MyOrderPageResponseDTO selectMyOrders(String UserId, Pageable pageable, MyOrderPageRequestDTO myOrderPageRequestDTO);
}
