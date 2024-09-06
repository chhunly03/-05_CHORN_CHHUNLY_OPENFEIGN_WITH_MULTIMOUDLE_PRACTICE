package com.khrd.controller;

import com.khrd.model.entity.Customer;
import com.khrd.model.entity.Order;
import com.khrd.model.entity.Product;
import com.khrd.model.request.OrderRequest;
import com.khrd.model.response.OrderResponse;
import com.khrd.model.responseDTO.OrderResponseDTO;
import com.khrd.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<OrderResponse<List<OrderResponseDTO>>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrder();
        OrderResponse<List<OrderResponseDTO>> response = OrderResponse.<List<OrderResponseDTO>>builder()
                .message("Get Customer Successfully.")
                .status(HttpStatus.OK)
                .payload(orders)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<OrderResponse<OrderResponseDTO>> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponseDTO data = orderService.createrOrder(orderRequest);
        if (data != null) {
            OrderResponse<OrderResponseDTO> response = OrderResponse.<OrderResponseDTO>builder()
                    .message("Created Order Successfully.")
                    .status(HttpStatus.CREATED)
                    .payload(data)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            OrderResponse<OrderResponseDTO> response = OrderResponse.<OrderResponseDTO>builder()
                    .message("Created Order Not Successfully.")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse<OrderResponseDTO>> getOrderById(@PathVariable Long id) {
        OrderResponseDTO orderResponseDTO = orderService.getOrderById(id);
        if (orderResponseDTO == null) {
            OrderResponse<OrderResponseDTO> response = OrderResponse.<OrderResponseDTO>builder()
                    .message("Get Order Not Successfully.")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            OrderResponse<OrderResponseDTO> response = OrderResponse.<OrderResponseDTO>builder()
                    .message("Get Order Successfully.")
                    .status(HttpStatus.OK)
                    .payload(orderResponseDTO)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse<OrderResponseDTO>> updateOrder(@PathVariable("id")Long id,@RequestBody OrderRequest orderRequest) throws Exception {
        OrderResponseDTO order = orderService.updateOrderById(id,orderRequest);
        if (order != null) {
            OrderResponse<OrderResponseDTO>response=OrderResponse.<OrderResponseDTO>builder()
                    .message("Update Order Successfully.")
                    .status(HttpStatus.OK)
                    .payload(order)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            OrderResponse<OrderResponseDTO>response=OrderResponse.<OrderResponseDTO>builder()
                    .message("Update Order Not Successfully.")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse<OrderResponseDTO>> deleteOrder(@PathVariable("id")Long id) throws Exception {
        Void delete = orderService.removeOrderById(id);
        OrderResponse<OrderResponseDTO>response=OrderResponse.<OrderResponseDTO>builder()
                .message("Removed Order Number "+id+" Successfully.")
                .status(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
