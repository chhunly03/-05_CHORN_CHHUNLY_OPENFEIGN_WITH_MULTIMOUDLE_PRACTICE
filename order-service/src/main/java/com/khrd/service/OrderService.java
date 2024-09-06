package com.khrd.service;

import com.khrd.model.request.OrderRequest;
import com.khrd.model.responseDTO.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> getAllOrder();

    OrderResponseDTO createrOrder(OrderRequest orderRequest);

    OrderResponseDTO getOrderById(Long id);

    OrderResponseDTO updateOrderById(Long id, OrderRequest orderRequest) throws Exception;

    Void removeOrderById(Long id);
}
