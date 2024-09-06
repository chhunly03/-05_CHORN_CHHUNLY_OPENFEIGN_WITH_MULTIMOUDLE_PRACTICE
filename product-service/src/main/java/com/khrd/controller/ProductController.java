package com.khrd.controller;

import com.khrd.model.entity.Product;
import com.khrd.model.request.ProductRequest;
import com.khrd.model.response.ProductResponse;
import com.khrd.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    private ResponseEntity<ProductResponse<List<Product>>> getAllProducts() {
        List<Product>products = productService.getAllProduct();
        ProductResponse<List<Product>>response=ProductResponse.<List<Product>>builder()
                .message("Get Product Successfully.")
                .status(HttpStatus.OK)
                .payload(products)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            ProductResponse<Product>response=ProductResponse.<Product>builder()
                    .message("Get Product Not Successfully.")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }else {
            ProductResponse<Product>response=ProductResponse.<Product>builder()
                    .message("Get Product Successfully.")
                    .status(HttpStatus.OK)
                    .payload(product)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping()
    public ResponseEntity<ProductResponse<Product>> createProduct(@RequestBody ProductRequest productRequest) {
        Product data = productService.createrProduct(productRequest);
        if (data != null) {
            ProductResponse<Product>response=ProductResponse.<Product>builder()
                    .message("Created Product Successfully.")
                    .status(HttpStatus.CREATED)
                    .payload(productService.getProductById(data.getId()))
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else {
            ProductResponse<Product>response=ProductResponse.<Product>builder()
                    .message("Created Product Not Successfully.")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse<Product>> updateProduct(@PathVariable("id")Long id,@RequestBody ProductRequest productRequest) throws Exception {
        Product product = productService.updateProductById(id,productRequest);
        if (product != null) {
            ProductResponse<Product>response=ProductResponse.<Product>builder()
                    .message("Update Product Successfully.")
                    .status(HttpStatus.OK)
                    .payload(product)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            ProductResponse<Product>response=ProductResponse.<Product>builder()
                    .message("Update Product Not Successfully.")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse<Product>> deleteProduct(@PathVariable("id")Long id) throws Exception {
        productService.removeProductById(id);
        ProductResponse<Product>response=ProductResponse.<Product>builder()
                .message("Removed Product Number "+id+" Successfully.")
                .status(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
