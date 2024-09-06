package com.khrd.model.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderResponseDTO{
    private Long id;
    private CustomerResponseDTO customerResponseDTO;
    private List<ProductResponseDTO>productResponseDTO;
    private LocalDateTime orderDate;

    public OrderResponseDTO toOrderResponseDTO(){
        return new OrderResponseDTO(this.id, this.customerResponseDTO, this.productResponseDTO,this.orderDate);
    }
}
