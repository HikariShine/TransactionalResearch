package com.mxixm.transactional.mybatis.service;

import com.mxixm.transactional.mybatis.model.Customer;
import com.mxixm.transactional.mybatis.repository.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bslota on 03.07.17.
 */
@Service
@Profile("!async")
public class DefaultTokenGenerator implements TokenGenerator {

    private final CustomerRepository customerRepository;

    public DefaultTokenGenerator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void generateToken(Customer customer) {
        final String token = String.valueOf(customer.hashCode());
        customer.activatedWith(token);
        customerRepository.update(customer);
    }
}
