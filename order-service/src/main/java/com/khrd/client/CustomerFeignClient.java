package com.khrd.client;

import com.khrd.model.response.OrderResponse;
import com.khrd.model.responseDTO.CustomerResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "customer-service",url = "http://localhost:8081/api/v1/customer")
public interface CustomerFeignClient {
    @GetMapping()
    CustomerResponseDTO getAllCustomer();

    @GetMapping("/{id}")
    OrderResponse<CustomerResponseDTO> getCustomerById(@PathVariable("id") Long id);
}