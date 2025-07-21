package com.jongkuen.inventory_app.query;

import com.jongkuen.inventory_app.model.Product;
import com.jongkuen.inventory_app.model.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
// 레포지토리 유사. Q 클래스 사용 서치컨디션(Dto) 활용. 검색 쿼리 생성
public class ProductQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Product> search(ProductSearchCondition condition) {
        // 빌드 한 번 해야 Q 클래스 생성
        QProduct product = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        if(condition.getName() != null && !condition.getName().isBlank()) {
            // contains: 해당 문자열이 있는지. Ignore: 대소문자 무시
            builder.and(product.name.containsIgnoreCase(condition.getName()));
        }
        if(condition.getMinPrice() != null){
            // goe: 크거나 같은
            builder.and(product.price.goe(condition.getMinPrice()));
        }
        if(condition.getMaxPrice() != null){
            // loe: 작거나 같은
            builder.and(product.price.loe(condition.getMaxPrice()));
        }

        // select * from product where builder(위 코드. 이름, 가격의 조건) order by name. fetch(): 실행
        return queryFactory.selectFrom(product).where(builder).orderBy(product.name.asc()).fetch();
    }
}
