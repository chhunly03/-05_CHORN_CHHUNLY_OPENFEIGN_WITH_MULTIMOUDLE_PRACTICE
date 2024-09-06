package com.khrd.model.request;

import com.khrd.model.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductRequest {
    private String name;
    @Column(precision = 8,scale = 2)
    private BigDecimal price;

    public Product toProduct() {
        return new Product(null, name, price);
    }

    public Product toProductUpdate(Product product) {
        return new Product(product.getId(), name, price);
    }
}
