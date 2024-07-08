package com.fpt.jpos.service;


import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;

import java.util.List;

public interface ICustomerService {
    Customer loginCustomer(Account account);

    Customer updateCustomer(Customer customer);

    void delete(Integer customerId);

    List<Customer> findAll();
}
