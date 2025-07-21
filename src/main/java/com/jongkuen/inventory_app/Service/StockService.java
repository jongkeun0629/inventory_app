package com.jongkuen.inventory_app.Service;

import com.jongkuen.inventory_app.dto.StockDto;
import com.jongkuen.inventory_app.dto.StockUpdateDto;
import com.jongkuen.inventory_app.model.Product;
import com.jongkuen.inventory_app.model.Stock;
import com.jongkuen.inventory_app.model.Warehouse;
import com.jongkuen.inventory_app.query.StockQueryRepository;
import com.jongkuen.inventory_app.query.StockSearchCondition;
import com.jongkuen.inventory_app.repository.ProductRepository;
import com.jongkuen.inventory_app.repository.StockRepository;
import com.jongkuen.inventory_app.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final StockQueryRepository queryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public List<Stock> search(StockSearchCondition condition) {
        return queryRepository.search(condition);
    }

    public Stock getById(Long id){
        return stockRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("재고를 찾을 수 없습니다"));
    }

    public Stock create(StockDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다."));
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다."));

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setWarehouse(warehouse);
        stock.setQuantity(dto.getQuantity());

        return stockRepository.save(stock);
    }

    public Stock update(Long id, StockUpdateDto dto){
        Stock stock = getById(id);
        stock.setQuantity(dto.getQuantity());

        return stockRepository.save(stock);
    }

    public void delete(Long id){
        stockRepository.deleteById(id);
    }
}
