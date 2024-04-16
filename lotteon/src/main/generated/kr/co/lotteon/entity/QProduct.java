package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 425002604L;

    public static final QProduct product = new QProduct("product");

    public final StringPath cate_code = createString("cate_code");

    public final NumberPath<Integer> discount = createNumber("discount", Integer.class);

    public final StringPath prod_business_type = createString("prod_business_type");

    public final StringPath prod_company = createString("prod_company");

    public final NumberPath<Integer> prod_delivery_fee = createNumber("prod_delivery_fee", Integer.class);

    public final StringPath prod_info = createString("prod_info");

    public final StringPath prod_name = createString("prod_name");

    public final NumberPath<Integer> prod_no = createNumber("prod_no", Integer.class);

    public final StringPath prod_org = createString("prod_org");

    public final NumberPath<Integer> prod_point = createNumber("prod_point", Integer.class);

    public final NumberPath<Integer> prod_price = createNumber("prod_price", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> prod_rdate = createDateTime("prod_rdate", java.time.LocalDateTime.class);

    public final StringPath prod_receipt = createString("prod_receipt");

    public final StringPath prod_seller = createString("prod_seller");

    public final NumberPath<Integer> prod_sold = createNumber("prod_sold", Integer.class);

    public final StringPath prod_status = createString("prod_status");

    public final NumberPath<Integer> prod_stock = createNumber("prod_stock", Integer.class);

    public final StringPath prod_tax = createString("prod_tax");

    public final StringPath thumb = createString("thumb");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

