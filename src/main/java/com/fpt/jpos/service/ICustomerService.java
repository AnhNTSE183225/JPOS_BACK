package com.fpt.jpos.service;


import com.fpt.jpos.pojo.Customer;

public interface ICustomerService {
    Customer loginCustomer(String username, String password);
}
