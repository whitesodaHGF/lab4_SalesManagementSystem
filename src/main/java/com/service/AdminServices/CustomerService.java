package com.service.AdminServices;

import com.model.Customer;

import java.util.List;

/**
 * 客户管理
 *
 * @author:HGF
 */
public interface CustomerService {

    List<Customer> getCustomers();

    Customer getCustomer(int id);

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);
}
