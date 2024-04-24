package kr.co.lotteon.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.MyOrderDTO;
import kr.co.lotteon.dto.MyOrderPageRequestDTO;
import kr.co.lotteon.dto.MyOrderPageResponseDTO;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.custom.OrdersRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct qProduct = QProduct.product;
    private final QOrders qOrders = QOrders.orders;
    private final QOrderDetail qOrderDetail = QOrderDetail.orderDetail;
    private final QProductimg qProductimg = QProductimg.productimg;

    // My/Order 페이지 상품 목록 조회
    public MyOrderPageResponseDTO selectMyOrders(String UserId, Pageable pageable, MyOrderPageRequestDTO myOrderPageRequestDTO) {
        // Orders 테이블에서 orderDate구하고, OrderNO 조회

        log.info("IMPL 시작");
        
        // SELECT * FROM `orders` WHERE `userId` = ? ORDER BY `orderDate` DESC;
        List<Orders> selectOrders = jpaQueryFactory
                                    .selectFrom(qOrders)
                                    .where(qOrders.userId.eq(UserId))
                                    .orderBy(qOrders.orderDate.desc())
                                    .fetch();

        log.info("selectOrders : " + selectOrders);

        // return을 위한 List<MyOrderDTO> 생성
        List<MyOrderDTO> myOrderDTOList = new ArrayList<>();

        log.info("새로만든 myOrderDTOList : " + myOrderDTOList);

        // OrderNO 가지고 OrderDetail 테이블에서 주문 상품 목록 조회, prodNo 구하기
        // SELECT * FROM `orderDetail` WHERE `orderNo` = ?
        for (Orders order : selectOrders) {
            List<OrderDetail> selectOrderDetail = jpaQueryFactory
                                                    .selectFrom(qOrderDetail)
                                                    .where(qOrderDetail.orderNo.eq(order.getOrderNo()))
                                                    .fetch();

            for (OrderDetail orderDetail : selectOrderDetail){
                // 상품 정보 임시 저장을 위한 orderDTO
                MyOrderDTO orderDTO = new MyOrderDTO();

                log.info("새로만든 orderDTO : " + orderDTO);

                orderDTO.setOrderDate(order.getOrderDate());
                orderDTO.setCount(orderDetail.getCount());
                orderDTO.setDetailPrice(orderDetail.getDetailPrice());
                orderDTO.setDetailStatus(orderDetail.getDetailStatus());
                orderDTO.setOrderNo(orderDetail.getOrderNo());
                log.info("orderDTO 1 : " + orderDTO);

                // prodNo 가지고 Product에서 상품 정보 조회 / prodNo 가지고 ProductImg에서 상품 이미지 조회
                // SELECT `prodName`, `prodCompany`, `thumb190` FROM `Product` AS a JOIN `ProductImg` AS b ON a.prodNo = b.prodNo WHERE a.prodNo = ?
                 Tuple selectProduct = jpaQueryFactory
                                        .select(qProduct.prodName, qProduct.prodCompany, qProductimg.thumb190)
                                        .from(qProduct)
                                        .join(qProductimg)
                                        .on(qProduct.prodNo.eq(qProductimg.prodNo))
                                        .where(qProduct.prodNo.eq(orderDetail.getProdNo()))
                                        .fetchOne();

                if (selectProduct != null) {
                    String prodName = selectProduct.get(0, String.class);
                    String prodCompany = selectProduct.get(1, String.class);
                    String thumb190 = selectProduct.get(2, String.class);

                    orderDTO.setProdName(prodName);
                    orderDTO.setProdCompany(prodCompany);
                    orderDTO.setThumb190(thumb190);

                    log.info("orderDTO 2 : " + orderDTO);

                    myOrderDTOList.add(orderDTO);

                    log.info("if문 마지막 myOrderDTOList : " + myOrderDTOList);
                }
            }
        }
        log.info("코드 끝났을때 myOrderDTOList : " + myOrderDTOList);
        log.info("IMPL 끝");

        int total = myOrderDTOList.size();
        log.info("total : " + total);

        return MyOrderPageResponseDTO.builder()
                .pageRequestDTO(myOrderPageRequestDTO)
                .myOrderDTOList(myOrderDTOList)
                .total(total)
                .build();


    }
}
