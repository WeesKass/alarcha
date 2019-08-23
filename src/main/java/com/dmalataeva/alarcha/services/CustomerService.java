package com.dmalataeva.alarcha.services;

import com.dmalataeva.alarcha.entities.CustomerEntity;
import com.dmalataeva.alarcha.models.Customer;
import com.dmalataeva.alarcha.repositories.CustomerRepository;
import com.dmalataeva.alarcha.util.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerById(int customerId) throws Exception {
        return new Customer(customerRepository.findById(customerId).orElseThrow(RecordNotFoundException::new));
    }

    public Customer saveCustomer(Customer customer) {
        CustomerEntity saveResult = customerRepository.save(customer.convertToEntity());
        return new Customer(saveResult);
    }

    public void deleteCustomerById(int customerId) {
        customerRepository.deleteById(customerId);
    }
}
