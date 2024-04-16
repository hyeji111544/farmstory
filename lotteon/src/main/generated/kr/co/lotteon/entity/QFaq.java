package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFaq is a Querydsl query type for Faq
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFaq extends EntityPathBase<Faq> {

    private static final long serialVersionUID = -525137133L;

    public static final QFaq faq = new QFaq("faq");

    public final StringPath faq_cate = createString("faq_cate");

    public final StringPath faq_content = createString("faq_content");

    public final DateTimePath<java.time.LocalDateTime> faq_date = createDateTime("faq_date", java.time.LocalDateTime.class);

    public final NumberPath<Integer> faq_no = createNumber("faq_no", Integer.class);

    public final StringPath faq_title = createString("faq_title");

    public final StringPath faq_type = createString("faq_type");

    public QFaq(String variable) {
        super(Faq.class, forVariable(variable));
    }

    public QFaq(Path<? extends Faq> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFaq(PathMetadata metadata) {
        super(Faq.class, metadata);
    }

}

