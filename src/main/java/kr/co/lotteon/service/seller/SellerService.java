package kr.co.lotteon.service.seller;

import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.OrderDetail;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;

    // 판매자 관리페이지 - 홈
    public SellerInfoDTO selectSellerInfo(HttpSession session, String UserId){

        Optional<Seller> optSeller = sellerRepository.findByUserId(UserId);
        String prodSeller = null;
        if (optSeller.isPresent()){
            prodSeller= optSeller.get().getSellerNo();
            session.setAttribute("prodSeller", optSeller.get().getSellerNo());
        }

        return sellerRepository.selectSellerInfo(prodSeller);
    }

    // 판매자 관리페이지 - 상품목록 - 상품관리 (전체 상품 목록)
    public ProductPageResponseDTO selectProductForSeller(String prodSeller, ProductPageRequestDTO productPageRequestDTO){

        Pageable pageable = productPageRequestDTO.getPageable("prodNo");

        Page<Tuple> pageProducts = sellerRepository.selectProductForSeller(prodSeller, productPageRequestDTO, pageable);

        List<ProductDTO> dtoList = pageProducts.getContent().stream()
                .map(tuple -> {
                    Product product = tuple.get(0, Product.class);
                    String thumb190 = tuple.get(1, String.class);
                    product.setThumb190(thumb190);
                    return modelMapper.map(product, ProductDTO.class);
                })
                .toList();
        int total = (int) pageProducts.getTotalElements();

        return ProductPageResponseDTO.builder()
                .productPageRequestDTO(productPageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    // 판매자 관리페이지 - 상품목록 - 상품관리 (검색 상품 목록)
    public ProductPageResponseDTO searchProductForSeller(String prodSeller, ProductPageRequestDTO productPageRequestDTO){

        Pageable pageable = productPageRequestDTO.getPageable("prodNo");

        Page<Tuple> pageProducts = sellerRepository.searchProductForSeller(prodSeller, productPageRequestDTO, pageable);

        List<ProductDTO> dtoList = pageProducts.getContent().stream()
                .map(tuple -> {
                    Product product = tuple.get(0, Product.class);
                    String thumb190 = tuple.get(1, String.class);
                    product.setThumb190(thumb190);
                    return modelMapper.map(product, ProductDTO.class);
                })
                .toList();
        int total = (int) pageProducts.getTotalElements();

        return ProductPageResponseDTO.builder()
                .productPageRequestDTO(productPageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    // 최근 한달치 주문 건수 //
    public LinkedHashMap<String, Integer> selectProdSalesCount(String prodSeller) {
        // 최근 한달치 주문 데이터 조회
        List<OrderDetail> selectOrders = sellerRepository.selectProdSalesCount(prodSeller);

        // 모든 날짜를 포함하는 기간을 정의
        LocalDate oneMonthsAgo = LocalDate.now().minusMonths(1);
        List<LocalDate> allDatesInRange = Stream.iterate(oneMonthsAgo, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(oneMonthsAgo, LocalDate.now()) + 1)
                .collect(Collectors.toList());

        LinkedHashMap<String, Integer> orderByDate = new LinkedHashMap<>();

        for (int i = allDatesInRange.size() - 1; i >= 0; i--) {
            String localDate = allDatesInRange.get(i).toString().substring(5, 10);
            orderByDate.put(localDate, 0);
        }

        for (OrderDetail order : selectOrders) {
            String date = order.getDetailDate().toString().substring(5, 10);
            if (orderByDate.containsKey(date)) {
                orderByDate.put(date, orderByDate.get(date)+1);
            }else {
                orderByDate.put(date, 1);
            }
        }
        log.info("orderByDate : " + orderByDate);
        return orderByDate;
    }

    public PageResponseDTO selectProdSalesInfo(String prodSeller, PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("No");

        Page<Tuple> orderResults = sellerRepository.selectProdSalesInfo(prodSeller, pageRequestDTO, pageable);

        List<OrderDetailDTO> dtoList = orderResults.stream()
                .map(tuple -> {
                    OrderDetail orderDetail = tuple.get(0, OrderDetail.class);
                    String prodName = tuple.get(1, String.class);
                    Orders orders = tuple.get(2, Orders.class);
                    OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetail, OrderDetailDTO.class);
                    orderDetailDTO.setProdName(prodName);
                    orderDetailDTO.setUserId(orders.getUserId());
                    orderDetailDTO.setOrderReceiver(orders.getOrderReceiver());
                    orderDetailDTO.setOrderHp(orders.getOrderHp());
                    orderDetailDTO.setOrderPay(orders.getOrderPay());
                    orderDetailDTO.setOrderMemo(orders.getOrderMemo());
                    orderDetailDTO.setOrderAddr(orders.getOrderAddr());
                    return orderDetailDTO;
                })
                .toList();

        int total = (int) orderResults.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

}
