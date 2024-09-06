package com.khrd.service;

import com.khrd.model.entity.Customer;
import com.khrd.model.request.CustomerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<Customer> getAllCustomer();

    Customer getCustomerById(Long id);

    Customer createrCustomer(CustomerRequest customerRequest);

    Customer updateCustomerById(Long id, CustomerRequest customerRequest) throws Exception;

    Void removeCustomerById(Long id) throws Exception;
}
