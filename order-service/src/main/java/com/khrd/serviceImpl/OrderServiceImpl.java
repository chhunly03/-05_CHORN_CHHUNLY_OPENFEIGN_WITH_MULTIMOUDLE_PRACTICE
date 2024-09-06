package com.khrd.serviceImpl;

import com.khrd.client.CustomerFeignClient;
import com.khrd.client.ProductFeignClient;
import com.khrd.model.request.OrderRequest;
import com.khrd.model.response.OrderResponse;
import com.khrd.model.responseDTO.CustomerResponseDTO;
import com.khrd.model.responseDTO.OrderResponseDTO;
import com.khrd.model.responseDTO.ProductResponseDTO;
import com.khrd.model.entity.Order;
import com.khrd.repository.OrderRepository;
import com.khrd.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductFeignClient productFeignClient;
    private final CustomerFeignClient customerFeignClient;

    @Override
    public List<OrderResponseDTO> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();

        for (Order order : orders) {
            OrderResponse<CustomerResponseDTO> customerResponse = customerFeignClient.getCustomerById(order.getCustomerId());
            CustomerResponseDTO customerDTO = customerResponse != null ? customerResponse.getPayload() : null;

            List<Long> productId = order.getProductIds();
            List<ProductResponseDTO> lstProduct = new ArrayList<>();

            for (Long pro : productId) {
                OrderResponse<ProductResponseDTO> productResponse = productFeignClient.getProductsByIds(pro);
                if (productResponse != null && productResponse.getPayload() != null) {
                    lstProduct.add(productResponse.getPayload());
                }
            }

            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setId(order.getId());
            orderResponseDTO.setCustomerResponseDTO(customerDTO);
            orderResponseDTO.setProductResponseDTO(lstProduct);
            orderResponseDTO.setOrderDate(order.getOrderDate());

            orderResponseDTOs.add(orderResponseDTO);
        }

        return orderResponseDTOs;
    }


    @Override
    public OrderResponseDTO createrOrder(OrderRequest orderRequest) {
        Order order = new Order();
        OrderResponse<CustomerResponseDTO> customerResponseDTO = customerFeignClient.getCustomerById(orderRequest.getCustomerId());
        List<ProductResponseDTO> lstProduct = new ArrayList<>();
        for (Long productId : orderRequest.getProductIds() ) {
            OrderResponse<ProductResponseDTO> productResponseDTOs = productFeignClient.getProductsByIds(productId);
           if(productResponseDTOs!=null) {
               lstProduct.add(productResponseDTOs.getPayload());
           }
            order.setProductIds(orderRequest.getProductIds());
            order.setCustomerId(orderRequest.getCustomerId());
            order.setOrderDate(orderRequest.getOrderDate());
            orderRepository.save(order);
        }
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.toOrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setCustomerResponseDTO(customerResponseDTO.getPayload());
        orderResponseDTO.setProductResponseDTO(lstProduct);
        orderResponseDTO.setOrderDate(order.getOrderDate());
        return orderResponseDTO;
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        OrderResponse<CustomerResponseDTO> customerResponse = customerFeignClient.getCustomerById(order.getCustomerId());
        CustomerResponseDTO customerDTO = customerResponse != null ? customerResponse.getPayload() : null;

        List<Long> productIds = order.getProductIds();
        List<ProductResponseDTO> lstProduct = new ArrayList<>();

        for (Long pro : productIds) {
            OrderResponse<ProductResponseDTO> productResponse = productFeignClient.getProductsByIds(pro);
            if (productResponse != null && productResponse.getPayload() != null) {
                lstProduct.add(productResponse.getPayload());
            }
        }

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setCustomerResponseDTO(customerDTO);
        orderResponseDTO.setProductResponseDTO(lstProduct);
        orderResponseDTO.setOrderDate(order.getOrderDate());

        return orderResponseDTO;
    }

    @Override
    public OrderResponseDTO updateOrderById(Long id, OrderRequest orderRequest) throws Exception {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new Exception("Order not found with id " + id);
        }
        order.setCustomerId(orderRequest.getCustomerId());
        order.setProductIds(orderRequest.getProductIds());
        order.setOrderDate(orderRequest.getOrderDate());

        Order updatedOrder = orderRepository.save(order);

        OrderResponse<CustomerResponseDTO> customerResponse = customerFeignClient.getCustomerById(updatedOrder.getCustomerId());
        CustomerResponseDTO customerDTO = customerResponse != null ? customerResponse.getPayload() : null;

        List<Long> productIds = updatedOrder.getProductIds();
        List<ProductResponseDTO> lstProduct = new ArrayList<>();
        for (Long pro : productIds) {
            OrderResponse<ProductResponseDTO> productResponse = productFeignClient.getProductsByIds(pro);
            if (productResponse != null && productResponse.getPayload() != null) {
                lstProduct.add(productResponse.getPayload());
            }
        }

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(updatedOrder.getId());
        orderResponseDTO.setCustomerResponseDTO(customerDTO);
        orderResponseDTO.setProductResponseDTO(lstProduct);
        orderResponseDTO.setOrderDate(updatedOrder.getOrderDate());

        return orderResponseDTO;
    }

    @Override
    public Void removeOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        orderRepository.delete(order);
        return null;
    }
}
