package com.mxixm.transactional.mybatis.service;

import com.mxixm.transactional.mybatis.model.Customer;
import com.mxixm.transactional.mybatis.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by bslota on 01.07.17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;


    @Autowired
    private CustomerTransactionalService customerTransactionalService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MyOwnDataSourceService myOwnDataSourceService;

    @Before
    public void doClear() {
        myOwnDataSourceService.doClear();
    }

    @Test
    public void shouldPersistCustomerWithToken() throws Exception {
        //when
        final Customer returnedCustomer = customerService.createCustomer("Matt", "matt@gmail.com");

        //then
        final Customer persistedCustomer = customerRepository.findOne(returnedCustomer.getId());
        assertEquals("matt@gmail.com", returnedCustomer.getEmail());
        assertEquals("Matt", returnedCustomer.getName());
        assertTrue(returnedCustomer.hasToken());
        assertEquals(returnedCustomer, persistedCustomer);
    }

    @Test
    public void testSimpleTransactional() throws Exception {
        customerService.createCustomerRequiredTransactionalTest("Matt", "matt@gmail.com");
    }

    @Test
    public void testNestedTransactional() throws Exception {
        customerTransactionalService.createCustomerSupportsTransactionalTest("Matt", "matt@gmail.com");
    }

    @Test
    public void doTestSavepoint() {
        myOwnDataSourceService.doTestSavepoint();
    }

}