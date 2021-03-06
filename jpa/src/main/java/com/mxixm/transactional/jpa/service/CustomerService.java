package com.mxixm.transactional.jpa.service;

import com.mxixm.transactional.jpa.event.CustomerCreatedEvent;
import com.mxixm.transactional.jpa.model.Customer;
import com.mxixm.transactional.jpa.repository.CustomerRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by bslota on 30.06.17.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CustomerService(CustomerRepository customerRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.customerRepository = customerRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public Customer createCustomer(String name, String email) {
        final Customer newCustomer = customerRepository.save(new Customer(name, email));
        final CustomerCreatedEvent event = new CustomerCreatedEvent(newCustomer);
        applicationEventPublisher.publishEvent(event);
        return newCustomer;
    }
}
