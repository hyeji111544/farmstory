package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsedpoint is a Querydsl query type for Usedpoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsedpoint extends EntityPathBase<Usedpoint> {

    private static final long serialVersionUID = 1411712560L;

    public static final QUsedpoint usedpoint = new QUsedpoint("usedpoint");

    public final StringPath pointCode = createString("pointCode");

    public final NumberPath<Integer> pointNo = createNumber("pointNo", Integer.class);

    public final NumberPath<Integer> pointUsed = createNumber("pointUsed", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> pointUsedDate = createDateTime("pointUsedDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> pointUsedNo = createNumber("pointUsedNo", Integer.class);

    public QUsedpoint(String variable) {
        super(Usedpoint.class, forVariable(variable));
    }

    public QUsedpoint(Path<? extends Usedpoint> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsedpoint(PathMetadata metadata) {
        super(Usedpoint.class, metadata);
    }

}

