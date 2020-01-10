package com.mxixm.transactional.jpa.service;

import com.mxixm.transactional.jpa.model.Customer;

/**
 * Created by bslota on 03.07.17.
 */
public interface TokenGenerator {

    void generateToken(Customer customer);
}
