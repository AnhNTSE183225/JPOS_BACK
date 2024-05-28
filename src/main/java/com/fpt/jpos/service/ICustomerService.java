package com.fpt.jpos.service;


import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.pojo.CustomerRegistration;

public interface ICustomerService {
    Customer loginCustomer(Account account);
    Customer registerCustomer(CustomerRegistration customerRegistration);
}
