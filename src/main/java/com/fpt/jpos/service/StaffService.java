package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.repository.IStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService implements IStaffService {

    private final IStaffRepository staffRepository;
    private final IAccountRepository accountRepository;

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffByAccount(Account account) {
        return staffRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
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
