package com.mxixm.transactional.jpa.repository;

import com.mxixm.transactional.jpa.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bslota on 30.06.17.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
