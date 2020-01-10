package com.mxixm.transactional.mybatis.service;

import com.mxixm.transactional.mybatis.event.CustomerCreatedEvent;
import com.mxixm.transactional.mybatis.model.Customer;
import com.mxixm.transactional.mybatis.repository.CustomerRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
        final Customer newCustomer = new Customer(name, email);
        customerRepository.insert(newCustomer);
        final CustomerCreatedEvent event = new CustomerCreatedEvent(newCustomer);
        applicationEventPublisher.publishEvent(event);
        return newCustomer;
    }

    @Transactional
    public Customer createCustomerRequiredTransactionalTest(String name, String email) {
        final Customer newCustomer = new Customer(name, email);
        customerRepository.insert(newCustomer);
        return newCustomer;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Customer createCustomerSupportsTransactionalTest(String name, String email) {
        final Customer newCustomer = new Customer(name, email);
        customerRepository.insert(newCustomer);
        int a = 1 / 0;
        return newCustomer;
    }

    public Customer createCustomerSupportsWithoutTransactionalTest(String name, String email) {
        final Customer newCustomer = new Customer(name, email);
        customerRepository.insert(newCustomer);
        int a = 1 / 0;
        return newCustomer;
    }

}
