package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 901081966L;

    public static final QUser user = new QUser("user");

    public final StringPath user_addr1 = createString("user_addr1");

    public final StringPath user_addr2 = createString("user_addr2");

    public final DateTimePath<java.time.LocalDateTime> user_birth = createDateTime("user_birth", java.time.LocalDateTime.class);

    public final StringPath user_email = createString("user_email");

    public final StringPath user_grade = createString("user_grade");

    public final StringPath user_hp = createString("user_hp");

    public final StringPath user_id = createString("user_id");

    public final StringPath user_name = createString("user_name");

    public final NumberPath<Integer> user_point = createNumber("user_point", Integer.class);

    public final StringPath user_profile = createString("user_profile");

    public final StringPath user_promo = createString("user_promo");

    public final StringPath user_provider = createString("user_provider");

    public final StringPath user_pw = createString("user_pw");

    public final DateTimePath<java.time.LocalDateTime> user_regDate = createDateTime("user_regDate", java.time.LocalDateTime.class);

    public final StringPath user_role = createString("user_role");

    public final StringPath user_status = createString("user_status");

    public final DateTimePath<java.time.LocalDateTime> user_update = createDateTime("user_update", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> user_visit_Date = createDateTime("user_visit_Date", java.time.LocalDateTime.class);

    public final StringPath user_zip = createString("user_zip");

    public final NumberPath<Integer> userpoint_point_no = createNumber("userpoint_point_no", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

