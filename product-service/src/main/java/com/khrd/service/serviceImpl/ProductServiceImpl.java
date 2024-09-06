package com.khrd.service.serviceImpl;

import com.khrd.model.entity.Product;
import com.khrd.model.request.ProductRequest;
import com.khrd.repository.ProductRepository;
import com.khrd.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createrProduct(ProductRequest productRequest) {
        return productRepository.save(productRequest.toProduct());
    }

    @Override
    public Product updateProductById(Long id, ProductRequest productRequest) throws Exception {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return productRepository.save(productRequest.toProductUpdate(product));
        }else {
            throw new Exception("Not Found Product!.");
        }
    }

    @Override
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }
}
