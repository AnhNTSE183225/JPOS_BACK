package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.repository.IStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    @Transactional
    public int updateStaff(Staff staff) {
        System.out.println(staff.getName());
        System.out.println(staff.getPhone());
        System.out.println(staff.getStaffId());
        return staffRepository.updateStaff(staff.getName(), staff.getPhone(), staff.getStaffType(), staff.getStaffId());
    }

    @Override
    @Transactional
    public void deleteStaff(Integer staffId) {
        staffRepository.deleteById(staffId);
    }
}
