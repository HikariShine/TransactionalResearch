package com.mxixm.transactional.mybatis.repository;


import com.mxixm.transactional.mybatis.model.Customer;
import org.apache.ibatis.annotations.*;

/**
 * Created by bslota on 30.06.17.
 */
@Mapper
public interface CustomerRepository {

    @Insert("insert into customer (name,email) values (#{name},#{email})")
    @Options(useGeneratedKeys = true)
    int insert(Customer customer);


    @Update("update customer set token = #{token} where id = #{id}")
    int update(Customer customer);

    @Select("select id, name, email, token from customer where id = #{id}")
    Customer findOne(long id);

}
