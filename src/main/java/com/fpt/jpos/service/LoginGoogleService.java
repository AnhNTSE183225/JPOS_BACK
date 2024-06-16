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
public class LoginGoogleService implements ILoginGoogleService {

    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;

    @Autowired
    public LoginGoogleService(IAccountRepository accountRepository, ICustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

	@Override
	public Customer loginGoogleCustomer(Account userAccount) {
        Customer customer = null;
        
        return customer;
	}

	@Override
	public Customer registerCustomer(CustomerRegistrationDTO customerRegistrationDTO) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'registerCustomer'");
	}


}
