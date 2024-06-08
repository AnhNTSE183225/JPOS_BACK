package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.repository.IStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService implements IStaffService {

    private final IStaffRepository staffRepository;
    private final IAccountRepository accountRepository;

    @Autowired
    public StaffService(IStaffRepository staffRepository, IAccountRepository accountRepository) {
        this.staffRepository = staffRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Staff getStaffByAccount(Account account) {
        Account registeredAccount = accountRepository.findOneByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (registeredAccount == null) {
            throw new RuntimeException("Account doesn't exists!");
        } else {
            Staff staff = staffRepository.findByUsername(registeredAccount.getUsername());
            if (staff == null) {
                throw new RuntimeException("Account doesn't belong to staff");
            } else {
                return staff;
            }
        }
    }
}
