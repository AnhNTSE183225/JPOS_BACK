package com.fpt.jpos.service;


import com.fpt.jpos.dto.CustomerRegistrationDTO;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;

public interface ICustomerService {
    Customer loginCustomer(Account account);

    Customer registerCustomer(CustomerRegistrationDTO customerRegistrationDTO);

    Customer updateCustomer(Integer customerId, String name, String email, String address);
}
