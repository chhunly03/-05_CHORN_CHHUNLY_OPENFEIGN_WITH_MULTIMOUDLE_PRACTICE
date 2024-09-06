package com.khrd.model.request;

import com.khrd.model.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerRequest {
    private String name;
    private String email;

    public Customer toCustomer() {
        return new Customer(null,this.name,this.email);
    }

    public Customer toUpdateCustomer(Customer customer) {
        return new Customer(customer.getId(),this.name,this.email);
    }
}
