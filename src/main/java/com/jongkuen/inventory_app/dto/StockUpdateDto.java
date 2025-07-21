package com.jongkuen.inventory_app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateDto {
    @NotNull(message = "수량을 입력하세요")
    @Min(value = 1, message = "수량은 1 이상이어야 합니다")
    private Integer quantity;
}
