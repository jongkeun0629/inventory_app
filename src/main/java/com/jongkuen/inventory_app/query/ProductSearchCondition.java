package com.jongkuen.inventory_app.query;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
// 검색할 때 필요한 정보들. Dto와 유사
public class ProductSearchCondition {
    private String name;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;
}
