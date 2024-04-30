package kr.co.lotteon.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.custom.OrdersRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct qProduct = QProduct.product;
    private final QOrders qOrders = QOrders.orders;
    private final QOrderDetail qOrderDetail = QOrderDetail.orderDetail;
    private final QProductimg qProductimg = QProductimg.productimg;
    private final ModelMapper modelMapper;


    // My/Order 페이지 상품 목록조회(날짜순)
    public MyOrderPageResponseDTO selectMyOrdersByDate(String UserId,Pageable pageable,MyOrderPageRequestDTO myOrderPageRequestDTO){
        log.info("IMPL 시작");
        //1. orders테이블에서 10개 조회
        // SELECT orderNo FROM orders WHERE userId = '?' ORDER BY orderDate DESC LIMIT 10
        List<Integer> selectOrderNo = jpaQueryFactory
                .select(qOrders.orderNo)
                .from(qOrders)
                .where(qOrders.userId.eq(UserId))
                .orderBy(qOrders.orderDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        log.info("orderNo 조회" + selectOrderNo);

        //2. 그 10개의 orderNo를 for문으로 orderdetail에서 조회 -> 각 orderNo 마다 List<orderDetail>
        //SELECT * FROM orderdetail AS a JOIN product AS b ON a.prodNo = b.prodNo WHERE a.orderNO = '?'

        LinkedHashMap<Integer, List<OrderDetailDTO>> orderDetailDTOMap = new LinkedHashMap<>();

        for(Integer orderNo : selectOrderNo){
            List<Tuple> orderDetails  = jpaQueryFactory
                    .select(qOrderDetail, qProduct.prodCompany, qProduct.prodName, qProductimg.thumb190)
                    .from(qOrderDetail)
                    .join(qProduct)
                    .on(qOrderDetail.prodNo.eq(qProduct.prodNo))
                    .join(qProductimg)
                    .on(qProduct.prodNo.eq(qProductimg.prodNo))
                    .where(qOrderDetail.orderNo.eq(orderNo))
                    .fetch();

            log.info("OrderDetail 조회" +orderDetails);

            // List<Tuple> => List<OrderDetailDTO>
            List<OrderDetailDTO> dtoList = orderDetails.stream()
                    .map(tuple -> {
                        OrderDetail orderDetail = tuple.get(0, OrderDetail.class);
                        //구조체로 가져온게 아니라 단일값을 가져온거라 String형으로 선언
                        String company = tuple.get(1, String.class); // String으로 형변환을 하겠다
                        String prodName = tuple.get(2, String.class);
                        String thumb190 = tuple.get(3, String.class);

                        //OrderDetailDTO 로 변환
                        OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetail, OrderDetailDTO.class);
                        orderDetailDTO.setProdCompany(company);
                        orderDetailDTO.setProdName(prodName);
                        orderDetailDTO.setThumb190(thumb190);

                        return orderDetailDTO;
                    }
                ).toList();

            log.info("dtoList" +dtoList);
            orderDetailDTOMap.put(orderNo,dtoList);
        }
        log.info("orderDetailDTOMap" +orderDetailDTOMap);

        // 페이징 처리
        long total = jpaQueryFactory.select(qOrders.count()).from(qOrders).where(qOrders.userId.eq(UserId))
                .orderBy(qOrders.orderDate.desc()).fetchOne();

        Page<Integer> pageImpl = new PageImpl<>(selectOrderNo, pageable, total);
        int total2 = (int) pageImpl.getTotalElements();

        return MyOrderPageResponseDTO.builder()
                .pageRequestDTO(myOrderPageRequestDTO)
                .myOrderDTOList(orderDetailDTOMap)
                .total(total2)
                .build();
    }

}
