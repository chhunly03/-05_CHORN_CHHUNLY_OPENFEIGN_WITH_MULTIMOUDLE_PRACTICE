package com.khrd.client;

import com.khrd.model.entity.Order;
import com.khrd.model.response.OrderResponse;
import com.khrd.model.responseDTO.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name= "product-service", url = "http://localhost:8083/api/v1/product/")
public interface ProductFeignClient {

    @GetMapping
    OrderResponse<List<ProductResponseDTO> > getAllProducts();
    @GetMapping("/{id}")
    OrderResponse<ProductResponseDTO> getProductsByIds(@PathVariable Long id);
}

