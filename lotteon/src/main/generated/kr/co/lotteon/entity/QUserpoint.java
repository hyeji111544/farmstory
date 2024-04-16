package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserpoint is a Querydsl query type for Userpoint
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserpoint extends EntityPathBase<Userpoint> {

    private static final long serialVersionUID = 1812520674L;

    public static final QUserpoint userpoint = new QUserpoint("userpoint");

    public final StringPath pointBalance = createString("pointBalance");

    public final NumberPath<Integer> pointNo = createNumber("pointNo", Integer.class);

    public final StringPath userId = createString("userId");

    public QUserpoint(String variable) {
        super(Userpoint.class, forVariable(variable));
    }

    public QUserpoint(Path<? extends Userpoint> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserpoint(PathMetadata metadata) {
        super(Userpoint.class, metadata);
    }

}

