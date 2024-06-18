package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.repository.IStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Staff staff = null;
        if (registeredAccount != null) {
            staff = staffRepository.findByUsername(registeredAccount.getUsername());
        }
        return staff;
    }

    @Override
    public List<Staff> getDesignStaff() {
        return staffRepository.findByStaffType("design");
    }

    @Override
    public List<Staff> getSaleStaff() {
        return staffRepository.findByStaffType("sale");
    }

    @Override
    public List<Staff> getProductionStaff() {
        return staffRepository.findByStaffType("produce");
    }
}
