package com.fpt.jpos.service;


import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.dto.CustomerRegistrationDTO;

public interface ILoginFacebookService {
    Customer loginFacebookCustomer(Account account);
    Customer registerCustomer(CustomerRegistrationDTO customerRegistrationDTO);
}
