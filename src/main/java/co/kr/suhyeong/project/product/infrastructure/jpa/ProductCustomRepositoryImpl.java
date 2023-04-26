package co.kr.suhyeong.project.product.infrastructure.jpa;

import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.aggregate.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static co.kr.suhyeong.project.product.domain.model.aggregate.QProduct.product;

public class ProductCustomRepositoryImpl extends QuerydslRepositorySupport implements ProductCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ProductCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ProductCustomRepositoryImpl.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Product> findProductListWithPagination(Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(product)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
