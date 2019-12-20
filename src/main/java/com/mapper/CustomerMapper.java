package com.mapper;

import com.model.Customer;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CustomerMapper extends Mapper<Customer> {

    @Select("<script>" +
            "select * from customer" +
            "<if test='id!=null'>" +
            "where id=#{id}" +
            "</if>" +
            "</script>")
    Customer selectCustomerById(int id);
}