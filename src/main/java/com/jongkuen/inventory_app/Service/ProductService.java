package com.jongkuen.inventory_app.Service;

import com.jongkuen.inventory_app.dto.ProductDto;
import com.jongkuen.inventory_app.model.Product;
import com.jongkuen.inventory_app.query.ProductQueryRepository;
import com.jongkuen.inventory_app.query.ProductSearchCondition;
import com.jongkuen.inventory_app.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductQueryRepository queryRepository;

    public List<Product> search(ProductSearchCondition condition){
        return queryRepository.search(condition);
    }

    public Product getById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("제품을 찾을 수 없습니다. id: " + id));
    }

    public Product create(ProductDto dto){
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        return productRepository.save(product);
    }

    public Product update(Long id, ProductDto dto){
        Product product = getById(id);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        return productRepository.save(product);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }
}
