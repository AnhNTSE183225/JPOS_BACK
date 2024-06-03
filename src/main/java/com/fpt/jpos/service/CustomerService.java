package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.dto.CustomerRegistrationDTO;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;

    @Autowired
    public CustomerService(IAccountRepository accountRepository, ICustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer loginCustomer(Account userAccount) {
        Customer customer = null;
        Optional<Account> account = accountRepository.findById(userAccount.getUsername());
        if (account.isPresent() && account.get().getPassword().equals(userAccount.getPassword())) {
            customer = customerRepository.findByUsername(account.get().getUsername());
        }
        return customer;
    }

    @Override
    public Customer registerCustomer(CustomerRegistrationDTO customerRegistrationDTO) {
        Customer customer = null;
        if (accountRepository.findById(customerRegistrationDTO.getUsername()).isEmpty()) {
            Account newAccount = new Account();
            newAccount.setUsername(customerRegistrationDTO.getUsername());
            newAccount.setPassword(customerRegistrationDTO.getPassword());
            newAccount.setRole("customer");
            newAccount.setStatus(true);
            accountRepository.save(newAccount);
            customer = new Customer();
            customer.setUsername(newAccount.getUsername());
            customer.setName(customerRegistrationDTO.getName());
            customer.setAddress(customerRegistrationDTO.getAddress());
            customer = customerRepository.save(customer);
        }
        return customer;
    }

}
