package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService implements ICustomerService{

    private IAccountRepository accountRepository;
    private ICustomerRepository customerRepository;

    @Autowired
    public CustomerService(IAccountRepository accountRepository, ICustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer loginCustomer(String username, String password) {
        Customer customer = null;
        Optional<Account> account = accountRepository.findById(username);
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            customer = customerRepository.findByUsername(account.get().getUsername());
        }
        return customer;
    }
}
