package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQna is a Querydsl query type for Qna
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQna extends EntityPathBase<Qna> {

    private static final long serialVersionUID = -525126175L;

    public static final QQna qna = new QQna("qna");

    public final StringPath qna_cate = createString("qna_cate");

    public final StringPath qna_content = createString("qna_content");

    public final DateTimePath<java.time.LocalDateTime> qna_date = createDateTime("qna_date", java.time.LocalDateTime.class);

    public final NumberPath<Integer> qna_no = createNumber("qna_no", Integer.class);

    public final StringPath qna_status = createString("qna_status");

    public final StringPath qna_title = createString("qna_title");

    public final StringPath qna_type = createString("qna_type");

    public final StringPath user_id = createString("user_id");

    public QQna(String variable) {
        super(Qna.class, forVariable(variable));
    }

    public QQna(Path<? extends Qna> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQna(PathMetadata metadata) {
        super(Qna.class, metadata);
    }

}

