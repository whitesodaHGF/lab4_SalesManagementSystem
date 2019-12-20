package com.service.AdminServices.Impl;

import com.Exception.CommonException;
import com.enums.ResultEnum;
import com.mapper.CustomerMapper;
import com.model.Customer;
import com.service.AdminServices.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户管理
 *
 * @author:HGF
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public List<Customer> getCustomers() {
        return customerMapper.selectAll();
    }

    @Override
    public Customer getCustomer(int id) {
        Customer customer=customerMapper.selectCustomerById(id);
        if(customer==null) throw new CommonException(ResultEnum.CUSTOMER_ID_MISS);
        return customer;
    }

    @Override
    @Transactional
    public void addCustomer(Customer customer) {
        customerMapper.insert(customer);
    }

    @Override
    @Transactional
    public void updateCustomer(Customer customer) {
        customerMapper.updateByPrimaryKey(customer);
    }
}
