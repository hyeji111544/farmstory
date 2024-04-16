package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser_coupon is a Querydsl query type for User_coupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser_coupon extends EntityPathBase<User_coupon> {

    private static final long serialVersionUID = -169848297L;

    public static final QUser_coupon user_coupon = new QUser_coupon("user_coupon");

    public final NumberPath<Integer> couponId = createNumber("couponId", Integer.class);

    public final StringPath cpNo = createString("cpNo");

    public final DateTimePath<java.time.LocalDateTime> ucpDate = createDateTime("ucpDate", java.time.LocalDateTime.class);

    public final StringPath ucpStatus = createString("ucpStatus");

    public final DateTimePath<java.time.LocalDateTime> ucpUseDate = createDateTime("ucpUseDate", java.time.LocalDateTime.class);

    public final StringPath userId = createString("userId");

    public QUser_coupon(String variable) {
        super(User_coupon.class, forVariable(variable));
    }

    public QUser_coupon(Path<? extends User_coupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser_coupon(PathMetadata metadata) {
        super(User_coupon.class, metadata);
    }

}

