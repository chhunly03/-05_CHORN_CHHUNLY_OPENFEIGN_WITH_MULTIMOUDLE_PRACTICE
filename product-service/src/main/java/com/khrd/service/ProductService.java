package com.khrd.service;

import com.khrd.model.entity.Product;
import com.khrd.model.request.ProductRequest;

import java.util.List;

public interface ProductService {
    List<Product> getAllProduct();

    Product getProductById(Long id);

    Product createrProduct(ProductRequest productRequest);

    Product updateProductById(Long id, ProductRequest productRequest) throws Exception;

    void removeProductById(Long id);
}
