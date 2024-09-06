package com.khrd.controller;

import com.khrd.model.entity.Customer;
import com.khrd.model.request.CustomerRequest;
import com.khrd.model.response.CustomerResponse;
import com.khrd.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping()
    private ResponseEntity<CustomerResponse<List<Customer>>> getAllCustomers() {
        List<Customer>customers = customerService.getAllCustomer();
        CustomerResponse<List<Customer>>response=CustomerResponse.<List<Customer>>builder()
                .message("Get Customer Successfully.")
                .status(HttpStatus.OK)
                .payload(customers)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse<Customer>> getCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            CustomerResponse<Customer>response=CustomerResponse.<Customer>builder()
                    .message("Get Customer Not Successfully.")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }else {
            CustomerResponse<Customer>response=CustomerResponse.<Customer>builder()
                    .message("Get Customer Successfully.")
                    .status(HttpStatus.OK)
                    .payload(customer)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping()
    public ResponseEntity<CustomerResponse<Customer>> createCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer data = customerService.createrCustomer(customerRequest);
        if (data != null) {
            CustomerResponse<Customer>response=CustomerResponse.<Customer>builder()
                    .message("Created Customer Successfully.")
                    .status(HttpStatus.CREATED)
                    .payload(customerService.getCustomerById(data.getId()))
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else {
            CustomerResponse<Customer>response=CustomerResponse.<Customer>builder()
                    .message("Created Customer Not Successfully.")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse<Customer>> updateCustomer(@PathVariable("id")Long id,@RequestBody CustomerRequest customerRequest) throws Exception {
        Customer customer = customerService.updateCustomerById(id,customerRequest);
        if (customer != null) {
            CustomerResponse<Customer>response=CustomerResponse.<Customer>builder()
                    .message("Update Customer Successfully.")
                    .status(HttpStatus.OK)
                    .payload(customer)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            CustomerResponse<Customer>response=CustomerResponse.<Customer>builder()
                    .message("Update Customer Not Successfully.")
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse<Customer>> deleteCustomer(@PathVariable("id")Long id) throws Exception {
        Void delete = customerService.removeCustomerById(id);
        CustomerResponse<Customer>response=CustomerResponse.<Customer>builder()
                .message("Removed Customer Number "+id+" Successfully.")
                .status(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
