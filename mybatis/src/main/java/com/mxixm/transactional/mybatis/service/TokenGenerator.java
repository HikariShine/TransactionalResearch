package com.mxixm.transactional.mybatis.service;


import com.mxixm.transactional.mybatis.model.Customer;

/**
 * Created by bslota on 03.07.17.
 */
public interface TokenGenerator {

    void generateToken(Customer customer);
}
