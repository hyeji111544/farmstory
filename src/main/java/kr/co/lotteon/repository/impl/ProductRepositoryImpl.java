package kr.co.lotteon.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.ProductPageRequestDTO;
import kr.co.lotteon.entity.QProduct;
import kr.co.lotteon.entity.QProductimg;
import kr.co.lotteon.entity.QUser;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.custom.ProductRepositoryCustom;
import kr.co.lotteon.repository.custom.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QProduct qProduct = QProduct.product;
    private QProductimg qProductimg = QProductimg.productimg;


    //ADMIN 페이지 프로덕트 조화
    @Override
    public Page<Tuple> selectProducts(ProductPageRequestDTO pageRequestDTO, Pageable pageable) {

        QueryResults<Tuple> results = jpaQueryFactory
                .select(qProduct, qProductimg)
                .from(qProduct)
                .join(qProductimg)
                .on(qProduct.prodNo.eq(qProductimg.prodNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qProduct.prodNo.desc())
                .fetchResults();

        List<Tuple> content = results.getResults();
        log.info(content.toString());
        long total = results.getTotal();
        log.info("total : {}", total);
        return new PageImpl<>(content, pageable, total);
    }

    // cate 로 프로덕트 조회
    public Page<Tuple> selectProductsByCate(ProductPageRequestDTO pageRequestDTO, Pageable pageable){
        String sort = pageRequestDTO.getSort();
        String seletedCate = pageRequestDTO.getCateCode();
        OrderSpecifier<?> orderSpecifier = null;
        log.info("here1 : " + sort);

        if (sort != null && sort.startsWith("prodSole")){
            orderSpecifier = qProduct.prodSold.desc();
        }else if (sort != null && sort.startsWith("prodLowPrice")) {
            orderSpecifier = qProduct.prodPrice.asc();
        }else if (sort != null && sort.startsWith("prodHighPrice")) {
            orderSpecifier = qProduct.prodPrice.desc();
        }else if (sort != null && sort.startsWith("prodScore")) {
            orderSpecifier = qProduct.tReviewScore.desc();
        }else if (sort != null && sort.startsWith("prodReview")) {
            orderSpecifier = qProduct.tReviewCount.desc();
        }else if (sort != null && sort.startsWith("prodRdate")) {
            orderSpecifier = qProduct.prodRdate.asc();
        }else {
            orderSpecifier = qProduct.prodSold.desc();
        }

        QueryResults<Tuple> results = jpaQueryFactory
                .select(qProduct, qProductimg)
                .from(qProduct)
                .join(qProductimg)
                .on(qProduct.prodNo.eq(qProductimg.prodNo))
                .where(qProduct.cateCode.like(seletedCate+"%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetchResults();

        List<Tuple> content = results.getResults();
        log.info(content.toString());
        long total = results.getTotal();
        log.info("total : {}", total);
        return new PageImpl<>(content, pageable, total);
    }

    // 제품 상세 조회
    @Override
    public Tuple selectProduct(int prodNo){
        return jpaQueryFactory
                .select(qProduct, qProductimg)
                .from(qProduct)
                .join(qProductimg)
                .on(qProduct.prodNo.eq(qProductimg.prodNo))
                .where(qProduct.prodNo.eq(prodNo))
                .fetchOne();
    }
}