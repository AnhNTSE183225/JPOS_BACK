package com.fpt.jpos.service;

import com.fpt.jpos.exception.AccountAlreadyExistsException;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Customer createNewCustomer(Account account, String name, String address) {
        Customer customer = Customer.builder()
                .address(address)
                .account(account)
                .name(name)
                .build();
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer customer) throws AccountAlreadyExistsException {
        Customer oldCustomer = this.customerRepository.findByUsername(customer.getAccount().getUsername());
        //If there is changes to the email
        if(!oldCustomer.getAccount().getEmail().equals(customer.getAccount().getEmail())) {
            if(this.accountRepository.findOneByEmail(customer.getAccount().getEmail()) != null) {
                throw new AccountAlreadyExistsException();
            }
        }
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Integer customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.getAllCustomer();
    }

}
