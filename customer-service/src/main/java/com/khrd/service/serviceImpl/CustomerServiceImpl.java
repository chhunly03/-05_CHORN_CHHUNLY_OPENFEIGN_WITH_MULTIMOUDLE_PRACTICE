package com.khrd.service.serviceImpl;

import com.khrd.model.entity.Customer;
import com.khrd.model.request.CustomerRequest;
import com.khrd.repository.CustomerRepository;
import com.khrd.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findCustomersById(id);
    }

    @Override
    public Customer createrCustomer(CustomerRequest customerRequest) {
        return customerRepository.save(customerRequest.toCustomer());
    }

    @Override
    public Customer updateCustomerById(Long id, CustomerRequest customerRequest) throws Exception {
        Customer customer = customerRepository.findCustomersById(id);
        System.out.println(customer.toString());
        if (customer == null) {
            throw new Exception ("User not found.");
        }else {
            return customerRepository.save(customerRequest.toUpdateCustomer(customer));
        }
    }

    @Override
    public Void removeCustomerById(Long id) throws Exception {
        customerRepository.findCustomersById(id);
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }else {
            throw new Exception("Customer Not Found!.");
        }
        return null;
    }
}
