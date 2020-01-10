package com.mxixm.transactional.mybatis.service;

import com.mxixm.transactional.mybatis.model.Customer;
import com.mxixm.transactional.mybatis.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by bslota on 30.06.17.
 */
@Service
public class CustomerTransactionalService {

    private final CustomerRepository customerRepository;

    private final CustomerService customerService;

    public CustomerTransactionalService(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    /**
     * 测试无事务时的传递机制：不通过拦截器，直接调用，所以内部执行方法和事务无任何关联。
     * 如果内部抛出异常，被外部事务拦截，此时内部执行逻辑也在外面的事务中，而异常不会影响内部和外部的所有SQL的执行，所以会被全部提交
     */
    @Transactional
    public Customer createCustomerWithoutTransactionalTest(String name, String email) {
        final Customer newCustomer = new Customer(name, email);
        customerRepository.insert(newCustomer);
        try {
            customerService.createCustomerSupportsWithoutTransactionalTest(name + "_nested", email + "_nested");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCustomer;
    }

    /**
     * 测试SUPPORTS事务传递机制：支持使用当前事务，如果当前事务不存在，则不使用事务。
     * 在内部嵌套事务为SUPPORTS时，如果内部抛出异常，被外部事务拦截，此时事务仍然会回滚
     * 因为在内部异常中，已经标记事务为只可以回滚了，所以外部提交的时候会报错，其实并不是回滚，也是事务并没有提交，所以数据库中没有数据
     * 报错信息为：org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only
     * 传递机制如果为REQUIRED，则同样会返回如上错误。但如果内部不加事务注解，则不会返回上面的错误
     * 说明在每个事务嵌套过程中，任何一层发生了异常并且未捕获，都会被标记为仅允许回滚。
     */
    @Transactional
    public Customer createCustomerSupportsTransactionalTest(String name, String email) {
        final Customer newCustomer = new Customer(name, email);
        customerRepository.insert(newCustomer);
        try {
            customerService.createCustomerSupportsTransactionalTest(name + "_nested", email + "_nested");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCustomer;
    }

}
